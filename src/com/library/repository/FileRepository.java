package com.library.repository;

import com.library.annotations.FileDesc;

import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public abstract class FileRepository<T extends Serializable, ID extends Serializable> {

    private final String filename;
    private final Class<T> entityClass;
    private final Field idField;
    private final boolean autoIncrement;
    private final String schema;
    private final String database;

    public FileRepository(Class<T> entityClassObject, String idFieldName) {
        this.entityClass = entityClassObject;

        // Récupérer le nom du fichier et l'autocrémentation à partir de l'annotation @FileDesc
        if (entityClass.isAnnotationPresent(FileDesc.class)) {
            FileDesc fileDesc = entityClass.getAnnotation(FileDesc.class);
            this.autoIncrement = fileDesc.autocrement();
            this.database = fileDesc.database();
            this.schema = database + "/" + fileDesc.schema();
            String filenameAttr = fileDesc.filename();
            if (filenameAttr.isEmpty()) {
                filenameAttr = entityClass.getSimpleName();
            }
            this.filename = schema + "/" + filenameAttr + ".txt";
            createDirectoriesIfDoesntExist();
        } else {
            throw new RuntimeException("La classe " + entityClass.getSimpleName() + " doit avoir une annotation @FileDesc.");
        }

        // Récupérer le champ ID
        try {
            this.idField = entityClass.getDeclaredField(idFieldName);
            this.idField.setAccessible(true); // Permettre l'accès aux champs privés
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("Champ ID invalide : " + idFieldName);
        }
    }

    private void createDirectoriesIfDoesntExist() {
        File databaseDirectory = new File(database);
        File schemaDirectory = new File(schema);
        if (!databaseDirectory.exists()) {
            databaseDirectory.mkdir();
        }
        if (!schemaDirectory.exists()) {
            schemaDirectory.mkdir();
        }
    }

    public List<T> findAll() throws IOException, ClassNotFoundException {
        List<T> entities = new ArrayList<>();
        File file = new File(filename);

        if (file.length() == 0) {
            return entities; // Retourner une liste vide si le fichier est vide
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            while (true) {
                try {
                    T entity = (T) inputStream.readObject();
                    entities.add(entity);
                } catch (EOFException e) {
                    break; // Fin du fichier atteinte
                }
            }
        }

        return entities;
    }

    public Optional<T> findById(ID id) throws IOException, ClassNotFoundException {
        return findAll().stream().filter(entity -> {
            try {
                return id.equals(idField.get(entity));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }).findFirst();
    }

    public void save(T entity) throws IOException, ClassNotFoundException {
        List<T> entities = findAll();
        boolean exists = entities.stream().anyMatch(e -> {
            try {
                return idField.get(e).equals(idField.get(entity));
            } catch (IllegalAccessException ex) {
                throw new RuntimeException(ex);
            }
        });

        // Si l'entité n'existe pas et l'auto-incrémentation est activée
        if (!exists && autoIncrement) {
            setAutoIncrementedId(entity, entities);
        }

        if (!exists) {
            File file = new File(filename);
            boolean append = file.exists() && file.length() > 0;

            try (ObjectOutputStream outputStream = append ? new AppendableObjectOutputStream(new FileOutputStream(filename, true)) : new ObjectOutputStream(new FileOutputStream(filename))) {
                outputStream.writeObject(entity);
            }
        }
    }

    // Méthode pour définir l'ID autoincrémenté
    private void setAutoIncrementedId(T entity, List<T> entities) {
        try {
            int maxId = entities.stream()
                    .mapToInt(e -> {
                        try {
                            return (int) idField.get(e);
                        } catch (IllegalAccessException ex) {
                            throw new RuntimeException(ex);
                        }
                    })
                    .max()
                    .orElse(0); // Si aucun ID trouvé, on commence à 0

            // Attribuer le prochain ID à l'entité
            idField.set(entity, maxId + 1);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Erreur lors de l'attribution de l'ID auto-incrémenté", e);
        }
    }

    public void saveAll(List<T> entities) throws IOException, ClassNotFoundException {
        for (T entity : entities) {
            save(entity);
        }
    }

    public void update(T entity) throws IOException, ClassNotFoundException {
        List<T> entities = findAll();
        boolean updated = false;

        for (int i = 0; i < entities.size(); i++) {
            try {
                if (idField.get(entities.get(i)).equals(idField.get(entity))) {
                    entities.set(i, entity);
                    updated = true;
                    break;
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

        if (updated) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
                for (T e : entities) {
                    outputStream.writeObject(e);
                }
            }
        } else {
            System.out.println("Entité non trouvée pour la mise à jour : " + entity);
        }
    }

    public boolean deleteById(ID id) throws IOException, ClassNotFoundException {
        List<T> entities = findAll();
        boolean removed = entities.removeIf(entity -> {
            try {
                return id.equals(idField.get(entity));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        });

        if (removed) {
            try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(filename))) {
                for (T e : entities) {
                    outputStream.writeObject(e);
                }
            }
        } else {
            System.out.println("Entité non trouvée pour suppression avec ID : " + id);
        }
        return true;
    }

    // Classe interne pour gérer l'ObjectOutputStream appendable
    static class AppendableObjectOutputStream extends ObjectOutputStream {
        public AppendableObjectOutputStream(OutputStream out) throws IOException {
            super(out);
        }

        @Override
        protected void writeStreamHeader() throws IOException {
            reset(); // Empêche l'écriture d'un nouvel en-tête lors de l'ajout
        }
    }
}
