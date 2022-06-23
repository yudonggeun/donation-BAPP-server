package com.bapp.donationserver.data.dto;

import com.bapp.donationserver.data.type.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDetailDto {
    private String sender;//사용자
    private String receiver;//거래처
    private String purpose;//사용 용도
    private Long amount;//거래 금액
    private Long balance;//남은 금액 from balance
    private LocalDateTime date;
    private TransactionType type;
    private String blockChainTransactionId;
    private String certificateFile;//증명서 url

}
