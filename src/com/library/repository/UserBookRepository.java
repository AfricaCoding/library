package com.library.repository;

import com.library.entity.UserBook;

// Création du dépôt LibraryRepository qui étend FileRepository
public class UserBookRepository extends FileRepository<UserBook, Integer> {

    public UserBookRepository() {
        super(UserBook.class, "id");
    }
}
