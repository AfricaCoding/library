package com.library.utils;

import com.library.entity.Book;
import com.library.entity.Library;
import com.library.entity.User;
import com.library.entity.UserBook;
import com.library.repository.BookRepository;
import com.library.repository.LibraryRepository;
import com.library.repository.UserBookRepository;
import com.library.repository.UserRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataGenerator {
    private static final LibraryRepository libraryRepository = new LibraryRepository();
    private static final BookRepository bookRepository = new BookRepository();
    private static final UserRepository userRepository = new UserRepository();
    private static final UserBookRepository userBookRepository = new UserBookRepository();


    public static void inti() throws IOException, ClassNotFoundException {
        generateBooks();
        generateUsers();
        generateUserBook();

    }

    public static void generateBooks() throws IOException, ClassNotFoundException {
        if (bookRepository.findAll().isEmpty()) {
            Library library = new Library(1, "Tech Library");

            List<Book> books = new ArrayList<>();
            books.add(new Book("Clean Code", library));
            books.add(new Book("The Pragmatic Programmer", library));
            books.add(new Book("Design Patterns: Elements of Reusable Object-Oriented Software", library));
            books.add(new Book("Refactoring: Improving the Design of Existing Code", library));
            books.add(new Book("Effective Java", library));
            books.add(new Book("Java Concurrency in Practice", library));
            books.add(new Book("Head First Design Patterns", library));
            books.add(new Book("Code Complete", library));
            books.add(new Book("Introduction to Algorithms", library));
            books.add(new Book("The Art of Computer Programming", library));
            books.add(new Book("Cracking the Coding Interview", library));
            books.add(new Book("You Don't Know JS", library));
            books.add(new Book("JavaScript: The Good Parts", library));
            books.add(new Book("Python Crash Course", library));
            books.add(new Book("Fluent Python", library));
            books.add(new Book("Eloquent JavaScript", library));
            books.add(new Book("Grokking Algorithms", library));
            books.add(new Book("Learning Python", library));
            books.add(new Book("Python Cookbook", library));
            books.add(new Book("Effective Python", library));
            books.add(new Book("JavaScript: The Definitive Guide", library));
            books.add(new Book("The Go Programming Language", library));
            books.add(new Book("Programming Rust", library));
            books.add(new Book("The Rust Programming Language", library));
            books.add(new Book("Kotlin in Action", library));
            books.add(new Book("Spring in Action", library));
            books.add(new Book("Pro Spring Boot", library));
            books.add(new Book("Scala Programming Projects", library));
            books.add(new Book("Mastering Blockchain", library));
            books.add(new Book("The Blockchain Developer", library));
            books.add(new Book("Building Microservices", library));
            books.add(new Book("Microservices Patterns", library));
            books.add(new Book("Architecting for Scale", library));
            books.add(new Book("Cloud Native Java", library));
            books.add(new Book("Hands-On Microservices with Kotlin", library));
            books.add(new Book("Learning React", library));
            books.add(new Book("React Up & Running", library));
            books.add(new Book("Vue.js Up & Running", library));
            books.add(new Book("Pro Git", library));
            books.add(new Book("Docker Deep Dive", library));
            books.add(new Book("Kubernetes Up & Running", library));
            books.add(new Book("Learning GraphQL", library));
            books.add(new Book("GraphQL in Action", library));
            books.add(new Book("Mastering Git", library));
            books.add(new Book("CI/CD Pipelines with Jenkins", library));
            books.add(new Book("DevOps Handbook", library));
            books.add(new Book("Site Reliability Engineering", library));
            books.add(new Book("The Phoenix Project", library));
            books.add(new Book("The Unicorn Project", library));
            books.add(new Book("Infrastructure as Code", library));
            library.addBooks(books);
            libraryRepository.save(library);
            bookRepository.saveAll(books);
        }
    }


    public static void generateUsers() throws IOException, ClassNotFoundException {
        if (userRepository.findAll().isEmpty()) {
            List<User> users = new ArrayList<>();

            users.add(new User("John Doe"));
            users.add(new User("Jane Smith"));
            users.add(new User("Michael Johnson"));
            users.add(new User("Emily Davis"));
            users.add(new User("William Brown"));
            users.add(new User("Olivia Wilson"));
            users.add(new User("James Taylor"));
            users.add(new User("Sophia Martinez"));
            users.add(new User("Benjamin Anderson"));
            users.add(new User("Mia Thomas"));
            users.add(new User("Lucas Jackson"));
            users.add(new User("Ava White"));
            users.add(new User("Elijah Harris"));
            users.add(new User("Amelia Martin"));
            users.add(new User("Henry Thompson"));
            users.add(new User("Isabella Garcia"));
            users.add(new User("Alexander Moore"));
            users.add(new User("Charlotte Lee"));
            users.add(new User("Daniel King"));
            users.add(new User("Evelyn Clark"));

            userRepository.saveAll(users);
        }
    }

    public static void generateUserBook() throws IOException, ClassNotFoundException {
        if (userBookRepository.findAll().isEmpty()) {
            List<UserBook> users = new ArrayList<>();

            users.add(new UserBook(bookRepository.findById(1).get(), userRepository.findById(1).get()));
            users.add(new UserBook(bookRepository.findById(2).get(), userRepository.findById(1).get()));
            users.add(new UserBook(bookRepository.findById(3).get(), userRepository.findById(1).get()));
            users.add(new UserBook(bookRepository.findById(4).get(), userRepository.findById(2).get()));

            userBookRepository.saveAll(users);
        }
    }
}
