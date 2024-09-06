package com.library.entity;

import com.library.annotations.FileDesc;

import java.io.Serializable;
import java.util.Objects;

@FileDesc(filename="libraries.txt")
public class Library implements Serializable {

    private int id;
    private String name;

    public Library() {
    }

    public Library(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return  id + "," + name +"\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Library library = (Library) o;
        return id == library.id && Objects.equals(name, library.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
