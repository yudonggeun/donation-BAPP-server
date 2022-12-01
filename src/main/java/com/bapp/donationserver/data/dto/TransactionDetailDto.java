package com.bapp.donationserver.data.dto;

import com.bapp.donationserver.data.type.TransactionType;
import com.bapp.donationserver.entity.Transaction;
import com.bapp.donationserver.entity.TransactionDetail;
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

    public TransactionDetailDto(Transaction transaction) {
        this.amount = getAmount();
        this.balance = transaction.getFromBalance();
        this.date = LocalDateTime.from(getDate());
        this.type = getType();
        this.blockChainTransactionId = transaction.getId();

        if(transaction.getDetail() != null) {
            this.sender = transaction.getDetail().getSender();
            this.receiver = transaction.getDetail().getReceiver();
            this.purpose = transaction.getDetail().getPurpose();
            this.certificateFile = transaction.getDetail().getCertificateFile();
        }
    }
}
