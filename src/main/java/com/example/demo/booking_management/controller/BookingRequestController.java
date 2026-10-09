package com.example.demo.booking_management.controller;

import com.example.demo.booking_management.dto.BookingRequestDetailedDTO;
import com.example.demo.booking_management.dto.BookingRequestResponseDTO;
import com.example.demo.booking_management.dto.CreateBookingRequestDTO;
import com.example.demo.booking_management.dto.UpdateBookingRequestDTO;
import com.example.demo.booking_management.entity.BookingRequestStatus;
import com.example.demo.booking_management.service.BookingRequestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/booking-requests")
@CrossOrigin(origins = "*")
public class BookingRequestController {
    
    @Autowired
    private BookingRequestService bookingRequestService;
    
    @PostMapping
    public ResponseEntity<?> createBookingRequest(@Valid @RequestBody CreateBookingRequestDTO createBookingRequestDTO) {
        try {
            BookingRequestResponseDTO bookingRequest = bookingRequestService.createBookingRequest(createBookingRequestDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(bookingRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{bookingId}")
    public ResponseEntity<?> getBookingRequestById(@PathVariable Long bookingId) {
        try {
            BookingRequestResponseDTO bookingRequest = bookingRequestService.getBookingRequestById(bookingId);
            return ResponseEntity.ok(bookingRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{bookingId}/detailed")
    public ResponseEntity<?> getBookingRequestDetailedById(@PathVariable Long bookingId) {
        try {
            BookingRequestDetailedDTO bookingRequest = bookingRequestService.getBookingRequestDetailedById(bookingId);
            return ResponseEntity.ok(bookingRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllBookingRequests() {
        try {
            List<BookingRequestDetailedDTO> bookingRequests = bookingRequestService.getAllBookingRequests();
            return ResponseEntity.ok(bookingRequests);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/user/{userId}")
    public ResponseEntity<?> getBookingRequestsByUser(@PathVariable String userId) {
        try {
            List<BookingRequestResponseDTO> bookingRequests = bookingRequestService.getBookingRequestsByUser(userId);
            return ResponseEntity.ok(bookingRequests);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/machine/{machineId}")
    public ResponseEntity<?> getBookingRequestsByMachine(@PathVariable String machineId) {
        try {
            List<BookingRequestResponseDTO> bookingRequests = bookingRequestService.getBookingRequestsByMachine(machineId);
            return ResponseEntity.ok(bookingRequests);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/status/{status}")
    public ResponseEntity<?> getBookingRequestsByStatus(@PathVariable BookingRequestStatus status) {
        try {
            List<BookingRequestResponseDTO> bookingRequests = bookingRequestService.getBookingRequestsByStatus(status);
            return ResponseEntity.ok(bookingRequests);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{bookingId}")
    public ResponseEntity<?> updateBookingRequest(@PathVariable Long bookingId, 
                                                  @Valid @RequestBody UpdateBookingRequestDTO updateBookingRequestDTO) {
        try {
            BookingRequestResponseDTO bookingRequest = bookingRequestService.updateBookingRequest(bookingId, updateBookingRequestDTO);
            return ResponseEntity.ok(bookingRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{bookingId}")
    public ResponseEntity<?> deleteBookingRequest(@PathVariable Long bookingId) {
        try {
            bookingRequestService.deleteBookingRequest(bookingId);
            return ResponseEntity.ok("Booking request deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PostMapping("/{bookingId}/approve")
    public ResponseEntity<?> approveBookingRequest(@PathVariable Long bookingId, 
                                                   @RequestParam String approvedBy) {
        try {
            BookingRequestResponseDTO bookingRequest = bookingRequestService.approveBookingRequest(bookingId, approvedBy);
            return ResponseEntity.ok(bookingRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PostMapping("/{bookingId}/reject")
    public ResponseEntity<?> rejectBookingRequest(@PathVariable Long bookingId, 
                                                  @RequestParam String rejectionReason) {
        try {
            BookingRequestResponseDTO bookingRequest = bookingRequestService.rejectBookingRequest(bookingId, rejectionReason);
            return ResponseEntity.ok(bookingRequest);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
}
