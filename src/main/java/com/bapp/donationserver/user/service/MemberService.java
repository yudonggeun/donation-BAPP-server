package com.bapp.donationserver.user.service;

import com.bapp.donationserver.data.CampaignInfo;
import com.bapp.donationserver.data.Cart;
import com.bapp.donationserver.data.MemberInfo;

import java.util.List;

public interface MemberService {

    //개인 정보 조회
    MemberInfo getMemberInformation(String email);
    //개인 정보 수정
    void updateMemberInformation(String email, MemberInfo updateMemberInformation);
    //장바구니 담기
    void putCampaignAtCart(String campaignId);
    //장바구니 삭제
    void deleteCampaignAtCart(String campaignId);
    //장바구니 전체 삭제
    void clearCart(String campaignId);
    //장바구니 내역 결제
    void buyDonationAtCart(Cart cart);
    //자신의 기부 내역 조회
    List<CampaignInfo> checkMyDonationList(String memberId);
}