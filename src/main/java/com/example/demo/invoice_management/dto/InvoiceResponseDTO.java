package com.example.demo.invoice_management.dto;

import com.example.demo.invoice_management.entity.InvoiceType;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class InvoiceResponseDTO {
    
    private Long invoiceId;
    private String invoiceNumber;
    private LocalDate invoiceDate;
    private BigDecimal amount;
    private InvoiceType invoiceType;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private Boolean isActive;
    private Boolean isPaid;
    private LocalDate paymentDate;
    private String paymentMethod;
    private LocalDate dueDate;
    private BigDecimal taxAmount;
    private BigDecimal discountAmount;
    private BigDecimal totalAmount;
    
    public InvoiceResponseDTO() {}
    
    public InvoiceResponseDTO(Long invoiceId, String invoiceNumber, LocalDate invoiceDate, 
                             BigDecimal amount, InvoiceType invoiceType, String title, 
                             String description, LocalDateTime createdAt, LocalDateTime updatedAt,
                             Boolean isActive, Boolean isPaid, LocalDate paymentDate, 
                             String paymentMethod, LocalDate dueDate, BigDecimal taxAmount,
                             BigDecimal discountAmount, BigDecimal totalAmount) {
        this.invoiceId = invoiceId;
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.amount = amount;
        this.invoiceType = invoiceType;
        this.title = title;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isActive = isActive;
        this.isPaid = isPaid;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.dueDate = dueDate;
        this.taxAmount = taxAmount;
        this.discountAmount = discountAmount;
        this.totalAmount = totalAmount;
    }
    
    public Long getInvoiceId() {
        return invoiceId;
    }
    
    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }
    
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
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
}
