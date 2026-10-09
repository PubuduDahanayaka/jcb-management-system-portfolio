package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public class RefundOperation implements InvoiceOperation {
    
    @Override
    public String execute(String operation) {
        return "Refund: " + operation + " - processing refund transactions";
    }
    
    @Override
    public InvoiceType getInvoiceType() {
        return InvoiceType.REFUND;
    }
}
