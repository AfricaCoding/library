package com.library.service;

import com.library.entity.Book;
import com.library.repository.BookRepository;

import java.io.IOException;
import java.util.List;

public class BookService {

    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Create a new book
    public void createBook(Book book) throws IOException, ClassNotFoundException {
        bookRepository.save(book);
        System.out.println("Le livre " + book.getName() + " est ajouté");
    }

    // Find a book by id
    public Book getBookById(int id) throws IOException, ClassNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Livre non trouvé"));
    }

    // Get all books
    public List<Book> getAllBooks() throws IOException, ClassNotFoundException {
        return bookRepository.findAll();
    }

    // Update an existing book
    public void updateBook(Book book) throws IOException, ClassNotFoundException {
        bookRepository.update(book);
        System.out.println("Le livre " + book.getName() + " est edité");
    }

    // Delete a book by id
    public boolean deleteBook(int id) throws IOException, ClassNotFoundException {
        return bookRepository.deleteById(id);
    }
}
