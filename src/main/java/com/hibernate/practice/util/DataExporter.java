package com.hibernate.practice.util;

import com.hibernate.practice.entity.Account;
import com.hibernate.practice.entity.Card;
import com.hibernate.practice.entity.Client;
import com.hibernate.practice.entity.Transaction;
import com.hibernate.practice.entity.enums.AccountType;
import com.hibernate.practice.entity.enums.TransactionType;
import lombok.experimental.UtilityClass;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.hibernate.practice.util.GeneratorUtils.generateNumber;

@UtilityClass
public class DataExporter {
    public static void export(SessionFactory sessionFactory) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        Client client = saveClient(session, "everette_hubbard", ShaUtils.sha256(".3x2]}3iZ!.3"));
        Client client1 = saveClient(session, "tim_coffey", ShaUtils.sha256("d1*65u78YiJk"));
        Client client2 = saveClient(session, "delbert_glass", ShaUtils.sha256(",9o;1vB2bC£m"));

        Account account = saveAccount(session, client, generateNumber(6), BigDecimal.ZERO, AccountType.DEBIT);
        Account account1 = saveAccount(session, client1, generateNumber(6), BigDecimal.ZERO, AccountType.CREDIT);
        Account account2 = saveAccount(session, client2, generateNumber(6), BigDecimal.ZERO, AccountType.DEBIT);

        Card card = saveCard(session, generateNumber(16), generateNumber(3), LocalDate.now().plusYears(3), account);
        Card card1 = saveCard(session, generateNumber(16), generateNumber(3), LocalDate.now().plusYears(3), account1);
        Card card2 = saveCard(session, generateNumber(16), generateNumber(3), LocalDate.now().plusYears(3), account2);

        session.getTransaction().commit();
    }

    private static Client saveClient(Session session, String username, String password) {
        Client client = Client.builder()
                .username(username)
                .password(password)
                .build();

        session.persist(client);
        return client;
    }

    private static Account saveAccount(Session session, Client client, String number, BigDecimal balance, AccountType accountType) {
        Account account = Account.builder()
                .number(number)
                .balance(balance)
                .accountType(accountType)
                .build();


        account.addClient(client);
        session.persist(account);
        return account;
    }

    private static Card saveCard(Session session, String number, String cvv, LocalDate expiryDate, Account account) {
        Card card = Card.builder()
                .number(number)
                .cvv(cvv)
                .expiryDate(expiryDate)
                .build();

        card.addAccount(account);
        session.persist(card);
        return card;
    }

    private static Transaction saveTransaction(Session session, Account account, BigDecimal amount, TransactionType transactionType) {
        Transaction transaction = Transaction.builder()
                .amount(amount)
                .type(transactionType)
                .dateTime(LocalDateTime.now())
                .build();

        transaction.addAccount(account);
        session.persist(transaction);
        return transaction;
    }
}
