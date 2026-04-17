package com.custom_dress.demo.gateway.service.user;

import com.custom_dress.demo.gateway.model.Cart;
import com.custom_dress.demo.gateway.repository.CartRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CartService {

    private final CartRepository cartRepository;

    public CartService(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    // Get Cart
    public Cart getCart(String userId) {
        return cartRepository.findByUserId(userId)
                .orElseGet(() -> cartRepository.save(
                        new Cart(null, userId, new ArrayList<>())
                ));
    }

    // Add Dress to Cart
    public Cart addToCart(String userId, String dressId) {

        Cart cart = getCart(userId);

        if (!cart.getDressIds().contains(dressId)) {
            cart.getDressIds().add(dressId);
        }

        return cartRepository.save(cart);
    }

    // Remove Dress from Cart
    public Cart removeFromCart(String userId, String dressId) {

        Cart cart = getCart(userId);
        cart.getDressIds().remove(dressId);

        return cartRepository.save(cart);
    }
}

