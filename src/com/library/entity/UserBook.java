package com.library.entity;

import com.library.annotations.FileDesc;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@FileDesc(filename = "userbook.txt")
public class UserBook implements Serializable {

    @Serial
    private static final long serialVersionUID =1L;
    private int id;
    private int userId;
    private int bookId;

    public UserBook() {
    }

    public UserBook(int id, int userId, int bookId) {
        this.id = id;
        this.userId = userId;
        this.bookId = bookId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserBook userBook = (UserBook) o;
        return id == userBook.id && userId == userBook.userId && bookId == userBook.bookId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, bookId);
    }

    @Override
    public String toString() {
        return id + "," + userId + "," + bookId;
    }
}
