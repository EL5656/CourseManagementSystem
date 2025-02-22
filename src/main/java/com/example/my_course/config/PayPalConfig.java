package com.example.my_course.config;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.stereotype.Component;

@Component
public class PayPalConfig {
    private static final Dotenv dotenv = Dotenv.load();

    public static String getClientId() {
        return dotenv.get("PAYPAL_CLIENT_ID");
    }

    public static String getSecretKey() {
        return dotenv.get("PAYPAL_SECRET");
    }

    public static String getMode() {
        return dotenv.get("PAYPAL_MODE", "sandbox"); // Default to sandbox
    }

//    public static void main(String[] args) {
//        System.out.println(getClientId());
//        System.out.println(getSecretKey());
//        System.out.println(getMode());
//    }
}
