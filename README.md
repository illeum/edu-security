# edu-security
# Security_김성아
# 8주차

* 서버 측 구현
    - JWT 의존성 추가
    - JWT 유틸리티 생성
    - LoginService: 로그인 성공 시 JWT를 발급하도록 변경
    - JWT 검증 로직 삽입
    - WebConfig: JWT 인터셉터를 /api/memo/** 및 /api/me에 적용
    - /api/me 컨트롤러 추가
    - MemoApiController: JWT 검증이 통과된 요청만 처리하도록 변경

* 클라이언트 측 구현
    -