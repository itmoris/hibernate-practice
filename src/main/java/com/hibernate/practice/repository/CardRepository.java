package com.hibernate.practice.repository;

import com.hibernate.practice.entity.Card;
import org.hibernate.Session;

public class CardRepository extends BaseRepository<Long, Card> {
    public CardRepository(Session session) {
        super(session, Card.class);
    }
}
