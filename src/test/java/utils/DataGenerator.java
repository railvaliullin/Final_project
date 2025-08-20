package utils;

import java.util.UUID;

public class DataGenerator {
    public static String generateEmail() {
        return "user_" + UUID.randomUUID() + "@test.com";
    }
}

