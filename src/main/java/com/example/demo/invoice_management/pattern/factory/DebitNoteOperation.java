package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public class DebitNoteOperation implements InvoiceOperation {
    
    @Override
    public String execute(String operation) {
        return "Debit Note: " + operation + " - issuing debit adjustments";
    }
    
    @Override
    public InvoiceType getInvoiceType() {
        return InvoiceType.DEBIT_NOTE;
    }
}
