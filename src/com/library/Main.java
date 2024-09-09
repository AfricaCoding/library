package com.library;

import com.library.entity.Library;
import com.library.repository.BookRepository;
import com.library.repository.LibraryRepository;
import com.library.repository.UserBookRepository;
import com.library.repository.UserRepository;
import com.library.service.LibraryService;
import com.library.ui.UserInteraction;
import com.library.utils.DataGenerator;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ClassNotFoundException {

        // Initialisation des données
        DataGenerator.inti();
        LibraryService libraryService = new LibraryService(
                new LibraryRepository(), new BookRepository(), new UserRepository(), new UserBookRepository()
        );
        Library library = libraryService.findById(1);

        // Interface utilisateur
        UserInteraction userInteraction = new UserInteraction(libraryService, library);
        userInteraction.startInteraction();
    }
}
