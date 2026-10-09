package com.example.demo.invoice_management.pattern.strategy;

import com.example.demo.invoice_management.entity.Invoice;
import org.springframework.stereotype.Component;

@Component
public class RefundInvoiceProcessingStrategy implements InvoiceProcessingStrategy {
    
    @Override
    public String processInvoice(Invoice invoice) {
        return "REFUND processing for invoice: " + invoice.getInvoiceNumber() + 
               " - Special refund handling with validation - Amount: " + invoice.getAmount();
    }
    
    @Override
    public String getStrategyName() {
        return "REFUND_PROCESSING";
    }
}
