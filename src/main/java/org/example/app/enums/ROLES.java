package org.example.app.enums;

public enum ROLES {
    USER("user"),
    ADMIN("admin");

    public final String name;

    ROLES(String name) {
        this.name = name;
    }
}
