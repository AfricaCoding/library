package com.library.repository;

import com.library.annotations.FileDesc;
import com.library.entity.Book;
import com.library.entity.Library;

import java.awt.font.TextHitInfo;
import java.io.*;
import java.util.List;

public class BookRepository implements Repository<Book, Integer>{

    private String filename;

    public BookRepository() {
        Class<Book> bookClass = Book.class;
        if (bookClass.isAnnotationPresent(FileDesc.class)) {
            FileDesc annotation = bookClass.getAnnotation(FileDesc.class);
            this.filename = annotation.filename();
        }
    }

    @Override
    public List<Book> findAll() throws IOException {
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }
        }
        return List.of();
    }

    @Override
    public void save(Book book) throws IOException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(book.toString());
        }
    }

    public static void main(String[] args) throws IOException {
        BookRepository bookRepository = new BookRepository();
        Book book1 = new Book(1, "DS IN JAVA", 1);
        Book book2 = new Book(2, "DS IN PYTHON", 2);
        Book book3 = new Book(3, "DS IN KOTLIN", 3);
        bookRepository.save(book1);
        bookRepository.save(book2);
        bookRepository.save(book3);

        bookRepository.findAll();
    }
}
