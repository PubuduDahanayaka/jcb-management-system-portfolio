package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public class MonthlySubscriptionOperation implements InvoiceOperation {
    
    @Override
    public String execute(String operation) {
        return "Monthly Subscription: " + operation + " - recurring subscription billing";
    }
    
    @Override
    public InvoiceType getInvoiceType() {
        return InvoiceType.MONTHLY_SUBSCRIPTION;
    }
}
