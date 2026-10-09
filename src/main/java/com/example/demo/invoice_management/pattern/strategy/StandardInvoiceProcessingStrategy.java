package com.example.demo.invoice_management.pattern.strategy;

import com.example.demo.invoice_management.entity.Invoice;
import org.springframework.stereotype.Component;

@Component
public class StandardInvoiceProcessingStrategy implements InvoiceProcessingStrategy {
    
    @Override
    public String processInvoice(Invoice invoice) {
        return "Standard processing for invoice: " + invoice.getInvoiceNumber() + 
               " - Amount: " + invoice.getAmount() + " - Type: " + invoice.getInvoiceType();
    }
    
    @Override
    public String getStrategyName() {
        return "STANDARD_PROCESSING";
    }
}
