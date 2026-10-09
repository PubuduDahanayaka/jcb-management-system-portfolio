package com.example.demo.booking_management.pattern.factory;

import com.example.demo.booking_management.entity.BookingRequestStatus;

public class PendingBookingOperation implements BookingOperation {
    
    @Override
    public String execute(String operation) {
        return "Pending booking: " + operation + " - awaiting approval";
    }
    
    @Override
    public BookingRequestStatus getStatus() {
        return BookingRequestStatus.PENDING;
    }
}
