package com.example.demo.invoice_management.dto;

import com.example.demo.invoice_management.entity.InvoiceType;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public class UpdateInvoiceDTO {
    
    @Size(min = 5, max = 20, message = "Invoice number must be between 5 and 20 characters")
    private String invoiceNumber;
    
    private LocalDate invoiceDate;
    
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    private BigDecimal amount;
    
    private InvoiceType invoiceType;
    
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    private String title;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    private String description;
    
    private Boolean isPaid;
    
    private LocalDate paymentDate;
    
    private String paymentMethod;
    
    private LocalDate dueDate;
    
    @DecimalMin(value = "0.00", message = "Tax amount must be non-negative")
    private BigDecimal taxAmount;
    
    @DecimalMin(value = "0.00", message = "Discount amount must be non-negative")
    private BigDecimal discountAmount;
    
    private BigDecimal totalAmount;
    
    private Boolean isActive;
    
    public UpdateInvoiceDTO() {}
    
    public String getInvoiceNumber() {
        return invoiceNumber;
    }
    
    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }
    
    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }
    
    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }
    
    public BigDecimal getAmount() {
        return amount;
    }
    
    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }
    
    public InvoiceType getInvoiceType() {
        return invoiceType;
    }
    
    public void setInvoiceType(InvoiceType invoiceType) {
        this.invoiceType = invoiceType;
    }
    
    public String getTitle() {
        return title;
    }
    
    public void setTitle(String title) {
        this.title = title;
    }
    
    public String getDescription() {
        return description;
    }
    
    public void setDescription(String description) {
        this.description = description;
    }
    
    public Boolean getIsPaid() {
        return isPaid;
    }
    
    public void setIsPaid(Boolean isPaid) {
        this.isPaid = isPaid;
    }
    
    public LocalDate getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public BigDecimal getTaxAmount() {
        return taxAmount;
    }
    
    public void setTaxAmount(BigDecimal taxAmount) {
        this.taxAmount = taxAmount;
    }
    
    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }
    
    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
    
    public BigDecimal getTotalAmount() {
        return totalAmount;
    }
    
    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }
}
