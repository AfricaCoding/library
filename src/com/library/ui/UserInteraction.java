package com.library.ui;

import com.library.entity.Book;
import com.library.entity.Library;
import com.library.entity.User;
import com.library.service.LibraryService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserInteraction {
    private final LibraryService libraryService;
    private final Library library;

    public UserInteraction(LibraryService libraryService, Library library) {
        this.libraryService = libraryService;
        this.library = library;
    }

    public void startInteraction() throws IOException, ClassNotFoundException {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Bienvenue à la bibliothèque: " + library.getName());
            displayMenu();

            String userInput = sc.nextLine();
            handleUserInput(userInput, sc);
        }
    }

    private void displayMenu() {
        System.out.println("1. Voir la liste des livres");
        System.out.println("2. Voir la liste des utilisateurs");
        System.out.println("3. Prendre un livre");
        System.out.println("Choisissez une option:");
    }

    private void handleUserInput(String userInput, Scanner sc) throws IOException, ClassNotFoundException {
        switch (UserCommand.getCommand(userInput)) {
            case ONE:
                displayBooks();
                break;
            case TWO:
                displayUsers();
                break;
            case THREE:
                handleBookBorrowing(sc);
                break;
            default:
                System.out.println("Commande inconnue");
                break;
        }
    }

    private void displayBooks() throws IOException, ClassNotFoundException {
        List<Book> allBooks = libraryService.getAllBooks();
        allBooks.forEach(book -> System.out.printf("id: %s, name: %s\n", book.getId(), book.getName()));
    }

    private void displayUsers() throws IOException, ClassNotFoundException {
        List<User> users = libraryService.getAllusers();
        users.forEach(user -> System.out.printf("id: %s, name: %s\n", user.getId(), user.getName()));
    }

    private void handleBookBorrowing(Scanner sc) throws IOException, ClassNotFoundException {
        System.out.print("Votre nom: ");
        String name = sc.nextLine();
        Optional<User> optionalUser = libraryService.getUserByName(name);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            System.out.println("Bonjour cher abonné " + user.getName() + "! Très ravi de vous revoir!");
            borrowBook(sc, user);
        } else {
            registerNewUser(sc);
        }
    }

    private void registerNewUser(Scanner sc) throws IOException, ClassNotFoundException {
        System.out.println("Vous n'êtes pas enregistré. Voulez-vous être enregistré ? (oui/non)");
        String yesOrNo = sc.nextLine();
        switch (UserCommand.getCommand(yesOrNo)) {
            case YES:
                System.out.print("Entrez votre nom : ");
                String newName = sc.nextLine();
                User newUser = new User(newName);
                library.addUser(newUser);
                libraryService.addUser(library);
                borrowBook(sc, newUser);
                break;
            case NO:
                System.out.println("Au revoir");
                break;
        }
    }

    private void borrowBook(Scanner sc, User user) throws IOException, ClassNotFoundException {
        System.out.println("Quel livre souhaitez-vous emprunter? Écrivez le nom:");
        String bookName = sc.nextLine();
        List<Book> foundBooks = libraryService.getBookByName(bookName);
        if (bookName.equals("\\q")) {
            return;
        }
        if (!foundBooks.isEmpty()) {
            System.out.println("***** Livres trouvés:");
            foundBooks.forEach(book -> System.out.println(book.getName()));
            if (foundBooks.size() == 1) {
                libraryService.takeBook(user, foundBooks.getFirst());
                System.out.println("\n*************\nVous avez pris le livre: " + foundBooks.getFirst().getName() + "\n*************\n");
            } else {
                borrowBook(sc, user);
            }
        } else {
            System.out.println("Désolé, nous n'avons pas ce livre.");
        }
    }
}
