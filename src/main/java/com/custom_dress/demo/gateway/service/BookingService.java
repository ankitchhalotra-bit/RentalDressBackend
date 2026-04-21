package com.custom_dress.demo.gateway.service;

import com.custom_dress.demo.gateway.dto.BookingRequestDTO;
import com.custom_dress.demo.gateway.model.Booking;
import com.custom_dress.demo.gateway.model.Dress;
import com.custom_dress.demo.gateway.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final DressService dressService;

    public BookingService(BookingRepository bookingRepository, DressService dressService) {
        this.bookingRepository = bookingRepository;
        this.dressService = dressService;
    }

    public Booking createBooking(String userId, BookingRequestDTO requestDTO) {
        // Validate dress exists
        Dress dress = dressService.getDressById(requestDTO.getDressId());
        
        // Check availability
        if (!dressService.isAvailable(requestDTO.getDressId(), 1)) {
            throw new RuntimeException("Dress is not available for the selected dates");
        }

        // Calculate rental days
        long rentalDays = TimeUnit.MILLISECONDS.toDays(
                requestDTO.getEndDate() - requestDTO.getStartDate()
        );

        if (rentalDays <= 0) {
            throw new RuntimeException("End date must be after start date");
        }

        // Calculate pricing
        Double rentalPrice = dress.getRentalPricePerDay() * rentalDays;
        Double totalAmount = rentalPrice + dress.getDepositAmount();

        // Create booking
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setDressId(requestDTO.getDressId());
        booking.setDressName(dress.getName());
        booking.setStartDate(requestDTO.getStartDate());
        booking.setEndDate(requestDTO.getEndDate());
        booking.setRentalDays((int) rentalDays);
        booking.setRentalPrice(rentalPrice);
        booking.setDepositAmount(dress.getDepositAmount());
        booking.setTotalAmount(totalAmount);
        booking.setSelectedSize(requestDTO.getSelectedSize());
        booking.setSelectedColor(requestDTO.getSelectedColor());
        booking.setDeliveryAddress(requestDTO.getDeliveryAddress());
        booking.setReturnAddress(requestDTO.getReturnAddress());
        booking.setNotes(requestDTO.getNotes());
        booking.setStatus("PENDING");
        booking.setPaymentStatus("PENDING");

        return bookingRepository.save(booking);
    }

    public Booking getBookingById(String bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found with ID: " + bookingId));
    }

    public List<Booking> getUserBookings(String userId) {
        return bookingRepository.findByUserId(userId);
    }

    public List<Booking> getDressBookings(String dressId) {
        return bookingRepository.findByDressId(dressId);
    }

    public Booking updateBookingStatus(String bookingId, String status) {
        Booking booking = getBookingById(bookingId);
        booking.setStatus(status);
        booking.setUpdatedAt(System.currentTimeMillis());
        return bookingRepository.save(booking);
    }

    public Booking updatePaymentStatus(String bookingId, String paymentStatus, String paymentId) {
        Booking booking = getBookingById(bookingId);
        booking.setPaymentStatus(paymentStatus);
        booking.setPaymentId(paymentId);
        
        if ("COMPLETED".equals(paymentStatus)) {
            booking.setStatus("CONFIRMED");
        }
        
        booking.setUpdatedAt(System.currentTimeMillis());
        return bookingRepository.save(booking);
    }

    public Booking cancelBooking(String bookingId) {
        Booking booking = getBookingById(bookingId);
        
        // Can only cancel if not yet shipped
        if ("SHIPPED".equals(booking.getStatus()) || "ACTIVE".equals(booking.getStatus()) || "RETURNED".equals(booking.getStatus())) {
            throw new RuntimeException("Cannot cancel booking in " + booking.getStatus() + " status");
        }
        
        booking.setStatus("CANCELLED");
        booking.setUpdatedAt(System.currentTimeMillis());
        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsByStatus(String status) {
        return bookingRepository.findByStatus(status);
    }

    public boolean isDateRangeAvailable(String dressId, Long startDate, Long endDate) {
        List<Booking> bookings = bookingRepository.findByDressId(dressId);
        
        return bookings.stream().noneMatch(booking -> 
            // Check if there's any overlap with active/confirmed bookings
            (booking.getStatus().equals("CONFIRMED") || booking.getStatus().equals("ACTIVE") || booking.getStatus().equals("SHIPPED")) &&
            !(endDate <= booking.getStartDate() || startDate >= booking.getEndDate())
        );
    }
}

