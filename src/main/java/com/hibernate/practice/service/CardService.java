package com.hibernate.practice.service;

import com.hibernate.practice.dto.card.CardCreateDto;
import com.hibernate.practice.dto.card.CardReadDto;
import com.hibernate.practice.entity.Card;
import com.hibernate.practice.mapper.card.CardCreateMapper;
import com.hibernate.practice.mapper.card.CardReadMapper;
import com.hibernate.practice.repository.CardRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class CardService {
    private final CardRepository cardRepository;
    private final CardCreateMapper cardCreateMapper;
    private final CardReadMapper cardReadMapper;

    @Transactional
    public Optional<CardReadDto> findById(Long id) {
        return cardRepository.findById(id)
                .map(cardReadMapper::mapFrom);
    }

    @Transactional
    public List<CardReadDto> findAll() {
        return cardRepository.findAll().stream()
                .map(cardReadMapper::mapFrom)
                .collect(Collectors.toList());
    }

    @Transactional
    public Long create(CardCreateDto dto) {
        Card card = cardCreateMapper.mapFrom(dto);
        return cardRepository.save(card);
    }

    @Transactional
    public boolean delete(long id) {
        Optional<Card> maybeCard = cardRepository.findById(id);
        maybeCard.ifPresent(cardRepository::delete);
        return maybeCard.isPresent();
    }
}
