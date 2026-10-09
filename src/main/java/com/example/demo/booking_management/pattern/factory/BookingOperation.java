package com.example.demo.booking_management.pattern.factory;

import com.example.demo.booking_management.entity.BookingRequestStatus;

public interface BookingOperation {
    String execute(String operation);
    BookingRequestStatus getStatus();
}
