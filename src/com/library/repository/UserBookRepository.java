package com.library.repository;

import com.library.annotations.FileDesc;
import com.library.entity.UserBook;
import com.library.utils.AnnotationUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserBookRepository implements Repository<UserBook, Integer> {

    private final String filename;

    public UserBookRepository() {
        Class<UserBook> userBookClass = UserBook.class;
        this.filename = AnnotationUtils.createAndGetName(userBookClass);
    }

    @Override
    public List<UserBook> findAll() throws IOException, ClassNotFoundException {
        List<UserBook> userBooks = new ArrayList<>();

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            while (true) {
                try {
                    UserBook userBook = (UserBook) inputStream.readObject();
                    userBooks.add(userBook);
                } catch (EOFException e) {
                    // Fin du fichier atteinte
                    break;
                }
            }
        } catch (FileNotFoundException e) {
            // Si le fichier n'existe pas encore, on retourne une liste vide
            System.out.println("Fichier non trouvé, une liste vide sera retournée.");
        }

        return userBooks;
    }

    @Override
    public void save(UserBook userBook) throws IOException, ClassNotFoundException {
        if (findAll()==null || !findAll().contains(userBook)) {
            File file = new File(filename);
            boolean append = file.exists() && file.length() > 0;
            try (ObjectOutputStream outputStream = append ?
                    new AppendableObjectOutputStream(new FileOutputStream(filename, true)) :
                    new ObjectOutputStream(new FileOutputStream(filename))) {

                outputStream.writeObject(userBook);
            }
        }
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        UserBookRepository repository = new UserBookRepository();
        List<UserBook> userBooks = List.of(
                new UserBook(1, 1, 1),
                new UserBook(2, 1, 3),
                new UserBook(3, 2, 2),
                new UserBook(4, 3, 4));
        userBooks.forEach(userBook -> {
            try {
                repository.save(userBook);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        });
        List<UserBook> all = repository.findAll();
        System.out.println(all);
    }
}
