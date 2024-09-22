package com.hibernate.practice.exception;

import com.hibernate.practice.entity.Transaction;

public class TransactionException extends ApplicationException {
    public TransactionException(String message, Transaction transaction) {
        super("%s: %s".formatted(message, transaction));
    }
}
