package com.library.repository;

import com.library.entity.User;

// Création du dépôt LibraryRepository qui étend FileRepository
public class UserRepository extends FileRepository<User, Integer> {

    public UserRepository() {
        super(User.class, "id");
    }
}
