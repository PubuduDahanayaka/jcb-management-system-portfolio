package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public class MaintenanceFeeOperation implements InvoiceOperation {
    
    @Override
    public String execute(String operation) {
        return "Maintenance Fee: " + operation + " - billing for maintenance services";
    }
    
    @Override
    public InvoiceType getInvoiceType() {
        return InvoiceType.MAINTENANCE_FEE;
    }
}
