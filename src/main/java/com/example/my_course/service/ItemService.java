package com.example.my_course.service;
import com.example.my_course.entity.Cart;
import com.example.my_course.entity.Item;
import com.example.my_course.exception.ItemAlreadyExistsException;
import com.example.my_course.repository.CartRepository;
import com.example.my_course.repository.ItemRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ItemService {
    private static final Logger logger = LoggerFactory.getLogger(ItemService.class);

    private final ItemRepository itemRepository;
    private final CartRepository cartRepository;

    public ItemService(ItemRepository itemRepository, CartRepository cartRepository) {
        this.itemRepository = itemRepository;
        this.cartRepository = cartRepository;
    }

    public Item addToCart(String userEmail, Long courseId, Double price) {
        logger.info("Searching cart for user: {}", userEmail);
        Cart cart = cartRepository.findByStudentEmail(userEmail);
        if (cart == null) {
            throw new RuntimeException("Cart not found for user: " + userEmail);
        }

        logger.info("Checking if item exists: cartId={}, courseId={}", cart.getCartId(), courseId);
        Item existingItem = itemRepository.findByCartAndCourseId(cart, courseId);

        if (existingItem != null) {
            throw new ItemAlreadyExistsException("Item already in cart");
        }

        LocalDateTime addedAt = LocalDateTime.now();
        Item newItem = new Item(cart, courseId, price, addedAt);

        logger.info("New item! Saving: {}", newItem);
        itemRepository.save(newItem);

        //Update total amount
        cart.updateTotalAmount();
        cartRepository.save(cart);

        return newItem;
    }

    public Long getCartIdByUserEmail(String userEmail) {
        logger.info("Searching cart for user: {}", userEmail);
        Cart cart = cartRepository.findByStudentEmail(userEmail);
        if (cart == null) {
            throw new RuntimeException("Cart not found for user: " + userEmail);
        }

        // Return the cartId
        logger.info("Found cart for user: {}, cartId: {}", userEmail, cart.getCartId());
        return cart.getCartId();
    }

    public List<Long> getCourseIdsByCart(Cart cart) {
        List<Item> items = itemRepository.findByCart(cart);
        List<Long> courseIds = new ArrayList<>();

        for (int i = 0; i < items.size(); i++) {
            courseIds.add(items.get(i).getCourseId());
        }
        System.out.println(":::Course IDs: " + courseIds);
        return courseIds;
    }

    public void removeCourseFromCart(Long cartId, Long courseId) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart not found"));

        Item item = itemRepository.findByCartAndCourseId(cart, courseId);
        if (item == null) {
            throw new RuntimeException("Item not found in cart");
        }

        itemRepository.delete(item);
    }
}
