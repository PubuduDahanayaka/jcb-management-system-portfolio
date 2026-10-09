package com.example.demo.invoice_management.controller;

import com.example.demo.invoice_management.dto.CreateInvoiceDTO;
import com.example.demo.invoice_management.dto.InvoiceResponseDTO;
import com.example.demo.invoice_management.dto.UpdateInvoiceDTO;
import com.example.demo.invoice_management.entity.InvoiceType;
import com.example.demo.invoice_management.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@CrossOrigin(origins = "*")
public class InvoiceController {
    
    @Autowired
    private InvoiceService invoiceService;
    
    @PostMapping
    public ResponseEntity<?> createInvoice(@Valid @RequestBody CreateInvoiceDTO createInvoiceDTO) {
        try {
            InvoiceResponseDTO invoice = invoiceService.createInvoice(createInvoiceDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(invoice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/{invoiceId}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long invoiceId) {
        try {
            InvoiceResponseDTO invoice = invoiceService.getInvoiceById(invoiceId);
            return ResponseEntity.ok(invoice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/number/{invoiceNumber}")
    public ResponseEntity<?> getInvoiceByNumber(@PathVariable String invoiceNumber) {
        try {
            InvoiceResponseDTO invoice = invoiceService.getInvoiceByNumber(invoiceNumber);
            return ResponseEntity.ok(invoice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping
    public ResponseEntity<?> getAllInvoices() {
        try {
            List<InvoiceResponseDTO> invoices = invoiceService.getAllInvoices();
            return ResponseEntity.ok(invoices);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/type/{invoiceType}")
    public ResponseEntity<?> getInvoicesByType(@PathVariable InvoiceType invoiceType) {
        try {
            List<InvoiceResponseDTO> invoices = invoiceService.getInvoicesByType(invoiceType);
            return ResponseEntity.ok(invoices);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/date-range")
    public ResponseEntity<?> getInvoicesByDateRange(@RequestParam LocalDate startDate, 
                                                     @RequestParam LocalDate endDate) {
        try {
            List<InvoiceResponseDTO> invoices = invoiceService.getInvoicesByDateRange(startDate, endDate);
            return ResponseEntity.ok(invoices);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/paid")
    public ResponseEntity<?> getPaidInvoices() {
        try {
            List<InvoiceResponseDTO> invoices = invoiceService.getPaidInvoices();
            return ResponseEntity.ok(invoices);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/unpaid")
    public ResponseEntity<?> getUnpaidInvoices() {
        try {
            List<InvoiceResponseDTO> invoices = invoiceService.getUnpaidInvoices();
            return ResponseEntity.ok(invoices);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/overdue")
    public ResponseEntity<?> getOverdueInvoices() {
        try {
            List<InvoiceResponseDTO> invoices = invoiceService.getOverdueInvoices();
            return ResponseEntity.ok(invoices);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PutMapping("/{invoiceId}")
    public ResponseEntity<?> updateInvoice(@PathVariable Long invoiceId, 
                                           @Valid @RequestBody UpdateInvoiceDTO updateInvoiceDTO) {
        try {
            InvoiceResponseDTO invoice = invoiceService.updateInvoice(invoiceId, updateInvoiceDTO);
            return ResponseEntity.ok(invoice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @DeleteMapping("/{invoiceId}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long invoiceId) {
        try {
            invoiceService.deleteInvoice(invoiceId);
            return ResponseEntity.ok("Invoice deleted successfully");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @PostMapping("/{invoiceId}/mark-paid")
    public ResponseEntity<?> markAsPaid(@PathVariable Long invoiceId, 
                                        @RequestParam String paymentMethod) {
        try {
            InvoiceResponseDTO invoice = invoiceService.markAsPaid(invoiceId, paymentMethod);
            return ResponseEntity.ok(invoice);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/statistics/total-paid")
    public ResponseEntity<?> getTotalPaidAmount() {
        try {
            BigDecimal totalPaid = invoiceService.getTotalPaidAmount();
            return ResponseEntity.ok(totalPaid);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/statistics/total-unpaid")
    public ResponseEntity<?> getTotalUnpaidAmount() {
        try {
            BigDecimal totalUnpaid = invoiceService.getTotalUnpaidAmount();
            return ResponseEntity.ok(totalUnpaid);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
    
    @GetMapping("/statistics/average-amount")
    public ResponseEntity<?> getAverageInvoiceAmount() {
        try {
            BigDecimal averageAmount = invoiceService.getAverageInvoiceAmount();
            return ResponseEntity.ok(averageAmount);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal server error: " + e.getMessage());
        }
    }
}
