package com.lee93.coc.srcurity;

import java.security.SecureRandom;
import java.util.Base64;

public class SecretKeyGenerator {

    public static String generateSecretKey() {
        SecureRandom random = new SecureRandom();
        byte[] ketBytes = new byte[32];
        random.nextBytes(ketBytes);
        return Base64.getEncoder().encodeToString(ketBytes);
    }

    public static void main(String[] args) {
        String secretKey = generateSecretKey();
        System.out.println("Generated SECRET_KEY ::: " + secretKey);
    }
}
