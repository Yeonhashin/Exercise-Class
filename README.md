# 🧘 Pilates Workout Project 시스템 사양서

## 1. 프로젝트 개요

### 목적
- 필라테스 수업에 대한 예약·취소 및 대기 예약 기능을 제공하는 웹 시스템.
- 사용자는 실시간으로 잔여 인원을 확인하고, 정원 초과 시 대기 등록 가능.

### 개발 목표
<개인>
- 자바 스프링 기반 프로젝트로 기술 공부용

<프로젝트>
- 캘린더와 표 형식의 수업 리스트 제공으로 직관적인 UI와 명확한 수업 예약 상태 표시
- 수업 정원에 따른 예약과 대기 예약 관리

### 개발 스택
| 구분 | 기술 |
|------|------|
| Backend | Spring Framework 5.0.7, Java 11 |
| Frontend | Thymeleaf, JavaScript, jQuery |
| Database | MySQL 8.0.28 |
| ORM | MyBatis 3.5.9 + MyBatis-Spring |
| Build/Deploy | Maven, AWS |
| Tools | IntelliJ, GitHub |

---
---

## 2. 시스템 아키텍처

### 주요 구성도


### 주요 모듈
| 모듈 | 설명 |
|------|------|
| `User` | 회원가입, 로그인 |
| `Class` | 수업정보 관리 (시간, 강사, 정원 등) |
| `Reservation` | 예약/취소/대기예약 처리 |
| `Notice` | 게시판 관리 |

---
---

## 3. 데이터베이스 구조
### class_info
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | INT (PK, AUTO_INCREMENT) | 수업 ID |
| class_type_id | INT | 수업 타입 ID (class_type 테이블 참조) |
| class_time_id | INT | 수업 시간 ID (class_time 테이블 참조) |
| class_point_id | INT | 수업 지점 ID (class_point 테이블 참조) |
| instructor_id | INT | 강사 ID (instructor 테이블 참조) |
| class_date | VARCHAR(10) | 수업 일자 (yyyy-MM-dd 형식) |
| maximum | INT | 정원 수 (예약 가능한 최대 인원) |
| create_dt | DATETIME | 생성일시 |
| update_dt | DATETIME | 수정일시 |
| updated | VARCHAR(10) | 최종 수정자 |


### class_point
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | INT (PK, AUTO_INCREMENT) | 수업 지점 ID |
| class_point_name | VARCHAR(30) | 수업 지점 명 |
| class_point_maximum | INT | 지점 최대 인원수 |
| class_point_memo | VARCHAR(45) | 지점 설명 |
| create_dt | DATETIME | 생성일시 |
| update_dt | DATETIME | 수정일시 |


### class_time
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | INT (PK, AUTO_INCREMENT) | 시간 ID |
| class_start_time | VARCHAR(20) | 수업 시작 시간 |
| class_end_time | VARCHAR(20) | 수업 종료 시간  |
| create_dt | DATETIME | 생성일시 |
| update_dt | DATETIME | 수정일시 |


### class_type
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | INT (PK, AUTO_INCREMENT) | 수업 타입 ID |
| class_type_name | VARCHAR(20) | 수업 타입 명 |
| class_name | VARCHAR(20) | 수업 명 |
| class_memo | VARCHAR(45) | 수업 메모 |
| class_level | INT  | 수업 레벨 |
| create_dt | DATETIME | 생성일시 |
| update_dt | DATETIME | 수정일시 |


### instructor_info
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | INT (PK, AUTO_INCREMENT) | 강사 ID |
| class_point_id | INT | 수업 지점 ID |
| instructor_name | VARCHAR(10) | 강사 이름 |
| create_dt | DATETIME | 생성일시 |
| update_dt | DATETIME | 수정일시 |


### notice
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | INT (PK, AUTO_INCREMENT) | 공지사항 ID |
| title | VARCHAR(60) | 제목 |
| content | LONGTEXT | 본문 내용 |
| hit | INT | 조회수 |
| display_flag | INT | 표시 여부 (1=표시, 0=비표시) |
| create_dt | DATETIME | 생성일시 |
| update_dt | DATETIME | 수정일시 |


### user
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | INT (PK, AUTO_INCREMENT) | 사용자 ID |
| email | VARCHAR(50) | 이메일 (로그인 ID) |
| name | VARCHAR(10) | 사용자 이름 |
| password | INT | 비밀번호 |
| delete_fg | CHAR(1) | 삭제 여부 (0=정상, 1=삭제) |
| last_login_dt | DATETIME | 마지막 로그인 일시 |
| create_dt | DATETIME | 생성일시 |
| update_dt | DATETIME | 수정일시 |


### user_reservation
| 컬럼명 | 타입 | 설명 |
|--------|------|------|
| id | INT (PK, AUTO_INCREMENT) | 예약 ID |
| user_id | INT | 사용자 ID (user 테이블 참조) |
| class_id | INT | 수업 ID (class_info 테이블 참조) |
| cancel_flag | INT | 예약 취소 여부 (0=정상, 1=취소) |
| create_dt | DATETIME | 생성일시 |
| update_dt | DATETIME | 수정일시 |
| updated_user | VARCHAR(45) | 최종 수정자 |

---
---

## 4. 주요 화면 사양

### 4.1 회원가입 화면 
  1. 이름, Email, Password 입력으로 회원가입 기능
  2. 입력한 값에 대한 유효성 검사

### 4.2 로그인 화면 
  1. `user`테이블 기반 로그인 기능
  2. Email 기억하기 체크시, 일주일간 세션 상 Email 데이터를 보관으로 로그인 화면에 자동 표시 가능

### 4.3 수업 일람 화면 (캘린더) 
  1. 현재부터 5일간의 날짜/시간대별 수업 정보 표시 
  2. 현재 시간 기준 수업 시작 시간이 지난 경우 수업 예약 불가 표시
  3. 로그인한 사용자가 예약하지 않은 수업에 대해서는 수업 예약, 예약한 수업에 대해서는 수업 예약 취소 버튼 표시
  4.  수업 잔여인원이 1이상인 경우 일반예약, 0이하인 경우 대기예약 기능

### 4.4 수업 일람 화면 (표)  
  1. 현재 시간 기준 수업 시작 시간이 지나지 않은 수업에 대한 날짜/시간대별 수업 정보가 표시  
  2. 로그인한 사용자가 예약하지 않은 수업에 대해서는 수업 예약, 예약한 수업에 대해서는 수업 예약 취소 버튼 표시
  3. 수업 잔여인원이 1이상인 경우 일반예약, 0이하인 경우 대기예약 기능

### 4.5 예약 수업 일람 화면 
  1. 로그인한 사용자가 예약한 이력을 표시`user_reservation` (`user_id=로그인한 사용자의 ID`) 
  2. 예약 취소시  `user_reservation`(`cancel_flag=1`)  

### 4.6 공지사항 화면
  1.`notice`테이블 기반 공지사항 정보 표시

---
---

## 5. 주요 로직 

### 5.1 회원가입
- **로직**  
  1. `user`에 Email 중복 검사
  3. `user`에 새 행 생성 

### 5.2 로그인
- **로직**  
  1. 로그인시 입력한 Email, Password 기반  `user`read
  2. 로그인 성공시 `user`에 최종 로그인 일시 갱신 (`last_login_dt=현재시간`) 

### 5.2 수업 표시
- **로직**  
  1. `class_info`테이블 기반 수업 정보 표시
  2. `user`테이블 데이터 기반 예약 정보 표시
  3. 예약 잔여인원 계산 
`class_info`테이블 vacancy - 해당 수업을 예약한 `user_reservation`테이블의 ID 수 (`cancel_flag=0`)  

### 5.3 일반 및 대기 예약
- **조건**: 잔여 정원 > 0
- **로직**  
  1. 수업 정원 - 취소되지 않은 해당 수업 예약건수 id 카운트
  2. `user_reservation`에 새 행 생성 (`cancel_flag=0`, `wait_flag=NULL`)  
  3. 트랜잭션 단위로 정원 초과 방지 처리  
  4. 예약/대기예약 성공시 비동기 처리로 메일 전송 

### 5.4 일반 및 대기 예약 취소
- **로직**  
  1. 해당 사용자의 예약 행 `cancel_flag=1`  
  2. 예약/대기예약 성공시 비동기 처리로 메일 전송
