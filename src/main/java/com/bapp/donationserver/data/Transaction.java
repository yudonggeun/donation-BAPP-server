package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.TransactionDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.UUID;

/**
 * 기부금 사용 내역 관련 정보 클래스
 */
@Entity
@Getter
@Setter
public class Transaction {
    @Id
    private String  id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CAMPAIGN_ID")
    private Campaign campaign;//기부 켐페인 id
    private String sender;//사용자
    private String receiver;//거래처
    private Long amount;//거래 금액
    private Long balance;//남은 금액
    private LocalDate date;//거래 시간

    public Transaction() {
        this.id = UUID.randomUUID().toString();
    }

    public Transaction(Campaign campaign, String sender, String receiver, Long amount, Long balance) {
        this.id = UUID.randomUUID().toString();
        this.campaign = campaign;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.balance = balance;
        this.date = LocalDate.now();
    }

    public TransactionDto getDto() {
        TransactionDto dto = new TransactionDto();
        dto.setSender(getSender());
        dto.setReceiver(getReceiver());
        dto.setAmount(getAmount());
        dto.setBalance(getBalance());
        dto.setDate(getDate());

        return dto;
    }

    public void setDto(TransactionDto dto) {
        this.setSender(dto.getSender());
        this.setReceiver(dto.getReceiver());
        this.setAmount(dto.getAmount());
        this.setBalance(dto.getBalance());
    }
}
