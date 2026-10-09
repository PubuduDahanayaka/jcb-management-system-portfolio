package com.example.demo.booking_management.pattern.decorator;

import com.example.demo.booking_management.pattern.factory.BookingOperation;

public abstract class BookingOperationDecorator implements BookingOperation {
    
    protected BookingOperation bookingOperation;
    
    public BookingOperationDecorator(BookingOperation bookingOperation) {
        this.bookingOperation = bookingOperation;
    }
    
    @Override
    public String execute(String operation) {
        return bookingOperation.execute(operation);
    }
    
    @Override
    public com.example.demo.booking_management.entity.BookingRequestStatus getStatus() {
        return bookingOperation.getStatus();
    }
}
