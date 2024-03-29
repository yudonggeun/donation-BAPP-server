# 블록체인을 이용한 기부 플랫폼

> 스마트 컨트렉트를 이용한 기부 서비스 구현을 목표로 개발한 스프링 기반 서버 어플리케이션입니다.
> <br>안드로이드 앱과 상호작용합니다. 다음 링크는 안드로이드 앱 github 주소입니다.

[Android app](https://github.com/PParkcode/Capston)

### stack
* spring 
  * spring boot
  * spring data jpa
  * spring mvc

* mysql
* klaytn java api
* solidity


### 기능
1. 관리자 페이지
>    * 기부 활동 승인 및 거부
   
2. api 응답
>   * 포인트 충전, 포인트 환불, 기부하기
>   * 회원가입, 로그인
>   * 거래내역 조회 및 생성 관리

3. klaytn network api 호출

> klaytn network에서 erc20 토큰을 통해 서비스 기부 포인트 관리합니다.
<br>
> [klaytn contract 확인하기](https://baobab.scope.klaytn.com/account/0x2979C52Ad2a065DC406779c020ebe9a6b548ac22?tabId=txList)
