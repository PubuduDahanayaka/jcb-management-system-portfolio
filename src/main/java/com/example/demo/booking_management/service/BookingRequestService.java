package com.example.demo.booking_management.service;

import com.example.demo.booking_management.dto.BookingRequestDetailedDTO;
import com.example.demo.booking_management.dto.BookingRequestResponseDTO;
import com.example.demo.booking_management.dto.CreateBookingRequestDTO;
import com.example.demo.booking_management.dto.UpdateBookingRequestDTO;
import com.example.demo.booking_management.entity.BookingRequest;
import com.example.demo.booking_management.entity.BookingRequestStatus;
import com.example.demo.booking_management.pattern.decorator.LoggingBookingOperationDecorator;
import com.example.demo.booking_management.pattern.decorator.NotificationBookingOperationDecorator;
import com.example.demo.booking_management.pattern.decorator.ValidationBookingOperationDecorator;
import com.example.demo.booking_management.pattern.factory.BookingOperation;
import com.example.demo.booking_management.pattern.factory.BookingOperationFactory;
import com.example.demo.booking_management.repository.BookingRequestRepository;
import com.example.demo.machine_management.entity.Machine;
import com.example.demo.machine_management.repository.MachineRepository;
import com.example.demo.user_management.entity.User;
import com.example.demo.user_management.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingRequestService {
    
    @Autowired
    private BookingRequestRepository bookingRequestRepository;
    
    @Autowired
    private MachineRepository machineRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private BookingOperationFactory bookingOperationFactory;
    
    public BookingRequestResponseDTO createBookingRequest(CreateBookingRequestDTO createBookingRequestDTO) {
        try {
            Optional<Machine> machine = machineRepository.findByMachineIdAndIsActiveTrue(createBookingRequestDTO.getMachineId());
            if (machine.isEmpty()) {
                throw new RuntimeException("Machine not found with ID: " + createBookingRequestDTO.getMachineId());
            }
            
            Optional<User> user = userRepository.findByUserIdAndIsActiveTrue(createBookingRequestDTO.getUserId());
            if (user.isEmpty()) {
                throw new RuntimeException("User not found with ID: " + createBookingRequestDTO.getUserId());
            }
            
            List<BookingRequest> conflictingBookings = bookingRequestRepository.findConflictingBookings(
                    createBookingRequestDTO.getMachineId(), createBookingRequestDTO.getRequestedDate());
            if (!conflictingBookings.isEmpty()) {
                throw new RuntimeException("Machine is already booked for the requested date");
            }
            
            BookingRequest bookingRequest = new BookingRequest();
            bookingRequest.setMachine(machine.get());
            bookingRequest.setUser(user.get());
            bookingRequest.setRequestedDate(createBookingRequestDTO.getRequestedDate());
            bookingRequest.setAdditionalMessage(createBookingRequestDTO.getAdditionalMessage());
            
            BookingRequest savedBookingRequest = bookingRequestRepository.save(bookingRequest);
            
            BookingOperation operation = bookingOperationFactory.createOperation(savedBookingRequest.getStatus());
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("CREATE_BOOKING_REQUEST");
            
            return convertToResponseDTO(savedBookingRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create booking request: " + e.getMessage(), e);
        }
    }
    
    public BookingRequestResponseDTO getBookingRequestById(Long bookingId) {
        try {
            Optional<BookingRequest> bookingRequest = bookingRequestRepository.findByBookingIdAndIsActiveTrue(bookingId);
            if (bookingRequest.isEmpty()) {
                throw new RuntimeException("Booking request not found with ID: " + bookingId);
            }
            
            BookingOperation operation = bookingOperationFactory.createOperation(bookingRequest.get().getStatus());
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("GET_BOOKING_REQUEST");
            
            return convertToResponseDTO(bookingRequest.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get booking request: " + e.getMessage(), e);
        }
    }
    
    public BookingRequestDetailedDTO getBookingRequestDetailedById(Long bookingId) {
        try {
            Optional<BookingRequest> bookingRequest = bookingRequestRepository.findByBookingIdAndIsActiveTrue(bookingId);
            if (bookingRequest.isEmpty()) {
                throw new RuntimeException("Booking request not found with ID: " + bookingId);
            }
            
            BookingOperation operation = bookingOperationFactory.createOperation(bookingRequest.get().getStatus());
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("GET_BOOKING_REQUEST_DETAILED");
            
            return convertToDetailedDTO(bookingRequest.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get detailed booking request: " + e.getMessage(), e);
        }
    }
    
    public List<BookingRequestDetailedDTO> getAllBookingRequests() {
        try {
            List<BookingRequest> bookingRequests = bookingRequestRepository.findByIsActiveTrue();
            
            BookingOperation operation = bookingOperationFactory.createOperation(BookingRequestStatus.PENDING);
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("GET_ALL_BOOKING_REQUESTS");
            
            return bookingRequests.stream()
                    .map(this::convertToDetailedDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all booking requests: " + e.getMessage(), e);
        }
    }
    
    public List<BookingRequestResponseDTO> getBookingRequestsByUser(String userId) {
        try {
            List<BookingRequest> bookingRequests = bookingRequestRepository.findByUserIdAndIsActiveTrue(userId);
            
            BookingOperation operation = bookingOperationFactory.createOperation(BookingRequestStatus.PENDING);
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("GET_BOOKING_REQUESTS_BY_USER");
            
            return bookingRequests.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get booking requests by user: " + e.getMessage(), e);
        }
    }
    
    public List<BookingRequestResponseDTO> getBookingRequestsByMachine(String machineId) {
        try {
            List<BookingRequest> bookingRequests = bookingRequestRepository.findByMachineIdAndIsActiveTrue(machineId);
            
            BookingOperation operation = bookingOperationFactory.createOperation(BookingRequestStatus.PENDING);
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("GET_BOOKING_REQUESTS_BY_MACHINE");
            
            return bookingRequests.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get booking requests by machine: " + e.getMessage(), e);
        }
    }
    
    public List<BookingRequestResponseDTO> getBookingRequestsByStatus(BookingRequestStatus status) {
        try {
            List<BookingRequest> bookingRequests = bookingRequestRepository.findByStatusAndIsActiveTrue(status);
            
            BookingOperation operation = bookingOperationFactory.createOperation(status);
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("GET_BOOKING_REQUESTS_BY_STATUS");
            
            return bookingRequests.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get booking requests by status: " + e.getMessage(), e);
        }
    }
    
    public BookingRequestResponseDTO updateBookingRequest(Long bookingId, UpdateBookingRequestDTO updateBookingRequestDTO) {
        try {
            Optional<BookingRequest> bookingRequestOptional = bookingRequestRepository.findByBookingIdAndIsActiveTrue(bookingId);
            if (bookingRequestOptional.isEmpty()) {
                throw new RuntimeException("Booking request not found with ID: " + bookingId);
            }
            
            BookingRequest bookingRequest = bookingRequestOptional.get();
            
            if (updateBookingRequestDTO.getRequestedDate() != null) {
                bookingRequest.setRequestedDate(updateBookingRequestDTO.getRequestedDate());
            }
            if (updateBookingRequestDTO.getStatus() != null) {
                bookingRequest.setStatus(updateBookingRequestDTO.getStatus());
                if (updateBookingRequestDTO.getStatus() == BookingRequestStatus.APPROVED) {
                    bookingRequest.setApprovedBy(updateBookingRequestDTO.getApprovedBy());
                    bookingRequest.setApprovedAt(LocalDateTime.now());
                }
            }
            if (updateBookingRequestDTO.getAdditionalMessage() != null) {
                bookingRequest.setAdditionalMessage(updateBookingRequestDTO.getAdditionalMessage());
            }
            if (updateBookingRequestDTO.getRejectionReason() != null) {
                bookingRequest.setRejectionReason(updateBookingRequestDTO.getRejectionReason());
            }
            if (updateBookingRequestDTO.getIsActive() != null) {
                bookingRequest.setIsActive(updateBookingRequestDTO.getIsActive());
            }
            
            BookingRequest updatedBookingRequest = bookingRequestRepository.save(bookingRequest);
            
            BookingOperation operation = bookingOperationFactory.createOperation(updatedBookingRequest.getStatus());
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("UPDATE_BOOKING_REQUEST");
            
            return convertToResponseDTO(updatedBookingRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update booking request: " + e.getMessage(), e);
        }
    }
    
    public void deleteBookingRequest(Long bookingId) {
        try {
            Optional<BookingRequest> bookingRequestOptional = bookingRequestRepository.findByBookingIdAndIsActiveTrue(bookingId);
            if (bookingRequestOptional.isEmpty()) {
                throw new RuntimeException("Booking request not found with ID: " + bookingId);
            }
            
            BookingRequest bookingRequest = bookingRequestOptional.get();
            bookingRequest.setIsActive(false);
            bookingRequestRepository.save(bookingRequest);
            
            BookingOperation operation = bookingOperationFactory.createOperation(bookingRequest.getStatus());
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("DELETE_BOOKING_REQUEST");
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete booking request: " + e.getMessage(), e);
        }
    }
    
    public BookingRequestResponseDTO approveBookingRequest(Long bookingId, String approvedBy) {
        try {
            Optional<BookingRequest> bookingRequestOptional = bookingRequestRepository.findByBookingIdAndIsActiveTrue(bookingId);
            if (bookingRequestOptional.isEmpty()) {
                throw new RuntimeException("Booking request not found with ID: " + bookingId);
            }
            
            BookingRequest bookingRequest = bookingRequestOptional.get();
            bookingRequest.setStatus(BookingRequestStatus.APPROVED);
            bookingRequest.setApprovedBy(approvedBy);
            bookingRequest.setApprovedAt(LocalDateTime.now());
            
            BookingRequest updatedBookingRequest = bookingRequestRepository.save(bookingRequest);
            
            BookingOperation operation = bookingOperationFactory.createOperation(updatedBookingRequest.getStatus());
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("APPROVE_BOOKING_REQUEST");
            
            return convertToResponseDTO(updatedBookingRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to approve booking request: " + e.getMessage(), e);
        }
    }
    
    public BookingRequestResponseDTO rejectBookingRequest(Long bookingId, String rejectionReason) {
        try {
            Optional<BookingRequest> bookingRequestOptional = bookingRequestRepository.findByBookingIdAndIsActiveTrue(bookingId);
            if (bookingRequestOptional.isEmpty()) {
                throw new RuntimeException("Booking request not found with ID: " + bookingId);
            }
            
            BookingRequest bookingRequest = bookingRequestOptional.get();
            bookingRequest.setStatus(BookingRequestStatus.REJECTED);
            bookingRequest.setRejectionReason(rejectionReason);
            
            BookingRequest updatedBookingRequest = bookingRequestRepository.save(bookingRequest);
            
            BookingOperation operation = bookingOperationFactory.createOperation(updatedBookingRequest.getStatus());
            operation = new ValidationBookingOperationDecorator(operation);
            operation = new NotificationBookingOperationDecorator(operation);
            operation = new LoggingBookingOperationDecorator(operation);
            operation.execute("REJECT_BOOKING_REQUEST");
            
            return convertToResponseDTO(updatedBookingRequest);
        } catch (Exception e) {
            throw new RuntimeException("Failed to reject booking request: " + e.getMessage(), e);
        }
    }
    
    private BookingRequestResponseDTO convertToResponseDTO(BookingRequest bookingRequest) {
        return new BookingRequestResponseDTO(
                bookingRequest.getBookingId(),
                bookingRequest.getMachine().getMachineId(),
                bookingRequest.getUser().getUserId(),
                bookingRequest.getRequestedDate(),
                bookingRequest.getStatus(),
                bookingRequest.getAdditionalMessage(),
                bookingRequest.getCreatedAt(),
                bookingRequest.getUpdatedAt(),
                bookingRequest.getIsActive(),
                bookingRequest.getApprovedBy(),
                bookingRequest.getApprovedAt(),
                bookingRequest.getRejectionReason()
        );
    }
    
    private BookingRequestDetailedDTO convertToDetailedDTO(BookingRequest bookingRequest) {
        BookingRequestDetailedDTO detailedDTO = new BookingRequestDetailedDTO();
        detailedDTO.setBookingId(bookingRequest.getBookingId());
        detailedDTO.setRequestedDate(bookingRequest.getRequestedDate());
        detailedDTO.setStatus(bookingRequest.getStatus());
        detailedDTO.setAdditionalMessage(bookingRequest.getAdditionalMessage());
        detailedDTO.setCreatedAt(bookingRequest.getCreatedAt());
        detailedDTO.setUpdatedAt(bookingRequest.getUpdatedAt());
        detailedDTO.setIsActive(bookingRequest.getIsActive());
        detailedDTO.setApprovedBy(bookingRequest.getApprovedBy());
        detailedDTO.setApprovedAt(bookingRequest.getApprovedAt());
        detailedDTO.setRejectionReason(bookingRequest.getRejectionReason());
        
        Machine machine = bookingRequest.getMachine();
        BookingRequestDetailedDTO.MachineInfo machineInfo = new BookingRequestDetailedDTO.MachineInfo(
                machine.getMachineId(),
                machine.getMachineType(),
                machine.getFuelType(),
                machine.getCurrentFuelLevel(),
                machine.getMaxFuelCapacity(),
                machine.getOperatingHours(),
                machine.getIsActive()
        );
        detailedDTO.setMachineInfo(machineInfo);
        
        User user = bookingRequest.getUser();
        BookingRequestDetailedDTO.UserInfo userInfo = new BookingRequestDetailedDTO.UserInfo(
                user.getUserId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail(),
                user.getContactNumber(),
                user.getUserType(),
                user.getIsActive()
        );
        detailedDTO.setUserInfo(userInfo);
        
        return detailedDTO;
    }
}
