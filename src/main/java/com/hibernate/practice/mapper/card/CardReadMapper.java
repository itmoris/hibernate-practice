package com.hibernate.practice.mapper.card;

import com.hibernate.practice.dto.card.CardReadDto;
import com.hibernate.practice.entity.Card;
import com.hibernate.practice.mapper.Mapper;

public class CardReadMapper implements Mapper<Card, CardReadDto> {
    @Override
    public CardReadDto mapFrom(Card from) {
        return new CardReadDto(
                from.getId(),
                from.getAccount().getId(),
                from.getNumber(),
                from.getCvv(),
                from.getExpiryDate()
        );
    }
}
