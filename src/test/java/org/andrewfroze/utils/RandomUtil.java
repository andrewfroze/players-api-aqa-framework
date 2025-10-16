package org.andrewfroze.utils;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class RandomUtil {

    private static final Random random = new Random();

    public static <T> T getRandomValueFromArray(T[] inputArray) {
        return inputArray[random.nextInt(inputArray.length)];
    }

    public static <T> T getRandomValueFromList(List<T> inputList) {
        return inputList.get(random.nextInt(inputList.size()));
    }

    public static <T> T getRandomValueFromListExceptValue(List<T> inputList, T exceptValue) {
        return getRandomValueFromStreamExceptValue(inputList.stream(), exceptValue);
    }

    public static <T> T getRandomValueFromArrayExceptValue(T[] inputArray, T exceptValue) {
        return getRandomValueFromStreamExceptValue(Arrays.stream(inputArray), exceptValue);
    }

    private static <T> T getRandomValueFromStreamExceptValue(Stream<T> inputStream, T exceptValue) {
        List<T> filteredList = inputStream
                .filter(item -> !item.equals(exceptValue))
                .toList();

        if (filteredList.isEmpty()) {
            throw new IllegalArgumentException("No elements left after excluding the specified value");
        }

        return filteredList.get(random.nextInt(filteredList.size()));
    }
}
