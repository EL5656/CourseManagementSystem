package com.example.my_course.config;

import com.paypal.base.rest.APIContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfig {
    private final PayPalConfig payPalConfig;

    public PaymentConfig(PayPalConfig payPalConfig) {
        this.payPalConfig = payPalConfig;
    }

    @Bean
    public APIContext apiContext() {
        return new APIContext(
                payPalConfig.getClientId(),
                payPalConfig.getSecretKey(),
                payPalConfig.getMode()
        );
    }
}
