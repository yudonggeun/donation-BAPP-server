package com.bapp.donationserver.data.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private String sender;//사용자
    private String receiver;//거래처
    private Long amount;//거래 금액
    private Long balance;//남은 금액
    private LocalDateTime date;
}
