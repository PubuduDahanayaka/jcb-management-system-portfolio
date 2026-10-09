package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;

public interface InvoiceOperation {
    String execute(String operation);
    InvoiceType getInvoiceType();
}
