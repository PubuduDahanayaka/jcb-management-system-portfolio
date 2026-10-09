package com.example.demo.booking_management.pattern.decorator;

import com.example.demo.booking_management.pattern.factory.BookingOperation;

public class ValidationBookingOperationDecorator extends BookingOperationDecorator {
    
    public ValidationBookingOperationDecorator(BookingOperation bookingOperation) {
        super(bookingOperation);
    }
    
    @Override
    public String execute(String operation) {
        if (operation == null || operation.trim().isEmpty()) {
            throw new IllegalArgumentException("Booking operation cannot be null or empty");
        }
        String result = super.execute(operation);
        return result + " [VALIDATED]";
    }
}
