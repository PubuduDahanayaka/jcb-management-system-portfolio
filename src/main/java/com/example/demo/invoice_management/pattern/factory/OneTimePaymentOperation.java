package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public class OneTimePaymentOperation implements InvoiceOperation {
    
    @Override
    public String execute(String operation) {
        return "One Time Payment: " + operation + " - single payment transaction";
    }
    
    @Override
    public InvoiceType getInvoiceType() {
        return InvoiceType.ONE_TIME_PAYMENT;
    }
}
