package com.example.my_course.service;

import com.example.my_course.entity.Cart;
import com.example.my_course.repository.CartRepository;
import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
public class PaymentService {
    private final APIContext apiContext;
    private final CartRepository cartRepository;

    // Inject APIContext from PaymentConfig
    public PaymentService(APIContext apiContext, CartRepository cartRepository) {
        this.apiContext = apiContext;
        this.cartRepository = cartRepository;
    }

    public Payment createPayment(
            Long cartId,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl
    ) throws PayPalRESTException {
        Cart cart = cartRepository.findByCartId(cartId);
        if (cart == null) {
            throw new RuntimeException(":::Cart not found for: " + cartId);
        }
        Double total = cart.getTotalAmount();

        //from paypal api
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.forLanguageTag(currency), "%.2f", total));

        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = new ArrayList<>();
        transactions.add(transaction);

        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);
        payment.setRedirectUrls(redirectUrls);

        return payment.create(apiContext);
    }

    public Payment executePayment(
            String paymentId,
            String payerId
    ) throws PayPalRESTException {
        Payment payment =new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}
