package com.example.my_course.sqlandother;

import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Base64;

public class EncryptedKeyGenerator {
        public static void main(String[] args) {
            // Generate a 256-bit key for HS256
            Key secretKey = Keys.secretKeyFor(io.jsonwebtoken.SignatureAlgorithm.HS256);

            // Encode the key in Base64 to store it securely
            String encodedKey = Base64.getEncoder().encodeToString(secretKey.getEncoded());

            // Print out the Base64 encoded key
            System.out.println("Generated JWT Secret Key (Base64): " + encodedKey);
        }
}
