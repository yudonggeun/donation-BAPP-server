package com.bapp.donationserver.data.dto;

import com.bapp.donationserver.data.type.TransactionType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TransactionDto {
    private String id;
    private String from;
    private String to;
    private Long fromBalance;//남은 금액
    private Long toBalance;//남은 금액
    private Long amount;//거래 금액
    private LocalDateTime date;//거래 시간
    private TransactionType type;
    private String blockChainTransactionId;
    private String certificateFile;
}
