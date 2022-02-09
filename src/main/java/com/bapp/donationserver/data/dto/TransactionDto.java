package com.bapp.donationserver.data.dto;

import lombok.Data;

@Data
public class TransactionDto {
    private String sender;//사용자
    private String receiver;//거래처
    private Integer amount;//거래 금액
    private Integer balance;//남은 금액
}
