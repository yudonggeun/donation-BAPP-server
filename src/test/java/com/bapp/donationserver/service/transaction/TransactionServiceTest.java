package com.bapp.donationserver.service.transaction;

import com.bapp.donationserver.entity.Member;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.service.account.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.TestInstance.Lifecycle.PER_CLASS;

@SpringBootTest
@Slf4j
@TestInstance(PER_CLASS)
class TransactionServiceTest {

    @Autowired
    AccountService accountService;
    @Autowired
    TransactionService transactionService;

    Member testMember;

    @BeforeAll
    void addTestMember(){
        MemberDto member = new MemberDto(testMember);
        member.setName("테스트이름");
        member.setMemberType(MemberType.ADMIN);
        member.setNickname("닉");
        member.setEmail("test@test.com.kr");
        member.setProfilePhotoName("path");
        member.setPhoneNumber("000-0000-0001");
        accountService.createNewMember(member);
        testMember = accountService.getMember(member.getEmail());
    }
    @Test
    void pay() {
        transactionService.pay(testMember.getEmail(), 1000L);
        Member member = accountService.getMember(testMember.getEmail());
        Assertions.assertThat(member.getWallet().getAmount()).isEqualTo(1000L);
    }

    @Test
    void getDonationHistory() {
        //생각 좀 해보자.
    }

    @Test
    void withdraw() {
//        transactionService.withdraw();
    }
}