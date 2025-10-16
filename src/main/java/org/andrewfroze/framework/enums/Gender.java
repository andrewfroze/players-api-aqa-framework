package org.andrewfroze.framework.enums;

public enum Gender {

    MALE,
    FEMALE;

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}
