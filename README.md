# myCommunity
> 스프링을 사용한 커뮤니티사이트
## 사용한 기술
- Java 11
- <img src="https://img.shields.io/badge/SpringBoot-6DB33F?style=flat-square&logo=springboot&logoColor=white"/>  2.7.7
- <img src="https://img.shields.io/badge/Gradle-02303A?style=flat-square&logo=gradle&logoColor=white"/>
- <img src="https://img.shields.io/badge/Thymeleaf-005F0F?style=flat-square&logo=thymeleaf&logoColor=white"/>
- <img src="https://img.shields.io/badge/Bootstrap-7952B3?style=flat-square&logo=bootstrap&logoColor=white"/>  5.2.3  
- <img src="https://img.shields.io/badge/Jquery-0769AD?style=flat-square&logo=jquery&logoColor=white"/>
- MyBatis(H2)
- Chart.js
## 기능
- 게시글 CRUD(summernote사용)
- 게시글 댓글 수 표시
- 페이징
- 회원가입(sha256), 찾기
- 로그인, 로그아웃
- 관리자 페이지
- 댓글, 대댓글
- 조회 수
- 검색
- 출석체크(1,2,3,X,1,2...)
- 게시글 정렬(최신순, 인기순)
## 이미지
###### Home 화면
<img src="https://user-images.githubusercontent.com/80504740/216536997-0b7b23d4-082f-430e-959b-7f82c7da3cbd.png" width="450px" height="300px"></img>


###### 게시판
<img src="https://user-images.githubusercontent.com/80504740/223589951-ff4be71d-6ee5-44e4-8afc-7e57f565ab05.png" width="450px" height="300px"></img>


###### 글쓰기
<img src="https://user-images.githubusercontent.com/80504740/218664990-33bfcfa1-fb87-41a7-af06-2d83d441d12e.png" width="450px" height="300px"></img>


###### 게시글
<img src="https://user-images.githubusercontent.com/80504740/223589713-da756f93-64c9-4e19-9177-4bc3e8ad6a13.png" width="450px" height="300px"></img>


###### 검색
<img src="https://user-images.githubusercontent.com/80504740/217130283-596c2167-6d4c-40f7-9c29-5a0dd9b97d55.png" width="450px" height="300px"></img>


###### 회원가입
<img src="https://user-images.githubusercontent.com/80504740/217130050-3f8d0eeb-d236-4d3e-8bda-aee1f5f0d31b.png" width="450px" height="300px"></img>


###### 관리자 화면
<img src="https://user-images.githubusercontent.com/80504740/215989038-7f734319-ac59-4a9f-87af-150cd24429d1.png" width="450px" height="300px"></img>


###### 댓글
<img src="https://user-images.githubusercontent.com/80504740/216259441-1280c98a-5921-4fbc-94c6-e0f7002fa1ed.png" width="450px" height="300px"></img>

## 공부해야 할 사항
- 클린코드
- MyBatis동적쿼리활용하기
- <img src="https://img.shields.io/badge/Springsecurity-6DB33F?style=flat-square&logo=springsecurity&logoColor=white"/>
---
2023/01/19
- login패키지추가
- login 구현
- err modal처리
---
2023/01/20
- login유지
- user정보수정
- user비밀번호수정
- user탈퇴
---
2023/01/23해야할 것
- 인터셉터
- 관리자 화면
---
2023/01/23
- 인터셉터
- 페이징(개선하기)
---
2023/01/24
- 페이징 추가
- 비밀번호 암호화 하기
---
2023/01/25
- 출석보여주기기능
---
2023/01/26
- 출석체크 수정
- 게시판상세보기에서도 게시글보기가능
---
2023/01/30
- 아이디찾기, 비밀번호찾기
---
2023/01/31
- admin페이지추가
---
2023/02/02
- 대댓글
---
2023/02/03
- 댓글 수 표시
- 댓글작성, 수정.. 후 스크롤 이벤트
---
2023/02/07
- user비밀번호 암호화(MessageDigest(단방향암호화 복호화x)) 
---
2023/02/14
- summernote추가(https://summernote.org/)
- 게시글표시수정
---
2023/03/08
- summernote 다중 이미지 업로드
