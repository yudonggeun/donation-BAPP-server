package com.bapp.donationserver.data.dto;

import com.bapp.donationserver.data.type.TransactionType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TransactionDto {
    private String sender;//사용자
    private String receiver;//거래처
    private String purpose;//사용 용도
    private Long amount;//거래 금액
    private Long balance;//남은 금액
    private LocalDate date;
    private TransactionType type;
}
