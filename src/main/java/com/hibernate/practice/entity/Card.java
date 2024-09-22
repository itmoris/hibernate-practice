package com.hibernate.practice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "cards", schema = "public")
public class Card extends BaseEntity<Long> {
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cards_seq")
    @SequenceGenerator(name = "cards_seq", sequenceName = "cards_seq", allocationSize = 1)
    private Long id;

    @Column(name = "number", nullable = false, unique = true, length = 16)
    private String number;

    @Column(name = "cvv", nullable = false, length = 3)
    private String cvv;

    @Column(name = "expiry_date", nullable = false)
    private LocalDate expiryDate;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    public void addAccount(Account account) {
        this.account = account;
        account.getCards().add(this);
    }
}