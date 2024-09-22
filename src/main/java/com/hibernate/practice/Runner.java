package com.hibernate.practice;

import com.hibernate.practice.dto.account.AccountCreateDto;
import com.hibernate.practice.dto.account.AccountReadDto;
import com.hibernate.practice.dto.client.ClientReadDto;
import com.hibernate.practice.dto.transaction.TransactionDepositDto;
import com.hibernate.practice.dto.transaction.TransactionTransferDto;
import com.hibernate.practice.dto.transaction.TransactionWithdrawDto;
import com.hibernate.practice.entity.enums.AccountType;
import com.hibernate.practice.interceptor.TransactionInterceptor;
import com.hibernate.practice.interceptor.ValidateInterceptor;
import com.hibernate.practice.mapper.account.AccountCreateMapper;
import com.hibernate.practice.mapper.account.AccountReadMapper;
import com.hibernate.practice.mapper.card.CardCreateMapper;
import com.hibernate.practice.mapper.card.CardReadMapper;
import com.hibernate.practice.mapper.client.ClientCreateMapper;
import com.hibernate.practice.mapper.client.ClientReadMapper;
import com.hibernate.practice.mapper.client.ClientUpdateMapper;
import com.hibernate.practice.mapper.transaction.TransactionDepositMapper;
import com.hibernate.practice.mapper.transaction.TransactionReadMapper;
import com.hibernate.practice.mapper.transaction.TransactionWithdrawMapper;
import com.hibernate.practice.repository.AccountRepository;
import com.hibernate.practice.repository.CardRepository;
import com.hibernate.practice.repository.ClientRepository;
import com.hibernate.practice.repository.TransactionRepository;
import com.hibernate.practice.service.AccountService;
import com.hibernate.practice.service.CardService;
import com.hibernate.practice.service.ClientService;
import com.hibernate.practice.service.TransactionService;
import com.hibernate.practice.util.DataExporter;
import com.hibernate.practice.util.HibernateUtils;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.dynamic.DynamicType;
import net.bytebuddy.implementation.MethodDelegation;
import net.bytebuddy.matcher.ElementMatchers;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Proxy;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public class Runner {
    private static final SessionFactory sessionFactory = HibernateUtils.buildSessionFactory();

    public static void main(String[] args) {
        DataExporter.export(sessionFactory);

        Session session = makeSessionProxy();

        ClientRepository clientRepository = new ClientRepository(session);
        ClientReadMapper clientReadMapper = new ClientReadMapper();
        ClientCreateMapper clientCreateMapper = new ClientCreateMapper();
        ClientUpdateMapper clientUpdateMapper = new ClientUpdateMapper();

        AccountRepository accountRepository = new AccountRepository(session);
        AccountReadMapper accountReadMapper = new AccountReadMapper();
        AccountCreateMapper accountCreateMapper = new AccountCreateMapper(clientRepository);

        CardRepository cardRepository = new CardRepository(session);
        CardReadMapper cardReadMapper = new CardReadMapper();
        CardCreateMapper cardCreateMapper = new CardCreateMapper(accountRepository);

        TransactionRepository transactionRepository = new TransactionRepository(session);
        TransactionReadMapper transactionReadMapper = new TransactionReadMapper();
        TransactionDepositMapper transactionDepositMapper = new TransactionDepositMapper(accountRepository);
        TransactionWithdrawMapper transactionWithdrawMapper = new TransactionWithdrawMapper(accountRepository);

        ClientService clientService = makeProxy(ClientService.class, clientRepository, clientReadMapper, clientCreateMapper, clientUpdateMapper);
        AccountService accountService = makeProxy(AccountService.class, accountRepository, accountCreateMapper, accountReadMapper);
        CardService cardService = makeProxy(CardService.class, cardRepository, cardCreateMapper, cardReadMapper);
        TransactionService transactionService = makeProxy(TransactionService.class, transactionRepository, transactionReadMapper, transactionDepositMapper, transactionWithdrawMapper);

        ClientReadDto clientReadDto = clientService.findById(1L).orElseThrow();
        accountService.create(new AccountCreateDto(clientReadDto.id(), AccountType.DEBIT));

        List<AccountReadDto> accounts = accountService.findAllByClientId(clientReadDto.id());

        AccountReadDto accountOne = accounts.get(0);
        AccountReadDto accountTwo = accounts.get(1);

        transactionService.deposit(new TransactionDepositDto(accountOne.id(), BigDecimal.valueOf(1000)));
        transactionService.withdraw(new TransactionWithdrawDto(accountOne.id(), BigDecimal.valueOf(50)));
        transactionService.transfer(new TransactionTransferDto(accountOne.id(), accountTwo.id(), BigDecimal.valueOf(100)));
    }

    private static Session makeSessionProxy() {
        return (Session) Proxy.newProxyInstance(
                SessionFactory.class.getClassLoader(),
                new Class[]{Session.class},
                (proxy, method, args1) -> method.invoke(sessionFactory.getCurrentSession(), args1)
        );
    }

    private static <T> T makeProxy(Class<T> clazz, Object... initargs) {
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor(sessionFactory);
        ValidateInterceptor validateInterceptor = new ValidateInterceptor();

        Class<?>[] argsTypes = Arrays.stream(initargs)
                .map(Object::getClass)
                .toArray(Class<?>[]::new);

        try (
                DynamicType.Unloaded<T> make = new ByteBuddy()
                        .subclass(clazz)
                        .method(ElementMatchers.isDeclaredBy(clazz))
                        .intercept(
                                MethodDelegation.to(validateInterceptor)
                                        .andThen(MethodDelegation.to(transactionInterceptor))
                        )
                        .make()
        ) {
            return make.load(clazz.getClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor(argsTypes)
                    .newInstance(initargs);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException |
                 NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }
}
