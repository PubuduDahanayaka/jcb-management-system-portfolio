package com.example.demo.invoice_management.pattern.strategy;

import com.example.demo.invoice_management.entity.Invoice;
import org.springframework.stereotype.Component;

@Component
public class UrgentInvoiceProcessingStrategy implements InvoiceProcessingStrategy {
    
    @Override
    public String processInvoice(Invoice invoice) {
        return "URGENT processing for invoice: " + invoice.getInvoiceNumber() + 
               " - Priority handling with immediate attention - Amount: " + invoice.getAmount();
    }
    
    @Override
    public String getStrategyName() {
        return "URGENT_PROCESSING";
    }
}
