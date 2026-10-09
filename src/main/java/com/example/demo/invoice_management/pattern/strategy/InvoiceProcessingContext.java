package com.example.demo.invoice_management.pattern.strategy;

import com.example.demo.invoice_management.entity.Invoice;
import com.example.demo.invoice_management.entity.InvoiceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class InvoiceProcessingContext {
    
    @Autowired
    private StandardInvoiceProcessingStrategy standardStrategy;
    
    @Autowired
    private UrgentInvoiceProcessingStrategy urgentStrategy;
    
    @Autowired
    private BulkInvoiceProcessingStrategy bulkStrategy;
    
    @Autowired
    private RefundInvoiceProcessingStrategy refundStrategy;
    
    private InvoiceProcessingStrategy strategy;
    
    public void setStrategy(InvoiceProcessingStrategy strategy) {
        this.strategy = strategy;
    }
    
    public String processInvoice(Invoice invoice) {
        if (strategy == null) {
            strategy = selectStrategyByInvoiceType(invoice.getInvoiceType());
        }
        return strategy.processInvoice(invoice);
    }
    
    private InvoiceProcessingStrategy selectStrategyByInvoiceType(InvoiceType invoiceType) {
        return switch (invoiceType) {
            case REFUND -> refundStrategy;
            case PENALTY_FEE -> urgentStrategy;
            case SERVICE_INVOICE, EQUIPMENT_RENTAL -> bulkStrategy;
            default -> standardStrategy;
        };
    }
    
    public String getCurrentStrategyName() {
        return strategy != null ? strategy.getStrategyName() : "NO_STRATEGY_SELECTED";
    }
}
