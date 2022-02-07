package com.bapp.donationserver.user.repository;

import com.bapp.donationserver.data.MemberInfo;
import com.bapp.donationserver.data.MemberType;
import com.bapp.donationserver.data.url.MyPageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Repository
@Slf4j
public class MemoryMemberRepositoryImpl implements MemberRepository {

    private Map<String ,MemberInfo> db = new HashMap<>();
    @Override
    public void save(MemberInfo memberInfo) {
        if(db.get(memberInfo.getEmail()) != null){
            throw new IllegalStateException("존재하는 멤버입니다. 업데이트를 해주세요");
        }
        db.put(memberInfo.getEmail(), memberInfo);
        log.info("새로운 멤버 등록 class={}", memberInfo);
    }

    @Override
    public void update(String email, MemberInfo memberInfo) {
        log.info("멤버 정보 수정 ={}", memberInfo);
        db.replace(email, memberInfo);
    }

    @Override
    public void delete(String email) {
        log.info("멤버 삭제 ={}", email);
        db.remove(email);
    }

    @Override
    public MemberInfo findByEmail(String email) {
        log.info("멤버 조회={}", email);
        return db.get(email);
    }

    @PostConstruct
    public void init(){
        MyPageDto myPageDto = new MyPageDto();
        myPageDto.setEmail("ydong98@gmail.com");
        myPageDto.setName("유동근");
        myPageDto.setPassword("psasdflskdj");
        myPageDto.setNickname("test1234");
        myPageDto.setPhoneNumber("010-1111-1111");
        myPageDto.setMemberType(MemberType.USER);
        myPageDto.setProfilePhotoName("picture");

        MemberInfo memberInfo = new MemberInfo();
        memberInfo.setMyPageDto(myPageDto);
        db.put(memberInfo.getEmail(), memberInfo);
    }
}
