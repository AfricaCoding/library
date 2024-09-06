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

    public FileRepository(Class<T> entityClass, String idFieldName) {
        this.entityClass = entityClass;

        // Récupérer le nom du fichier à partir de l'annotation @FileDesc
        if (entityClass.isAnnotationPresent(FileDesc.class)) {
            FileDesc fileDesc = entityClass.getAnnotation(FileDesc.class);
            this.filename = fileDesc.filename();
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

        if (!exists) {
            File file = new File(filename);
            boolean append = file.exists() && file.length() > 0;

            try (ObjectOutputStream outputStream = append ? new AppendableObjectOutputStream(new FileOutputStream(filename, true)) : new ObjectOutputStream(new FileOutputStream(filename))) {
                outputStream.writeObject(entity);
            }
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

    public void delete(ID id) throws IOException, ClassNotFoundException {
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
