package com.example.demo.booking_management.pattern.decorator;

import com.example.demo.booking_management.pattern.factory.BookingOperation;

public class LoggingBookingOperationDecorator extends BookingOperationDecorator {
    
    public LoggingBookingOperationDecorator(BookingOperation bookingOperation) {
        super(bookingOperation);
    }
    
    @Override
    public String execute(String operation) {
        String result = super.execute(operation);
        System.out.println("Booking Logging: " + getStatus() + " executed operation: " + operation);
        return result + " [LOGGED]";
    }
}
