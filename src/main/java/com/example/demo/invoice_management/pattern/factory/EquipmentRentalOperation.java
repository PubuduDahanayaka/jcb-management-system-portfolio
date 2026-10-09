package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public class EquipmentRentalOperation implements InvoiceOperation {
    
    @Override
    public String execute(String operation) {
        return "Equipment Rental: " + operation + " - billing for equipment rental";
    }
    
    @Override
    public InvoiceType getInvoiceType() {
        return InvoiceType.EQUIPMENT_RENTAL;
    }
}
