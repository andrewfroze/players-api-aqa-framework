package org.andrewfroze.utils;

import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String DIGITS = "0123456789";
    private static final String ALL_ALLOWED = LETTERS + DIGITS;
    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(int lengthFrom, int lengthTo) {
        int length = random.nextInt(lengthFrom,lengthTo + 1);

        StringBuilder password = new StringBuilder(length);

        password.append(LETTERS.charAt(random.nextInt(LETTERS.length())));
        password.append(DIGITS.charAt(random.nextInt(DIGITS.length())));

        for (int i = 2; i < length; i++) {
            password.append(ALL_ALLOWED.charAt(random.nextInt(ALL_ALLOWED.length())));
        }

        return shuffleString(password.toString());
    }

    private static String shuffleString(String input) {
        char[] chars = input.toCharArray();
        for (int i = chars.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            char tmp = chars[i];
            chars[i] = chars[j];
            chars[j] = tmp;
        }
        return new String(chars);
    }
}
