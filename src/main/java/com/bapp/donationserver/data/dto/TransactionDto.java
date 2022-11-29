package com.bapp.donationserver.data.dto;

import com.bapp.donationserver.data.Transaction;
import com.bapp.donationserver.data.type.TransactionType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

@Data
@Slf4j
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

    public TransactionDto(Transaction entity) {
        this.setId(entity.getId());
        this.setFrom(entity.getFrom());
        this.setTo(entity.getTo());
        this.setFromBalance(entity.getFromBalance());
        this.setToBalance(entity.getToBalance());
        this.setAmount(entity.getAmount());
        this.setDate(entity.getDate());
        this.setType(entity.getType());
        try {
            this.setBlockChainTransactionId(entity.getDetail().getHashCode());
            this.setCertificateFile(entity.getDetail().getCertificateFile());
        } catch (NullPointerException e){
            log.info("Transaction Simple Dto : don't research Transaction_detail information");
        }
    }
}
