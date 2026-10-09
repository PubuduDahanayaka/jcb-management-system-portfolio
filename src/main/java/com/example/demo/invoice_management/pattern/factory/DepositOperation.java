package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public class DepositOperation implements InvoiceOperation {
    
    @Override
    public String execute(String operation) {
        return "Deposit: " + operation + " - processing deposit payments";
    }
    
    @Override
    public InvoiceType getInvoiceType() {
        return InvoiceType.DEPOSIT;
    }
}
