package com.hibernate.practice.util;

import lombok.experimental.UtilityClass;

import java.util.Random;

@UtilityClass
public class GeneratorUtils {
    private final Random random = new Random();
    private static final Long DEFAULT_MAX_VALUE = 999999999999999999L;

    public static String generateNumber(int length) {
        return generateNumber(length, length);
    }

    public static String generateNumber(int length, int pairLength) {
        if (length % pairLength != 0) {
            throw new IllegalArgumentException("length must be a multiple of ".concat(String.valueOf(pairLength)));
        }

        StringBuilder number = new StringBuilder(length);
        int pairsCount = (int) Math.ceil(length / (double) pairLength);

        long bound = DEFAULT_MAX_VALUE % (long) Math.pow(10, pairLength);

        random.longs(pairsCount, 0L, bound).forEach(value -> {
            String valueString = String.valueOf(value);
            if (valueString.length() < pairLength) {
                int diff = pairLength - valueString.length();
                valueString = "0".repeat(diff) + valueString;
            }
            number.append(valueString);
        });

        return number.toString();
    }
}
