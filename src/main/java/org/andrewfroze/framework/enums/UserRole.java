package org.andrewfroze.framework.enums;

public enum UserRole {

    SUPERVISOR,
    ADMIN,
    USER;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
