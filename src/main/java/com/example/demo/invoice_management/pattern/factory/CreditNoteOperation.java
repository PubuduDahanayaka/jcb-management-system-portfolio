package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public class CreditNoteOperation implements InvoiceOperation {
    
    @Override
    public String execute(String operation) {
        return "Credit Note: " + operation + " - issuing credit adjustments";
    }
    
    @Override
    public InvoiceType getInvoiceType() {
        return InvoiceType.CREDIT_NOTE;
    }
}
