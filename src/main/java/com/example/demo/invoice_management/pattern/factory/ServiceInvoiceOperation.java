package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public class ServiceInvoiceOperation implements InvoiceOperation {
    
    @Override
    public String execute(String operation) {
        return "Service Invoice: " + operation + " - billing for services provided";
    }
    
    @Override
    public InvoiceType getInvoiceType() {
        return InvoiceType.SERVICE_INVOICE;
    }
}
