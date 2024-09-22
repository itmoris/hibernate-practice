package com.hibernate.practice.mapper.card;

import com.hibernate.practice.dto.card.CardCreateDto;
import com.hibernate.practice.entity.Account;
import com.hibernate.practice.entity.Card;
import com.hibernate.practice.mapper.Mapper;
import com.hibernate.practice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

import static com.hibernate.practice.util.GeneratorUtils.generateNumber;

@RequiredArgsConstructor
public class CardCreateMapper implements Mapper<CardCreateDto, Card> {
    private final AccountRepository accountRepository;

    @Override
    public Card mapFrom(CardCreateDto from) {
        Account account = accountRepository.findById(from.accountId()).orElseThrow(IllegalArgumentException::new);

        Card card = Card.builder()
                .number(generateNumber(16))
                .cvv(generateNumber(3))
                .expiryDate(LocalDate.now().plusYears(3))
                .account(account)
                .build();

        card.addAccount(account);
        return card;
    }
}
