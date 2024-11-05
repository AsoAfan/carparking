package org.example.enums;

public enum ROLES {
    USER("user"),
    ADMIN("admin");

     public final String name;
    ROLES(String name) {
        this.name = name;
    }
}
