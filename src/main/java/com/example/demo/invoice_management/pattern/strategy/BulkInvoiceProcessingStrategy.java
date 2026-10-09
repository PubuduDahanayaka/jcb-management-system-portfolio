package com.example.demo.invoice_management.pattern.strategy;

import com.example.demo.invoice_management.entity.Invoice;
import org.springframework.stereotype.Component;

@Component
public class BulkInvoiceProcessingStrategy implements InvoiceProcessingStrategy {
    
    @Override
    public String processInvoice(Invoice invoice) {
        return "BULK processing for invoice: " + invoice.getInvoiceNumber() + 
               " - Batch processing with optimized handling - Amount: " + invoice.getAmount();
    }
    
    @Override
    public String getStrategyName() {
        return "BULK_PROCESSING";
    }
}
