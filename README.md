
"Spring Boot + Spring Data JPA 기반 웹 게시판 프로젝트입니다.
계층 구조는 Controller → Service → Repository → Entity(Model)로 구성되어 있으며,
JPA를 활용해 데이터베이스와 객체 간 매핑을 처리하고,
메서드 이름 기반 쿼리 정의 방식으로 게시글, 댓글, 회원 관련 CRUD 및 검색 기능을 구현했습니다."✔️ Spring Boot 사용 → 설정 자동화, 빠른 개발 가능

✔️ Spring Data JPA 사용 → 직접 쿼리 작성 최소화, 객체 중심 개발

✔️ DTO/Entity 분리 → 역할 명확하게

✔️ 페이징 처리, 검색 기능 → Pageable, ContainingIgnoreCase 등 활용

✔️ Repository는 JpaRepository 상속 → 기본 CRUD + 커스텀 메서드 제공

✔️ 서비스 계층 분리 → 비즈니스 로직 깔끔하게 관리
"Spring Boot + JPA 기반 계층형 구조의 게시판 프로젝트입니다. Repository는 JpaRepository 기반이고, 메서드 이름 기반 쿼리로 CRUD 및 검색을 구현했습니다."






✅ Spring Boot + JPA 게시판 웹 프로젝트
🔧 기술 스택

Backend: Spring Boot, Spring Data JPA, JSP, JSTL

DB: MariaDB

기타: Apache POI (엑셀 다운로드), AJAX (댓글 비동기 처리)

📌 프로젝트 개요
Spring Boot + Spring Data JPA 기반의 게시판 웹 애플리케이션입니다.
Controller → Service → Repository → Entity 계층 구조로 구성되어 있으며,
메서드 이름 기반 쿼리를 활용해 게시글, 댓글, 사용자 기능의 CRUD 및 검색을 구현했습니다.

✅ 핵심 특징
Spring Boot 사용: 설정 최소화로 빠른 개발 가능, 내장 톰캣 활용

Spring Data JPA: 객체 중심 데이터 처리, 메서드 이름 기반 쿼리로 생산성 향상

Entity 중심 처리: DTO를 따로 분리하지 않고 Entity로 화면과 DB 양쪽 처리

JPA 페이징 & 검색: Pageable, ContainingIgnoreCase 등 활용해 리스트 페이지네이션 및 키워드 검색 구현

JpaRepository 상속: 기본 CRUD + 커스텀 메서드 조합으로 복잡한 쿼리 없이도 기능 확장

Service 계층 분리: 비즈니스 로직을 Controller에서 분리해 코드 가독성과 재사용성 향상

👥 사용자 관리 및 권한 처리
로그인 사용자 정보를 HttpSession 기반 Map 형태로 관리 (loginUser)

사용자 권한(일반/관리자)에 따라 카테고리 접근 제한

일반 사용자: 자유, 질문, 도안 게시글만 작성 가능

관리자: 공지사항만 작성 가능

프론트엔드(JSP)에서는 세션 값을 활용하여 UI 요소(작성/수정/삭제 버튼 등)를 동적으로 제어

📝 추가 구현 기능
AJAX 기반 댓글 CRUD: 댓글 작성/수정/삭제 기능을 비동기로 처리해 사용자 경험 향상

엑셀 다운로드 기능: Apache POI를 이용해 게시글 목록을 엑셀 파일로 추출 가능

튜토리얼 영상 페이지: 유튜브 링크 기반으로 썸네일, 제목, 영상 연결 자동 처리
→ 카테고리 순서를 지정하여 유저가 직관적으로 접근할 수 있도록 구성

게시글 검색 + 카테고리 필터: 공지사항은 일반 사용자 검색 결과에서 제외되도록 조건 처리

🧠 기술적 고민 & 해결 경험
권한 체크의 유지보수성을 높이기 위해 세션 내 사용자 정보를 Map 구조로 관리 → 조건문 단순화, UI 제어 직관화

게시글/댓글 작성자 본인 여부 확인 시 JSP 내 조건문, JS에서의 인증 검사, Controller 레벨에서의 보안 체크를 다단계로 처리해 안전성 확보

유튜브 URL 파싱 로직 구현 시 다양한 링크 형태 (youtu.be, youtube.com/watch, embed) 지원하도록 유연하게 설계
