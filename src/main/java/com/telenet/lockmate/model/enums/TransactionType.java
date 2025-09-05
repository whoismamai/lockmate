package com.telenet.lockmate.model.enums;

public enum TransactionType {
    PAYMENT, // For payments made by users
    REFUND, // For refunds issued to users
    WITHDRAWAL, // For withdrawals by finance team
    FEE, // For fees charged (e.g., late fees, service fees)
    ADJUSTMENT // For manual adjustments by admin or finance team
}
