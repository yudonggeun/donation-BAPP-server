package com.bapp.donationserver.entity;

import com.bapp.donationserver.data.dto.TransactionDetailDto;
import com.bapp.donationserver.data.type.TransactionType;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * 기부금 사용 내역 관련 정보 클래스
 */
@Entity
@Getter
@Setter
@Slf4j
public class Transaction {
    @Id
    @Column(name = "ID", nullable = false)
    private String id;
    @Column(name = "FROM_WALLET")
    private String from;
    @Column(name = "TO_WALLET")
    private String to;
    @Column(name = "FROM_BALANCE")
    private Long fromBalance;//남은 금액
    @Column(name = "TO_BALANCE")
    private Long toBalance;//남은 금액
    @Column(name = "AMOUNT")
    private Long amount;//거래 금액
    @Column(name = "DATE")
    private LocalDateTime date;//거래 시간
    @Enumerated(EnumType.STRING)
    @Column(name = "TYPE")
    private TransactionType type;
    @OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY)
    private TransactionDetail detail;

    public Transaction() {
    }

    public Transaction(Wallet from, Wallet to, Long amount, TransactionType type, TransactionDetail detail) {
        this.from = from.getId();
        this.to = to.getId();
        this.fromBalance = from.getAmount();
        this.toBalance = to.getAmount();
        this.amount = amount;
        this.date = LocalDateTime.now();
        this.type = type;
        this.detail = detail;
    }
}
