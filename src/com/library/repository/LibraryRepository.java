package com.library.repository;

import com.library.entity.Library;

// Création du dépôt LibraryRepository qui étend FileRepository
public class LibraryRepository extends FileRepository<Library, Integer> {

    public LibraryRepository() {
        super(Library.class, "id");
    }
}
