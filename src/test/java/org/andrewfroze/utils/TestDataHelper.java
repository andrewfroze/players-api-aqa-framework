package org.andrewfroze.utils;

import org.andrewfroze.framework.enums.Gender;
import org.andrewfroze.framework.enums.UserRole;

import java.util.List;
import java.util.Random;

import static org.andrewfroze.framework.enums.UserRole.ADMIN;
import static org.andrewfroze.framework.enums.UserRole.USER;
import static org.andrewfroze.utils.RandomUtil.getRandomValueFromArray;

public class TestDataHelper {

    public static int getAllowedAge() {
        return new Random().nextInt(16, 60);
    }

    public static Gender getRandomGender() {
        return getRandomValueFromArray(Gender.values());
    }

    public static String getNewLogin() {
        return "testLogin_" + System.nanoTime();
    }

    public static String getValidPassword() {
        return PasswordGenerator.generatePassword(7, 15);
    }

    public static UserRole getUserRoleToCreateUser() {
        return RandomUtil.getRandomValueFromList(List.of(ADMIN, USER));
    }

    public static String getNewScreenName() {
        return "testScreenName_" + System.nanoTime();
    }
}
