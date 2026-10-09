package com.example.demo.invoice_management;

import com.example.demo.invoice_management.dto.CreateInvoiceDTO;
import com.example.demo.invoice_management.entity.InvoiceType;
import com.example.demo.invoice_management.service.InvoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class InvoiceManagementTestRunner implements CommandLineRunner {
    
    @Autowired
    private InvoiceService invoiceService;
    
    @Override
    public void run(String... args) throws Exception {
        System.out.println("=== Invoice Management System Test ===");
        
        try {
            CreateInvoiceDTO createInvoiceDTO = new CreateInvoiceDTO(
                    "INV-2024-001", LocalDate.now(), new BigDecimal("1500.00"), 
                    InvoiceType.SERVICE_INVOICE, "Machine Rental Service", 
                    "Monthly rental for excavator EXC0001"
            );
            
            var createdInvoice = invoiceService.createInvoice(createInvoiceDTO);
            System.out.println("Created Invoice: " + createdInvoice.getInvoiceNumber() + " - Amount: " + createdInvoice.getAmount());
            
            var retrievedInvoice = invoiceService.getInvoiceByNumber(createdInvoice.getInvoiceNumber());
            System.out.println("Retrieved Invoice: " + retrievedInvoice.getInvoiceNumber() + " - Type: " + retrievedInvoice.getInvoiceType());
            
            var paidInvoice = invoiceService.markAsPaid(createdInvoice.getInvoiceId(), "Credit Card");
            System.out.println("Marked as Paid: " + paidInvoice.getInvoiceNumber() + " - Payment Method: " + paidInvoice.getPaymentMethod());
            
            var totalPaid = invoiceService.getTotalPaidAmount();
            System.out.println("Total Paid Amount: " + totalPaid);
            
            System.out.println("=== Invoice Management Test completed successfully ===");
        } catch (Exception e) {
            System.err.println("Invoice Management Test failed: " + e.getMessage());
        }
    }
}
