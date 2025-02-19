package com.example.my_course.controller;
import com.example.my_course.entity.Item;
import com.example.my_course.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/item")
@CrossOrigin(origins = "http://localhost:3000")
public class ItemController {
    private static final Logger logger = LoggerFactory.getLogger(ItemController.class);

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    @PostMapping("/addToCart")
    public ResponseEntity<?> addToCart(@RequestParam String userEmail,
                                       @RequestParam Long courseId,
                                       @RequestParam Double price) {
        logger.info("Received request: userEmail={}, courseId={}, price={}", userEmail, courseId, price);

        try {
            Item item = itemService.addToCart(userEmail, courseId, price);
            logger.info("Item processed successfully: {}", item);
            return ResponseEntity.ok("Item added successfully!");
        } catch (RuntimeException e) {
            logger.warn("Item already exists in the cart");
            return ResponseEntity.ok("Item already exists in the cart."); // Return 200 OK with message
        }
    }

    @GetMapping("/cartId")
    public ResponseEntity<Map<String, Long>> getCartId(@RequestParam String userEmail) {
        Long cartId = itemService.getCartIdByUserEmail(userEmail);
        if (cartId == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Avoids returning an error page
        }
        Map<String, Long> response = new HashMap<>();
        response.put("cartId", cartId);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{cartId}/remove/{courseId}")
    public ResponseEntity<String> removeCourseFromCart(@PathVariable Long cartId, @PathVariable Long courseId) {
        logger.info("Received DELETE request: cartId={}, courseId={}", cartId, courseId);

        if (cartId == null || courseId == null) {
            logger.error("Invalid parameters: cartId={}, courseId={}", cartId, courseId);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid request parameters");
        }

        try {
            itemService.removeCourseFromCart(cartId, courseId);
            return ResponseEntity.ok("Course removed from cart successfully");
        } catch (RuntimeException e) {
            logger.error("Error removing course:", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

}
