package com.example.demo.invoice_management.repository;

import com.example.demo.invoice_management.entity.Invoice;
import com.example.demo.invoice_management.entity.InvoiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
    Optional<Invoice> findByInvoiceNumberAndIsActiveTrue(String invoiceNumber);
    
    List<Invoice> findByInvoiceTypeAndIsActiveTrue(InvoiceType invoiceType);
    
    List<Invoice> findByIsActiveTrue();
    
    @Query("SELECT i FROM Invoice i WHERE i.invoiceDate BETWEEN :startDate AND :endDate AND i.isActive = true")
    List<Invoice> findByInvoiceDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT i FROM Invoice i WHERE i.isPaid = :isPaid AND i.isActive = true")
    List<Invoice> findByIsPaidAndIsActiveTrue(@Param("isPaid") Boolean isPaid);
    
    @Query("SELECT i FROM Invoice i WHERE i.dueDate <= :date AND i.isPaid = false AND i.isActive = true")
    List<Invoice> findOverdueInvoices(@Param("date") LocalDate date);
    
    @Query("SELECT i FROM Invoice i WHERE i.dueDate BETWEEN :startDate AND :endDate AND i.isActive = true")
    List<Invoice> findByDueDateBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
    
    @Query("SELECT i FROM Invoice i WHERE i.amount >= :minAmount AND i.isActive = true")
    List<Invoice> findByAmountGreaterThanEqual(@Param("minAmount") BigDecimal minAmount);
    
    @Query("SELECT i FROM Invoice i WHERE i.amount <= :maxAmount AND i.isActive = true")
    List<Invoice> findByAmountLessThanEqual(@Param("maxAmount") BigDecimal maxAmount);
    
    @Query("SELECT i FROM Invoice i WHERE i.amount BETWEEN :minAmount AND :maxAmount AND i.isActive = true")
    List<Invoice> findByAmountBetween(@Param("minAmount") BigDecimal minAmount, @Param("maxAmount") BigDecimal maxAmount);
    
    @Query("SELECT i FROM Invoice i WHERE i.paymentMethod = :paymentMethod AND i.isActive = true")
    List<Invoice> findByPaymentMethodAndIsActiveTrue(@Param("paymentMethod") String paymentMethod);
    
    @Query("SELECT i FROM Invoice i WHERE i.createdAt BETWEEN :startDate AND :endDate AND i.isActive = true")
    List<Invoice> findByCreatedAtBetween(@Param("startDate") LocalDateTime startDate, @Param("endDate") LocalDateTime endDate);
    
    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.invoiceType = :invoiceType AND i.isActive = true")
    Long countByInvoiceTypeAndIsActiveTrue(@Param("invoiceType") InvoiceType invoiceType);
    
    @Query("SELECT COUNT(i) FROM Invoice i WHERE i.isPaid = :isPaid AND i.isActive = true")
    Long countByIsPaidAndIsActiveTrue(@Param("isPaid") Boolean isPaid);
    
    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.isPaid = true AND i.isActive = true")
    BigDecimal getTotalPaidAmount();
    
    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.isPaid = false AND i.isActive = true")
    BigDecimal getTotalUnpaidAmount();
    
    @Query("SELECT SUM(i.totalAmount) FROM Invoice i WHERE i.invoiceType = :invoiceType AND i.isActive = true")
    BigDecimal getTotalAmountByInvoiceType(@Param("invoiceType") InvoiceType invoiceType);
    
    @Query("SELECT AVG(i.totalAmount) FROM Invoice i WHERE i.isActive = true")
    BigDecimal getAverageInvoiceAmount();
    
    @Query("SELECT i FROM Invoice i WHERE i.createdAt >= :date AND i.isActive = true ORDER BY i.createdAt DESC")
    List<Invoice> findRecentInvoices(@Param("date") LocalDateTime date);
    
    boolean existsByInvoiceNumber(String invoiceNumber);
}
