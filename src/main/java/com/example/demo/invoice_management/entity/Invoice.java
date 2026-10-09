package com.example.demo.invoice_management.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "invoices")
public class Invoice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "invoice_id")
    private Long invoiceId;
    
    @NotBlank(message = "Invoice number is required")
    @Size(min = 5, max = 20, message = "Invoice number must be between 5 and 20 characters")
    @Column(name = "invoice_number", unique = true, nullable = false, length = 20)
    private String invoiceNumber;
    
    @NotNull(message = "Invoice date is required")
    @Column(name = "invoice_date", nullable = false)
    private LocalDate invoiceDate;
    
    @NotNull(message = "Amount is required")
    @DecimalMin(value = "0.01", message = "Amount must be greater than 0")
    @Column(name = "amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal amount;
    
    @NotNull(message = "Invoice type is required")
    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type", nullable = false)
    private InvoiceType invoiceType;
    
    @NotBlank(message = "Title is required")
    @Size(min = 5, max = 100, message = "Title must be between 5 and 100 characters")
    @Column(name = "title", nullable = false, length = 100)
    private String title;
    
    @Size(max = 500, message = "Description cannot exceed 500 characters")
    @Column(name = "description", length = 500)
    private String description;
    
    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    
    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "is_paid", nullable = false)
    private Boolean isPaid = false;
    
    @Column(name = "payment_date")
    private LocalDate paymentDate;
    
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;
    
    @Column(name = "due_date")
    private LocalDate dueDate;
    
    @Column(name = "tax_amount", precision = 10, scale = 2)
    private BigDecimal taxAmount;
    
    @Column(name = "discount_amount", precision = 10, scale = 2)
    private BigDecimal discountAmount;
    
    @Column(name = "total_amount", precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    public Invoice() {}
    
    public Invoice(String invoiceNumber, LocalDate invoiceDate, BigDecimal amount, 
                  InvoiceType invoiceType, String title, String description) {
        this.invoiceNumber = invoiceNumber;
        this.invoiceDate = invoiceDate;
        this.amount = amount;
        this.invoiceType = invoiceType;
        this.title = title;
        this.description = description;
        this.totalAmount = amount;
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
