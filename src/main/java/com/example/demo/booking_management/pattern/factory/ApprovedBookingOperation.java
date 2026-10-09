package com.example.demo.booking_management.pattern.factory;

import com.example.demo.booking_management.entity.BookingRequestStatus;

public class ApprovedBookingOperation implements BookingOperation {
    
    @Override
    public String execute(String operation) {
        return "Approved booking: " + operation + " - ready for use";
    }
    
    @Override
    public BookingRequestStatus getStatus() {
        return BookingRequestStatus.APPROVED;
    }
}
