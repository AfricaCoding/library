package com.library.repository;

import com.library.entity.Book;

// Création du dépôt LibraryRepository qui étend FileRepository
public class BookRepository extends FileRepository<Book, Integer> {

    public BookRepository() {
        super(Book.class, "id");
    }
}
