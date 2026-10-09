package com.example.demo.booking_management.pattern.factory;

import com.example.demo.booking_management.entity.BookingRequestStatus;

public class CancelledBookingOperation implements BookingOperation {
    
    @Override
    public String execute(String operation) {
        return "Cancelled booking: " + operation + " - request cancelled";
    }
    
    @Override
    public BookingRequestStatus getStatus() {
        return BookingRequestStatus.CANCELLED;
    }
}
