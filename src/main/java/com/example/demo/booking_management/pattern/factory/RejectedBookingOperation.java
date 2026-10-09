package com.example.demo.booking_management.pattern.factory;

import com.example.demo.booking_management.entity.BookingRequestStatus;

public class RejectedBookingOperation implements BookingOperation {
    
    @Override
    public String execute(String operation) {
        return "Rejected booking: " + operation + " - request denied";
    }
    
    @Override
    public BookingRequestStatus getStatus() {
        return BookingRequestStatus.REJECTED;
    }
}
