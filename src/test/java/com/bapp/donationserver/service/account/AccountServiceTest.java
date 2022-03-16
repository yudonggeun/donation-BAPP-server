package com.bapp.donationserver.service.account;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.dto.CampaignSimpleDto;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.data.type.MemberType;
import com.bapp.donationserver.exception.IllegalUserDataException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.TestInstance.Lifecycle.*;

@TestInstance(PER_CLASS)
@SpringBootTest
@Slf4j
class AccountServiceTest {

    @Autowired private AccountService accountService;

    String email = "test@test.com";
    String password = "qwerasdf2@";
    String name = "김테스트";
    String nickname = "test_admin";
    String phoneNumber = "010-0000-0000";
    MemberType memberType = MemberType.USER;
    String profilePath = "profile-path";

    @BeforeAll
    void initMember() {

        MemberDto myPageDto = new MemberDto();
        myPageDto.setEmail(email);
        myPageDto.setName(name);
        myPageDto.setPassword(password);
        myPageDto.setNickname(nickname);
        myPageDto.setPhoneNumber(phoneNumber);
        myPageDto.setMemberType(memberType);
        myPageDto.setProfilePhotoName(profilePath);

        accountService.newMember(myPageDto);
        log.info("테스트 데이터 db 입력");
    }

    @DisplayName("입력, 조회 양호")
    @Test
    void getMember() {
        //조회
        Member member = accountService.getMember(email);
        log.info("member={}", member);
        //검증
        //입력한 데이터가 잘 입력되었는지? 잘 조회되는가?
        assertThat(member).isNotNull();
        assertThat(member.getEmail()).isEqualTo(email);
        assertThat(member.getName()).isEqualTo(name);
        assertThat(member.getPassword()).isEqualTo(password);
        assertThat(member.getNickname()).isEqualTo(nickname);
        assertThat(member.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(member.getMemberType()).isSameAs(memberType);
        assertThat(member.getProfilePhotoName()).isEqualTo(profilePath);
    }

    @DisplayName("잘못된 id로 멤버 조회")
    @Test
    void errorGetMember(){
        Assertions.assertThrows(IllegalUserDataException.class, () -> {
            //InvalidDataAccessApiUsageException 예외가 발생하는 이유와 이에 대한 exceptio 해결 방법 고안
            accountService.getMember("ㅋㅋㅋ");
        });
    }

    @DisplayName("수정 양호")
    @Test
    void setMember() {
        Member member = accountService.getMember(email);
        MemberDto dto = new MemberDto();

        String email = "test@test.com";
        String password = "qwerasdf2@";
        String name = "김테스트";
        String nickname = "test_admin";
        String phoneNumber = "010-0000-0000";
        MemberType memberType = MemberType.USER;
        String profilePath = "profile-path";

        dto.setMemberType(memberType);
        dto.setEmail(email);
        dto.setPassword(password);
        dto.setName(name);
        dto.setNickname(nickname);
        dto.setPhoneNumber(phoneNumber);
        dto.setProfilePhotoName(profilePath);

        accountService.updateMember(member, dto);

        Member updateMember = accountService.getMember(dto.getEmail());
        assertThat(updateMember.getEmail()).isEqualTo(dto.getEmail());
        assertThat(updateMember.getName()).isEqualTo(dto.getName());
        assertThat(updateMember.getPassword()).isEqualTo(dto.getPassword());
        assertThat(updateMember.getNickname()).isEqualTo(dto.getNickname());
        assertThat(updateMember.getPhoneNumber()).isEqualTo(dto.getPhoneNumber());
        assertThat(updateMember.getMemberType()).isSameAs(dto.getMemberType());
        assertThat(updateMember.getProfilePhotoName()).isEqualTo(dto.getProfilePhotoName());
    }

    @DisplayName("기부 내역 조회 양호")
    @Test
    void checkMyDonationList() {
        Member member = accountService.getMember(email);
        List<CampaignSimpleDto> campaignSimpleDtos = accountService.checkMyDonationList(member);
        assertThat(campaignSimpleDtos).isNotNull();
        campaignSimpleDtos.forEach(dto -> assertThat(dto).isNotNull());
    }

    @DisplayName("회원가입 양호")
    @Test
    void newMember() {
        MemberDto member = createNewMemberDto("test@email.com");

        accountService.newMember(member);

        Member saveMember = accountService.getMember(email);

        //검증
        assertThat(saveMember).isNotNull();
        assertThat(saveMember.getMemberType()).isSameAs(memberType);
        assertThat(saveMember.getName()).isEqualTo(name);
        assertThat(saveMember.getPassword()).isEqualTo(password);
        assertThat(saveMember.getEmail()).isEqualTo(email);
        assertThat(saveMember.getPhoneNumber()).isEqualTo(phoneNumber);
        assertThat(saveMember.getNickname()).isEqualTo(nickname);
        assertThat(saveMember.getProfilePhotoName()).isEqualTo(profilePath);
        assertThat(saveMember.getWallet()).isNotNull();
//        assertThat(saveMember.getWallet().getAmount()).isSameAs(0l);
    }

    @DisplayName("로그인 양호")
    @Test
    void login() {
        Assertions.assertDoesNotThrow(() -> {
            Member member = accountService.login(email, password);
            assertThat(member.getEmail()).isEqualTo(email);
            assertThat(member.getPassword()).isEqualTo(password);
        });
    }

    @DisplayName("탈퇴 양호")
    @Test
    void dropMember() {
        MemberDto newMember = createNewMemberDto("drop@email.com");
        accountService.newMember(newMember);

        Member member = accountService.getMember(newMember.getEmail());
        accountService.dropMember(member);

        Assertions.assertThrows(Exception.class, () -> accountService.getMember(newMember.getEmail()));

    }

    MemberDto createNewMemberDto(String key){
        MemberDto member = new MemberDto();

        MemberType memberType = MemberType.USER;
        String name = "new";
        String phoneNumber = "0l0-1234-3432";
        String email = key;
        String password = "password1234";
        String nickname = "mynick";
        String profilePhotoName = "pridkfjls";

        member.setName(name);
        member.setMemberType(memberType);
        member.setNickname(nickname);
        member.setPhoneNumber(phoneNumber);
        member.setEmail(email);
        member.setPassword(password);
        member.setNickname(nickname);
        member.setProfilePhotoName(profilePhotoName);

        return member;
    }
}