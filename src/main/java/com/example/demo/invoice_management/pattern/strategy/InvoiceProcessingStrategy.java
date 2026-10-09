package com.example.demo.invoice_management.pattern.strategy;

import com.example.demo.invoice_management.entity.Invoice;

public interface InvoiceProcessingStrategy {
    String processInvoice(Invoice invoice);
    String getStrategyName();
}
