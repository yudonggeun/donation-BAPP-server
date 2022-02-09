package com.bapp.donationserver.data;

import com.bapp.donationserver.data.dto.TransactionDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

/**
 * 기부금 사용 내역 관련 정보 클래스
 */
@Data
@AllArgsConstructor
public class Transaction {
    private String campaignId;//기부 켐페인 id
    private String sender;//사용자
    private String receiver;//거래처
    private Integer amount;//거래 금액
    private Integer balance;//남은 금액

    public TransactionDto getDto(){
        TransactionDto dto = new TransactionDto();
        dto.setSender(getSender());
        dto.setReceiver(getReceiver());
        dto.setAmount(getAmount());
        dto.setBalance(getBalance());

        return dto;
    }

    public void setDto(TransactionDto dto){
        this.setSender(dto.getSender());
        this.setReceiver(dto.getReceiver());
        this.setAmount(dto.getAmount());
        this.setBalance(dto.getBalance());
    }
}
