package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.TransactionDto;
//import com.sun.istack.NotNull;
import lombok.Data;

//import javax.persistence.Entity;
//import javax.persistence.Id;
import java.time.LocalDateTime;

/**
 * 기부금 사용 내역 관련 정보 클래스
 */
//@Entity
@Data
public class Transaction {
//    @Id
    private String campaignId;//기부 켐페인 id
//    @NotNull
    private String sender;//사용자
//    @NotNull
    private String receiver;//거래처
//    @NotNull
    private Long amount;//거래 금액
//    @NotNull
    private Long balance;//남은 금액
//    @NotNull
    private LocalDateTime date;//거래 시간

    public Transaction() {
    }

    public Transaction(String campaignId, String sender, String receiver, Long amount, Long balance) {
        this.campaignId = campaignId;
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
        this.balance = balance;
        this.date = LocalDateTime.now();
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
