package com.example.demo.booking_management;

import com.example.demo.booking_management.dto.CreateBookingRequestDTO;
import com.example.demo.booking_management.service.BookingRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class BookingManagementTestRunner implements CommandLineRunner {
    
    @Autowired
    private BookingRequestService bookingRequestService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Booking Management System Test ===");
        
        try {
            CreateBookingRequestDTO createBookingRequestDTO = new CreateBookingRequestDTO(
                    "EXC0001", "CUS0001", LocalDateTime.now().plusDays(1), "Need excavator for construction work"
            );
            
            var createdBookingRequest = bookingRequestService.createBookingRequest(createBookingRequestDTO);
            System.out.println("Created Booking Request: " + createdBookingRequest.getBookingId() + " - Status: " + createdBookingRequest.getStatus());
            
            var detailedBookingRequest = bookingRequestService.getBookingRequestDetailedById(createdBookingRequest.getBookingId());
            System.out.println("Detailed Booking Request: " + detailedBookingRequest.getBookingId() + 
                             " - Machine: " + detailedBookingRequest.getMachineInfo().getMachineType() +
                             " - User: " + detailedBookingRequest.getUserInfo().getFirstName() + " " + detailedBookingRequest.getUserInfo().getLastName());
            
            var approvedBookingRequest = bookingRequestService.approveBookingRequest(createdBookingRequest.getBookingId(), "ADM0001");
            System.out.println("Approved Booking Request: " + approvedBookingRequest.getBookingId() + " - Status: " + approvedBookingRequest.getStatus());
            
            System.out.println("=== Booking Management Test completed successfully ===");
        } catch (Exception e) {
            System.err.println("Booking Management Test failed: " + e.getMessage());
        }
    }
}
