package com.hibernate.practice.entity;

import com.hibernate.practice.entity.enums.TransactionType;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "transactions", schema = "public")
public class Transaction extends BaseEntity<Long> {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "transactions_seq")
    @SequenceGenerator(name = "transactions_seq", sequenceName = "transactions_seq", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "account_id")
    private Account account;

    @Column(name = "amount", nullable = false, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private TransactionType type;

    @Column(name = "date_time", nullable = false)
    private LocalDateTime dateTime;

    public void addAccount(Account account) {
        this.account = account;
        account.getTransactions().add(this);
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "account=" + account +
                ", amount=" + amount +
                ", type=" + type +
                '}';
    }
}
