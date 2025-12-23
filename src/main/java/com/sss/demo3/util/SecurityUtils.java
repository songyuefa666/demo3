package com.sss.demo3.util;

import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;

public class SecurityUtils {

    private static final int SALT_LENGTH = 16;
    private static final String DELIMITER = "$";

    /**
     * Generate a salt
     */
    public static String generateSalt() {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[SALT_LENGTH];
        random.nextBytes(salt);
        return Base64.getEncoder().encodeToString(salt);
    }

    /**
     * Hash password with salt using SHA-256
     */
    public static String hashPassword(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(Base64.getDecoder().decode(salt));
            byte[] hashedPassword = md.digest(password.getBytes("UTF-8"));
            return Base64.getEncoder().encodeToString(hashedPassword);
        } catch (Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    /**
     * Verify password
     * storedPassword format expected: salt$hash (or just handle salt separately if stored in separate column)
     * For this project, let's assume we store "salt$hash" in the password column to avoid schema change if possible?
     * No, user said "Don't generate SQL", but I can change how data is stored in the string column.
     * The password column is VARCHAR(100).
     * Salt (Base64 of 16 bytes) is ~24 chars.
     * Hash (Base64 of 32 bytes) is ~44 chars.
     * Total ~69 chars. Fits in VARCHAR(100).
     */
    public static String encrypt(String password) {
        String salt = generateSalt();
        String hash = hashPassword(password, salt);
        return salt + DELIMITER + hash;
    }

    /**
     * Determine whether the stored value is in the expected salt$hash format.
     */
    public static boolean isEncryptedFormat(String storedValue) {
        if (storedValue == null || !storedValue.contains(DELIMITER)) {
            return false;
        }
        String[] parts = storedValue.split("\\$", -1);
        return parts.length == 2 && !parts[0].isEmpty() && !parts[1].isEmpty();
    }

    public static boolean verify(String password, String storedValue) {
        if (!isEncryptedFormat(storedValue)) {
            return false;
        }
        String[] parts = storedValue.split("\\$");
        String salt = parts[0];
        String hash = parts[1];
        String computedHash = hashPassword(password, salt);
        return computedHash.equals(hash);
    }
}
