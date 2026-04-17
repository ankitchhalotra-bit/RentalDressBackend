package com.custom_dress.demo.gateway.controller.user;

import com.custom_dress.demo.gateway.model.Cart;
import com.custom_dress.demo.gateway.service.user.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    // GET /api/cart?userId=123
    @GetMapping
    public ResponseEntity<Cart> getCart(@RequestParam String userId) {
        return ResponseEntity.ok(cartService.getCart(userId));
    }

    // POST /api/cart/add
    @PostMapping("/add")
    public ResponseEntity<Cart> addToCart(
            @RequestParam String userId,
            @RequestParam String dressId) {

        return ResponseEntity.ok(cartService.addToCart(userId, dressId));
    }

    // DELETE /api/cart/remove/{dressId}?userId=123
    @DeleteMapping("/remove/{dressId}")
    public ResponseEntity<Cart> removeFromCart(
            @RequestParam String userId,
            @PathVariable String dressId) {

        return ResponseEntity.ok(cartService.removeFromCart(userId, dressId));
    }
}
