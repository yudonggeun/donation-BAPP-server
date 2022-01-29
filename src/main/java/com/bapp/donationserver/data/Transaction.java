package com.bapp.donationserver.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 기부금 사용 내역 관련 정보 클래스
 */
@Getter
@AllArgsConstructor
public class Transaction {
    private String campaignId;//기부 켐페인 id
    private String sender;//사용자
    private String receiver;//거래처
    private Integer amount;//거래 금액
    private Integer balance;//남은 금액
}
