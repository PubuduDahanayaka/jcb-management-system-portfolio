package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public class PenaltyFeeOperation implements InvoiceOperation {
    
    @Override
    public String execute(String operation) {
        return "Penalty Fee: " + operation + " - billing for penalties and late fees";
    }
    
    @Override
    public InvoiceType getInvoiceType() {
        return InvoiceType.PENALTY_FEE;
    }
}
