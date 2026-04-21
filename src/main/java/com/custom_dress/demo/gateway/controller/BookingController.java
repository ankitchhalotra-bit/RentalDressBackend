package com.custom_dress.demo.gateway.controller;

import com.custom_dress.demo.gateway.dto.BookingRequestDTO;
import com.custom_dress.demo.gateway.model.Booking;
import com.custom_dress.demo.gateway.service.BookingService;
import com.custom_dress.demo.utility.JwtUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    private final BookingService bookingService;
    private final JwtUtil jwtUtil;

    public BookingController(BookingService bookingService, JwtUtil jwtUtil) {
        this.bookingService = bookingService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping
    public ResponseEntity<Booking> createBooking(
            @RequestHeader("Authorization") String authHeader,
            @RequestBody BookingRequestDTO requestDTO) {
        
        String userId = jwtUtil.extractUserId(authHeader.replace("Bearer ", ""));
        Booking booking = bookingService.createBooking(userId, requestDTO);
        
        return ResponseEntity.ok(booking);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getBookingById(@PathVariable String id) {
        return ResponseEntity.ok(bookingService.getBookingById(id));
    }

    @GetMapping
    public ResponseEntity<List<Booking>> getUserBookings(
            @RequestHeader("Authorization") String authHeader) {
        
        String userId = jwtUtil.extractUserId(authHeader.replace("Bearer ", ""));
        return ResponseEntity.ok(bookingService.getUserBookings(userId));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Booking> updateStatus(
            @PathVariable String id,
            @RequestBody Map<String, String> request) {
        
        String status = request.get("status");
        return ResponseEntity.ok(bookingService.updateBookingStatus(id, status));
    }

    @PutMapping("/{id}/payment")
    public ResponseEntity<Booking> updatePayment(
            @PathVariable String id,
            @RequestBody Map<String, String> request) {
        
        String paymentStatus = request.get("paymentStatus");
        String paymentId = request.get("paymentId");
        
        return ResponseEntity.ok(bookingService.updatePaymentStatus(id, paymentStatus, paymentId));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<Booking> cancelBooking(@PathVariable String id) {
        return ResponseEntity.ok(bookingService.cancelBooking(id));
    }

    @GetMapping("/check-availability")
    public ResponseEntity<Map<String, Boolean>> checkAvailability(
            @RequestParam String dressId,
            @RequestParam Long startDate,
            @RequestParam Long endDate) {
        
        boolean available = bookingService.isDateRangeAvailable(dressId, startDate, endDate);
        return ResponseEntity.ok(Map.of("available", available));
    }

    // Admin endpoints
    @GetMapping("/admin/all")
    public ResponseEntity<List<Booking>> getAllBookings() {
        return ResponseEntity.ok(bookingService.getBookingsByStatus("CONFIRMED"));
    }

    @GetMapping("/admin/by-status/{status}")
    public ResponseEntity<List<Booking>> getBookingsByStatus(@PathVariable String status) {
        return ResponseEntity.ok(bookingService.getBookingsByStatus(status));
    }

    @GetMapping("/admin/dress/{dressId}")
    public ResponseEntity<List<Booking>> getDressBookings(@PathVariable String dressId) {
        return ResponseEntity.ok(bookingService.getDressBookings(dressId));
    }
}

