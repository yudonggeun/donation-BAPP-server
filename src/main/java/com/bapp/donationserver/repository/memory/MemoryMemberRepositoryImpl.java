package com.bapp.donationserver.repository.memory;

import com.bapp.donationserver.data.Member;
import com.bapp.donationserver.data.MemberType;
import com.bapp.donationserver.data.Wallet;
import com.bapp.donationserver.data.dto.MemberDto;
import com.bapp.donationserver.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
@RequiredArgsConstructor
public class MemoryMemberRepositoryImpl implements MemberRepository {

    private final Map<String , Member> db = new HashMap<>();
    @Override
    public void save(Member member) {
        /*Member member = new Member();
        member.setDto(memberInfo);
        if(db.get(memberInfo.getEmail()) != null){
            throw new IllegalStateException("해당 이메일로 가입한 멤버가 존재합니다.");
        }*/
        db.put(member.getEmail(), member);
        log.info("새로운 멤버 등록 class={}", member);
    }

    @Override
    public void update(String email, MemberDto memberInfo) {
        log.info("멤버 정보 수정 ={}", memberInfo);
        Member member = db.get(email);
        member.setDto(memberInfo);
    }

    @Override
    public void delete(String email) {
        log.info("멤버 삭제 ={}", email);
        db.remove(email);
    }

    @Override
    public Member findByEmail(String email) {
        log.info("멤버 조회={}", email);
        return db.get(email);
    }

    @PostConstruct
    public void init(){
        MemberDto myPageDto = new MemberDto();
        myPageDto.setEmail("ydong98@gmail.com");
        myPageDto.setName("유동근");
        myPageDto.setPassword("psasdflskdj");
        myPageDto.setNickname("test1234");
        myPageDto.setPhoneNumber("010-1111-1111");
        myPageDto.setMemberType(MemberType.ADMIN);
        myPageDto.setProfilePhotoName("picture");

        Member memberInfo = new Member();
        memberInfo.setDto(myPageDto);
        memberInfo.setWallet(new Wallet());
        db.put(memberInfo.getEmail(), memberInfo);
    }
}
