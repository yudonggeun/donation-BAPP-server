package com.bapp.donationserver.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
public class Wallet {
    @Id
    private String id;//accountAddress
    private Long amount;
    private String privateKey;
    @OneToMany(mappedBy = "from", fetch = FetchType.LAZY)
    private List<Transaction> transactions = new ArrayList<>();

    //수정 필요 : 실제 블록체인 지갑의 주소를 id로 설정한다. 지금은 uuid 로 임시로 할당해서 구축
    public Wallet() {
        id = UUID.randomUUID().toString();
        amount = 0L;
    }
}
