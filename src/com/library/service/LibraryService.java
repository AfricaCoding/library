package com.library.service;

import com.library.entity.Book;
import com.library.entity.Library;
import com.library.entity.User;
import com.library.entity.UserBook;
import com.library.repository.BookRepository;
import com.library.repository.LibraryRepository;
import com.library.repository.UserBookRepository;
import com.library.repository.UserRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LibraryService {

    private final LibraryRepository libraryRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;
    private final UserBookRepository userBookRepository;

    public LibraryService(LibraryRepository libraryRepository, BookRepository bookRepository, UserRepository userRepository, UserBookRepository userBookRepository) {
        this.libraryRepository = libraryRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.userBookRepository = userBookRepository;
    }

    public Library findById(int id) throws IOException, ClassNotFoundException {
        return libraryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Bibliothèquez non trouvé"));
    }

    /**
     * Books
     */
    public void createBook(Book book) throws IOException, ClassNotFoundException {
        bookRepository.save(book);
        System.out.println("Le livre " + book.getName() + " est ajouté");
    }

    // Find a book by id
    public Book getBookById(int id) throws IOException, ClassNotFoundException {
        return bookRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Livre non trouvé"));
    }


    public List<Book> getBookByName(String name) throws IOException, ClassNotFoundException {
        return bookRepository.findAll().stream()
                .filter(book -> book.getName().toLowerCase().contains(name.toLowerCase())).collect(Collectors.toList());
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


    /**
     * users
     */
    // Create a new user
    public void createUser(User user) throws IOException, ClassNotFoundException {
        userRepository.save(user);
        System.out.println("Le livre " + user.getName() + " est ajouté");
    }

    // Find a user by id
    public User getUserById(int id) throws IOException, ClassNotFoundException {
        return userRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Utilisateur non trouvé"));
    }

    // Find a user by id
    public Optional<User> getUserByName(String name) throws IOException, ClassNotFoundException {
        return userRepository.findAll().stream().filter(user -> user.getName().equalsIgnoreCase(name)).findFirst();
    }

    // Get all users
    public List<User> getAllusers() throws IOException, ClassNotFoundException {
        return userRepository.findAll();
    }

    // Update an existing user
    public void updateUser(User user) throws IOException, ClassNotFoundException {
        userRepository.update(user);
        System.out.println("Le livre " + user.getName() + " est edité");
    }

    // Delete a user by id
    public boolean deleteUser(int id) throws IOException, ClassNotFoundException {
        return userRepository.deleteById(id);
    }

    public void addUser(Library library) throws IOException, ClassNotFoundException {
        libraryRepository.update(library);
        userRepository.saveAll(library.getUsers());
    }

    public void takeBook(User user, Book book) throws IOException, ClassNotFoundException {
        userBookRepository.save(new UserBook(book, user));
    }
}
