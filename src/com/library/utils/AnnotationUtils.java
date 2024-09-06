package com.library.utils;

import com.library.annotations.FileDesc;

import java.io.File;
import java.io.IOException;

public class AnnotationUtils {

    public static <T> String createAndGetName(Class<T> t) {
        String filename = null;
        if (t.isAnnotationPresent(FileDesc.class)) {
            FileDesc annotation = t.getAnnotation(FileDesc.class);
            filename = annotation.filename();
        }

        // Création du fichier s'il n'existe pas
        assert filename != null;
        File file = new File(filename);
        if (!file.exists()) {
            try {
                if (file.createNewFile()) {
                    System.out.println("Fichier " + filename + " créé avec succès.");
                } else {
                    System.out.println("Le fichier " + filename + " existe déjà.");
                }
            } catch (IOException e) {
                System.err.println("Erreur lors de la création du fichier : " + e.getMessage());
            }
        }
        return filename;
    }
}
