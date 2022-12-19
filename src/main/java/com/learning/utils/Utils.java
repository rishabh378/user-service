package com.learning.utils;

import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Random;

@Component
public class Utils {

    private final Random RANDOM = new SecureRandom();
    private final String ALPHABETS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";

    public String generateUserId(int length) {
        return generateRandomId(length);
    }

    public String generateAddressId(int length) {
        return generateRandomId(length);
    }

    private String generateRandomId(int length) {
        StringBuilder secureId = new StringBuilder();
        for (int index = 0; index < length; index ++ ) {
            secureId.append(ALPHABETS.charAt(RANDOM.nextInt(ALPHABETS.length())));
        }
        return new String(secureId);
    }
}
