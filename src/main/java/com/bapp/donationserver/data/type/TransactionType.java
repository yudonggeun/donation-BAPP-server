package com.bapp.donationserver.data.type;

public enum TransactionType {
    PAY,
    PAYBACK,
    DONATION,
    CANCEL_DONATION/*일반 사용자가 기부 취소하는 경우*/,
    WITHDRAW/*기부 단체 계정이 켐패인 지갑에서 인출하는 경우 */
}
