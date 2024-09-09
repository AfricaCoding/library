package com.library.entity;

import com.library.annotations.FileDesc;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@FileDesc(autocrement = true)
public class Book implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;
    private int id;
    private String name;
    private int libraryId;
    private Library library;

    public Book() {
    }

    public Book(String name, Library library) {
        this.name = name;
        this.library = library;
    }

    public Book(int id, String name, Library library) {
        this.id = id;
        this.name = name;
        this.library = library;
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

    public Library getLibrary() {
        return library;
    }

    public void setLibrary(Library library) {
        this.library = library;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, libraryId);
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", libraryId=" + libraryId +
                '}';
    }
}
