package com.library.entity;

import com.library.annotations.FileDesc;

import java.io.Serializable;
import java.util.Objects;

@FileDesc(filename="books.txt")
public class Book implements Serializable {

    private int id;
    private String name;
    private int libraryId;

    public Book() {
    }

    public Book(int id, String name, int libraryId) {
        this.id = id;
        this.name = name;
        this.libraryId = libraryId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLibraryId() {
        return libraryId;
    }

    public void setLibraryId(int libraryId) {
        this.libraryId = libraryId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && libraryId == book.libraryId && Objects.equals(name, book.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, libraryId);
    }
}
