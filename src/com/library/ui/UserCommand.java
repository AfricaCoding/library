package com.library.ui;

import java.util.Arrays;

public enum UserCommand {
    ONE("1"),
    TWO("2"),
    THREE("3"),
    YES("oui"),
    NO("non");

    private final String code;

    UserCommand(String code) {
        this.code = code;
    }

    public static UserCommand getCommand(String code) {
        return Arrays.stream(UserCommand.values())
                .filter(value -> value.code.equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Cette commande n'existe pas"));
    }
}
