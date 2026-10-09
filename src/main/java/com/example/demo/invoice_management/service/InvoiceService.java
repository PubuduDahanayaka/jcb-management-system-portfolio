package com.example.demo.invoice_management.service;

import com.example.demo.invoice_management.dto.CreateInvoiceDTO;
import com.example.demo.invoice_management.dto.InvoiceResponseDTO;
import com.example.demo.invoice_management.dto.UpdateInvoiceDTO;
import com.example.demo.invoice_management.entity.Invoice;
import com.example.demo.invoice_management.entity.InvoiceType;
import com.example.demo.invoice_management.pattern.factory.InvoiceOperation;
import com.example.demo.invoice_management.pattern.factory.InvoiceOperationFactory;
import com.example.demo.invoice_management.pattern.strategy.InvoiceProcessingContext;
import com.example.demo.invoice_management.repository.InvoiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class InvoiceService {
    
    @Autowired
    private InvoiceRepository invoiceRepository;
    
    @Autowired
    private InvoiceOperationFactory invoiceOperationFactory;
    
    @Autowired
    private InvoiceProcessingContext invoiceProcessingContext;
    
    public InvoiceResponseDTO createInvoice(CreateInvoiceDTO createInvoiceDTO) {
        try {
            if (invoiceRepository.existsByInvoiceNumber(createInvoiceDTO.getInvoiceNumber())) {
                throw new RuntimeException("Invoice number already exists");
            }
            
            Invoice invoice = new Invoice();
            invoice.setInvoiceNumber(createInvoiceDTO.getInvoiceNumber());
            invoice.setInvoiceDate(createInvoiceDTO.getInvoiceDate());
            invoice.setAmount(createInvoiceDTO.getAmount());
            invoice.setInvoiceType(createInvoiceDTO.getInvoiceType());
            invoice.setTitle(createInvoiceDTO.getTitle());
            invoice.setDescription(createInvoiceDTO.getDescription());
            invoice.setDueDate(createInvoiceDTO.getDueDate());
            invoice.setTaxAmount(createInvoiceDTO.getTaxAmount());
            invoice.setDiscountAmount(createInvoiceDTO.getDiscountAmount());
            
            BigDecimal totalAmount = calculateTotalAmount(createInvoiceDTO.getAmount(), 
                    createInvoiceDTO.getTaxAmount(), createInvoiceDTO.getDiscountAmount());
            invoice.setTotalAmount(totalAmount);
            
            Invoice savedInvoice = invoiceRepository.save(invoice);
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(savedInvoice.getInvoiceType());
            operation.execute("CREATE_INVOICE");
            
            String processingResult = invoiceProcessingContext.processInvoice(savedInvoice);
            System.out.println("Invoice Processing: " + processingResult);
            
            return convertToResponseDTO(savedInvoice);
        } catch (Exception e) {
            throw new RuntimeException("Failed to create invoice: " + e.getMessage(), e);
        }
    }
    
    public InvoiceResponseDTO getInvoiceById(Long invoiceId) {
        try {
            Optional<Invoice> invoice = invoiceRepository.findById(invoiceId);
            if (invoice.isEmpty() || !invoice.get().getIsActive()) {
                throw new RuntimeException("Invoice not found with ID: " + invoiceId);
            }
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(invoice.get().getInvoiceType());
            operation.execute("GET_INVOICE");
            
            String processingResult = invoiceProcessingContext.processInvoice(invoice.get());
            System.out.println("Invoice Processing: " + processingResult);
            
            return convertToResponseDTO(invoice.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get invoice: " + e.getMessage(), e);
        }
    }
    
    public InvoiceResponseDTO getInvoiceByNumber(String invoiceNumber) {
        try {
            Optional<Invoice> invoice = invoiceRepository.findByInvoiceNumberAndIsActiveTrue(invoiceNumber);
            if (invoice.isEmpty()) {
                throw new RuntimeException("Invoice not found with number: " + invoiceNumber);
            }
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(invoice.get().getInvoiceType());
            operation.execute("GET_INVOICE_BY_NUMBER");
            
            String processingResult = invoiceProcessingContext.processInvoice(invoice.get());
            System.out.println("Invoice Processing: " + processingResult);
            
            return convertToResponseDTO(invoice.get());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get invoice by number: " + e.getMessage(), e);
        }
    }
    
    public List<InvoiceResponseDTO> getAllInvoices() {
        try {
            List<Invoice> invoices = invoiceRepository.findByIsActiveTrue();
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(InvoiceType.SERVICE_INVOICE);
            operation.execute("GET_ALL_INVOICES");
            
            return invoices.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get all invoices: " + e.getMessage(), e);
        }
    }
    
    public List<InvoiceResponseDTO> getInvoicesByType(InvoiceType invoiceType) {
        try {
            List<Invoice> invoices = invoiceRepository.findByInvoiceTypeAndIsActiveTrue(invoiceType);
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(invoiceType);
            operation.execute("GET_INVOICES_BY_TYPE");
            
            return invoices.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get invoices by type: " + e.getMessage(), e);
        }
    }
    
    public List<InvoiceResponseDTO> getInvoicesByDateRange(LocalDate startDate, LocalDate endDate) {
        try {
            List<Invoice> invoices = invoiceRepository.findByInvoiceDateBetween(startDate, endDate);
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(InvoiceType.SERVICE_INVOICE);
            operation.execute("GET_INVOICES_BY_DATE_RANGE");
            
            return invoices.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get invoices by date range: " + e.getMessage(), e);
        }
    }
    
    public List<InvoiceResponseDTO> getPaidInvoices() {
        try {
            List<Invoice> invoices = invoiceRepository.findByIsPaidAndIsActiveTrue(true);
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(InvoiceType.SERVICE_INVOICE);
            operation.execute("GET_PAID_INVOICES");
            
            return invoices.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get paid invoices: " + e.getMessage(), e);
        }
    }
    
    public List<InvoiceResponseDTO> getUnpaidInvoices() {
        try {
            List<Invoice> invoices = invoiceRepository.findByIsPaidAndIsActiveTrue(false);
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(InvoiceType.SERVICE_INVOICE);
            operation.execute("GET_UNPAID_INVOICES");
            
            return invoices.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get unpaid invoices: " + e.getMessage(), e);
        }
    }
    
    public List<InvoiceResponseDTO> getOverdueInvoices() {
        try {
            List<Invoice> invoices = invoiceRepository.findOverdueInvoices(LocalDate.now());
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(InvoiceType.PENALTY_FEE);
            operation.execute("GET_OVERDUE_INVOICES");
            
            return invoices.stream()
                    .map(this::convertToResponseDTO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Failed to get overdue invoices: " + e.getMessage(), e);
        }
    }
    
    public InvoiceResponseDTO updateInvoice(Long invoiceId, UpdateInvoiceDTO updateInvoiceDTO) {
        try {
            Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
            if (invoiceOptional.isEmpty() || !invoiceOptional.get().getIsActive()) {
                throw new RuntimeException("Invoice not found with ID: " + invoiceId);
            }
            
            Invoice invoice = invoiceOptional.get();
            
            if (updateInvoiceDTO.getInvoiceNumber() != null && !updateInvoiceDTO.getInvoiceNumber().equals(invoice.getInvoiceNumber())) {
                if (invoiceRepository.existsByInvoiceNumber(updateInvoiceDTO.getInvoiceNumber())) {
                    throw new RuntimeException("Invoice number already exists");
                }
                invoice.setInvoiceNumber(updateInvoiceDTO.getInvoiceNumber());
            }
            if (updateInvoiceDTO.getInvoiceDate() != null) {
                invoice.setInvoiceDate(updateInvoiceDTO.getInvoiceDate());
            }
            if (updateInvoiceDTO.getAmount() != null) {
                invoice.setAmount(updateInvoiceDTO.getAmount());
            }
            if (updateInvoiceDTO.getInvoiceType() != null) {
                invoice.setInvoiceType(updateInvoiceDTO.getInvoiceType());
            }
            if (updateInvoiceDTO.getTitle() != null) {
                invoice.setTitle(updateInvoiceDTO.getTitle());
            }
            if (updateInvoiceDTO.getDescription() != null) {
                invoice.setDescription(updateInvoiceDTO.getDescription());
            }
            if (updateInvoiceDTO.getIsPaid() != null) {
                invoice.setIsPaid(updateInvoiceDTO.getIsPaid());
                if (updateInvoiceDTO.getIsPaid()) {
                    invoice.setPaymentDate(LocalDate.now());
                }
            }
            if (updateInvoiceDTO.getPaymentMethod() != null) {
                invoice.setPaymentMethod(updateInvoiceDTO.getPaymentMethod());
            }
            if (updateInvoiceDTO.getDueDate() != null) {
                invoice.setDueDate(updateInvoiceDTO.getDueDate());
            }
            if (updateInvoiceDTO.getTaxAmount() != null) {
                invoice.setTaxAmount(updateInvoiceDTO.getTaxAmount());
            }
            if (updateInvoiceDTO.getDiscountAmount() != null) {
                invoice.setDiscountAmount(updateInvoiceDTO.getDiscountAmount());
            }
            if (updateInvoiceDTO.getTotalAmount() != null) {
                invoice.setTotalAmount(updateInvoiceDTO.getTotalAmount());
            }
            if (updateInvoiceDTO.getIsActive() != null) {
                invoice.setIsActive(updateInvoiceDTO.getIsActive());
            }
            
            Invoice updatedInvoice = invoiceRepository.save(invoice);
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(updatedInvoice.getInvoiceType());
            operation.execute("UPDATE_INVOICE");
            
            String processingResult = invoiceProcessingContext.processInvoice(updatedInvoice);
            System.out.println("Invoice Processing: " + processingResult);
            
            return convertToResponseDTO(updatedInvoice);
        } catch (Exception e) {
            throw new RuntimeException("Failed to update invoice: " + e.getMessage(), e);
        }
    }
    
    public void deleteInvoice(Long invoiceId) {
        try {
            Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
            if (invoiceOptional.isEmpty() || !invoiceOptional.get().getIsActive()) {
                throw new RuntimeException("Invoice not found with ID: " + invoiceId);
            }
            
            Invoice invoice = invoiceOptional.get();
            invoice.setIsActive(false);
            invoiceRepository.save(invoice);
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(invoice.getInvoiceType());
            operation.execute("DELETE_INVOICE");
        } catch (Exception e) {
            throw new RuntimeException("Failed to delete invoice: " + e.getMessage(), e);
        }
    }
    
    public InvoiceResponseDTO markAsPaid(Long invoiceId, String paymentMethod) {
        try {
            Optional<Invoice> invoiceOptional = invoiceRepository.findById(invoiceId);
            if (invoiceOptional.isEmpty() || !invoiceOptional.get().getIsActive()) {
                throw new RuntimeException("Invoice not found with ID: " + invoiceId);
            }
            
            Invoice invoice = invoiceOptional.get();
            invoice.setIsPaid(true);
            invoice.setPaymentDate(LocalDate.now());
            invoice.setPaymentMethod(paymentMethod);
            
            Invoice updatedInvoice = invoiceRepository.save(invoice);
            
            InvoiceOperation operation = invoiceOperationFactory.createOperation(updatedInvoice.getInvoiceType());
            operation.execute("MARK_AS_PAID");
            
            String processingResult = invoiceProcessingContext.processInvoice(updatedInvoice);
            System.out.println("Invoice Processing: " + processingResult);
            
            return convertToResponseDTO(updatedInvoice);
        } catch (Exception e) {
            throw new RuntimeException("Failed to mark invoice as paid: " + e.getMessage(), e);
        }
    }
    
    public BigDecimal getTotalPaidAmount() {
        try {
            BigDecimal totalPaid = invoiceRepository.getTotalPaidAmount();
            return totalPaid != null ? totalPaid : BigDecimal.ZERO;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get total paid amount: " + e.getMessage(), e);
        }
    }
    
    public BigDecimal getTotalUnpaidAmount() {
        try {
            BigDecimal totalUnpaid = invoiceRepository.getTotalUnpaidAmount();
            return totalUnpaid != null ? totalUnpaid : BigDecimal.ZERO;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get total unpaid amount: " + e.getMessage(), e);
        }
    }
    
    public BigDecimal getAverageInvoiceAmount() {
        try {
            BigDecimal averageAmount = invoiceRepository.getAverageInvoiceAmount();
            return averageAmount != null ? averageAmount : BigDecimal.ZERO;
        } catch (Exception e) {
            throw new RuntimeException("Failed to get average invoice amount: " + e.getMessage(), e);
        }
    }
    
    private BigDecimal calculateTotalAmount(BigDecimal amount, BigDecimal taxAmount, BigDecimal discountAmount) {
        BigDecimal total = amount;
        if (taxAmount != null) {
            total = total.add(taxAmount);
        }
        if (discountAmount != null) {
            total = total.subtract(discountAmount);
        }
        return total.max(BigDecimal.ZERO);
    }
    
    private InvoiceResponseDTO convertToResponseDTO(Invoice invoice) {
        return new InvoiceResponseDTO(
                invoice.getInvoiceId(),
                invoice.getInvoiceNumber(),
                invoice.getInvoiceDate(),
                invoice.getAmount(),
                invoice.getInvoiceType(),
                invoice.getTitle(),
                invoice.getDescription(),
                invoice.getCreatedAt(),
                invoice.getUpdatedAt(),
                invoice.getIsActive(),
                invoice.getIsPaid(),
                invoice.getPaymentDate(),
                invoice.getPaymentMethod(),
                invoice.getDueDate(),
                invoice.getTaxAmount(),
                invoice.getDiscountAmount(),
                invoice.getTotalAmount()
        );
    }
}
