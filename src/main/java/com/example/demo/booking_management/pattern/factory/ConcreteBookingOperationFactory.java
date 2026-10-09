package com.example.demo.booking_management.pattern.factory;

import com.example.demo.booking_management.entity.BookingRequestStatus;
import org.springframework.stereotype.Component;

@Component
public class ConcreteBookingOperationFactory implements BookingOperationFactory {
    
    @Override
    public BookingOperation createOperation(BookingRequestStatus status) {
        return switch (status) {
            case PENDING -> new PendingBookingOperation();
            case APPROVED -> new ApprovedBookingOperation();
            case REJECTED -> new RejectedBookingOperation();
            case CANCELLED -> new CancelledBookingOperation();
            case COMPLETED -> new CompletedBookingOperation();
        };
    }
}
