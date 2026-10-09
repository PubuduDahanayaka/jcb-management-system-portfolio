package com.example.demo.booking_management.pattern.decorator;

import com.example.demo.booking_management.pattern.factory.BookingOperation;

public class NotificationBookingOperationDecorator extends BookingOperationDecorator {
    
    public NotificationBookingOperationDecorator(BookingOperation bookingOperation) {
        super(bookingOperation);
    }
    
    @Override
    public String execute(String operation) {
        String result = super.execute(operation);
        System.out.println("Notification: " + getStatus() + " booking status change notification sent");
        return result + " [NOTIFIED]";
    }
}
