package com.library.entity;

import com.library.annotations.FileDesc;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@FileDesc(filename = "user_book", autocrement = true)
public class UserBook implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private Book books;
    private User user;

    public UserBook() {
    }

    public UserBook(Book books, User user) {
        this.books = books;
        this.user = user;
    }

    public UserBook(int id, Book books, User user) {
        this.id = id;
        this.books = books;
        this.user = user;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Book getBooks() {
        return books;
    }

    public void setBooks(Book books) {
        this.books = books;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBook userBook = (UserBook) o;
        return id == userBook.id && Objects.equals(books, userBook.books) && Objects.equals(user, userBook.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, books, user);
    }

    @Override
    public String toString() {
        return "UserBook{" +
                "id=" + id +
                ", books=" + books +
                ", user=" + user +
                '}';
    }
}
