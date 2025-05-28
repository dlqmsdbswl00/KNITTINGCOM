
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
