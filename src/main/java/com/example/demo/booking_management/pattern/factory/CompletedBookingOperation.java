package com.example.demo.booking_management.pattern.factory;

import com.example.demo.booking_management.entity.BookingRequestStatus;

public class CompletedBookingOperation implements BookingOperation {
    
    @Override
    public String execute(String operation) {
        return "Completed booking: " + operation + " - successfully finished";
    }
    
    @Override
    public BookingRequestStatus getStatus() {
        return BookingRequestStatus.COMPLETED;
    }
}
