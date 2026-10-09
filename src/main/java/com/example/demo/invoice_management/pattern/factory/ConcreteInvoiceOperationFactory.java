package com.example.demo.invoice_management.pattern.factory;

import com.example.demo.invoice_management.entity.InvoiceType;
import org.springframework.stereotype.Component;

@Component
public class ConcreteInvoiceOperationFactory implements InvoiceOperationFactory {
    
    @Override
    public InvoiceOperation createOperation(InvoiceType invoiceType) {
        return switch (invoiceType) {
            case SERVICE_INVOICE -> new ServiceInvoiceOperation();
            case EQUIPMENT_RENTAL -> new EquipmentRentalOperation();
            case MAINTENANCE_FEE -> new MaintenanceFeeOperation();
            case PENALTY_FEE -> new PenaltyFeeOperation();
            case REFUND -> new RefundOperation();
            case CREDIT_NOTE -> new CreditNoteOperation();
            case DEBIT_NOTE -> new DebitNoteOperation();
            case MONTHLY_SUBSCRIPTION -> new MonthlySubscriptionOperation();
            case ONE_TIME_PAYMENT -> new OneTimePaymentOperation();
            case DEPOSIT -> new DepositOperation();
        };
    }
}
