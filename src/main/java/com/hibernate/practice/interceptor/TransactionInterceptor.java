package com.hibernate.practice.interceptor;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

@RequiredArgsConstructor
public class TransactionInterceptor {

    private final SessionFactory sessionFactory;

    // Транзакция открывается только одна на Session, из-за этого нельзя открывать
    // новую транзакцию во внутренних вызовах других методов, поэтому проверяем
    // если транзакция еще не открыта, то открываем ее и закрываем в том же методе
    // в котором она была открыта, иначе мы получим исключение из-за того что внутренний
    // вызов метода завершил транзакцию и при повторной попытке завершения транзакции методом
    // инициировавшим вызов других методов, будет выброшено исключение.
    // callMethod -> innerCallMethod -> innerCallMethod
    @RuntimeType
    public Object intercept(@SuperCall Callable<Object> call, @Origin Method method) throws Exception {
        Transaction transaction = null;
        boolean transactionStarted = false;
        if (method.isAnnotationPresent(Transactional.class)) {
            transaction = sessionFactory.getCurrentSession().getTransaction();
            if (!transaction.isActive()) {
                transaction.begin();
                transactionStarted = true;
            }
        }

        Object result;
        try {
            result = call.call();
            if (transactionStarted) {
                transaction.commit();
            }
        } catch (Exception exception) {
            if (transactionStarted) {
                transaction.rollback();
            }
            throw exception;
        }

        return result;
    }
}











