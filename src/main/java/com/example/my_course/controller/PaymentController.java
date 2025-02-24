package com.example.my_course.controller;

import com.example.my_course.service.OrderService;
import com.example.my_course.service.PaymentService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/payment")
public class PaymentController {
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    private final PaymentService paymentService;
    private final OrderService orderService;

    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
    }
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/success")
    public ResponseEntity<Void> paymentSuccess(@RequestParam("paymentId") String paymentId,
                                               @RequestParam("PayerID") String payerId
    ) {
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            if ("approved".equals(payment.getState())) {
                LocalDateTime paidAt = LocalDateTime.now();

                HttpHeaders headers = new HttpHeaders();
                headers.setLocation(URI.create("http://localhost:3000/payment/success?paymentId=" + paymentId + "&PayerID=" + payerId));
                return new ResponseEntity<>(headers, HttpStatus.FOUND); // 302 redirect
            }
        } catch (PayPalRESTException e) {
            logger.error("PayPal payment failed: {}", e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping("/cancel")
    public ResponseEntity<String> paymentCancel(){
        return ResponseEntity.ok("Payment cancelled");
    }

    @GetMapping("/error")
    public ResponseEntity<String>  paymentError(){
        return ResponseEntity.ok("Payment Error");
    }


    @PostMapping("/create")
    public RedirectView createPayment(
            @RequestParam Long cartId
    ){
        try{
            String cancelUrl="http://localhost:8080/payment/cancel";
            String successUrl="http://localhost:8080/payment/success";
            Payment payment = paymentService.createPayment(
                    cartId,
                    "MYR",
                    "paypal",
                    "sale", // Intent (sale, authorize, order)
                    "Course payment",
                    cancelUrl,
                    successUrl
            );
            //links that redirect users
            for(Links links: payment.getLinks()){
                if(links.getRel().equals("approval_url")){//when payment success
                    return new RedirectView(links.getHref());//success or cancel
                }
            }
        }catch(PayPalRESTException e){
            logger.error("PayPal payment failed: {}", e.getMessage(), e);
        }
        return new RedirectView("/payment/error");
    }
}
