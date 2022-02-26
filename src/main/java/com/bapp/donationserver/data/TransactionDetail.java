package com.bapp.donationserver.data;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class TransactionDetail {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String sender;//사용자
    private String receiver;//거래처
    private String purpose;//사용 용도
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "TRANSACTION_ID")
    private Transaction transaction;
    @Column(name = "CERTIFICATE_FILE")
    private String certificateFile;
    private String hashCode;//블록체인 TX 해쉬 값
}
