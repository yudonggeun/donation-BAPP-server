package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.TransactionDto;
import com.bapp.donationserver.data.type.TransactionType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;

/**
 * 기부금 사용 내역 관련 정보 클래스
 */
@Entity
@Getter
@Setter
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
    private LocalDate date;//거래 시간
    @Column(name = "TYPE")
    private TransactionType type;
    @OneToOne(mappedBy = "transaction", fetch = FetchType.LAZY)
    private TransactionDetail detail;

    public Transaction() {
    }

    public Transaction(Wallet from, Wallet to, Long amount, TransactionType type, TransactionDetail detail) {
        this.from = from.getId();
        this.to = to.getId();
        this.fromBalance = from.getAmount() - amount;
        this.toBalance = to.getAmount() + amount;
        this.amount = amount;
        this.date = LocalDate.now();
        this.type = type;
        this.detail = detail;
    }

    public TransactionDto getDto() {
        TransactionDto dto = new TransactionDto();
        dto.setSender(getDetail().getSender());
        dto.setReceiver(getDetail().getReceiver());
        dto.setAmount(getAmount());
        dto.setBalance(getToBalance());
        dto.setDate(getDate());
        dto.setType(getType());
        dto.setPurpose(getDetail().getPurpose());

        return dto;
    }

    public void setDto(TransactionDto dto) {
        getDetail().setSender(dto.getSender());
        getDetail().setReceiver(dto.getReceiver());
        getDetail().setPurpose(dto.getPurpose());
        setAmount(dto.getAmount());
        setToBalance(dto.getBalance());
        setType(dto.getType());

    }
}
