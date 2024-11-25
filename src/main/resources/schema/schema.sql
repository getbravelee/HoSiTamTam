package schema

CREATE TABLE favorite (
                          user_id BIGINT NOT NULL,        -- 사용자 ID
                          apt_id VARCHAR(255) NOT NULL,   -- 아파트 ID (Apartment 테이블의 id와 일치)
                          is_favorite BOOLEAN NOT NULL,   -- 즐겨찾기 여부
                          PRIMARY KEY (user_id, apt_id),  -- 복합 기본 키
                          FOREIGN KEY (user_id) REFERENCES users(user_id),  -- 사용자 테이블 참조
                          FOREIGN KEY (apt_id) REFERENCES Apartment(id)    -- 아파트 테이블 참조
);

CREATE TABLE users (
                       user_id BIGINT AUTO_INCREMENT PRIMARY KEY,                -- 사용자 고유 ID
                       user_login_id VARCHAR(255) NOT NULL UNIQUE,             -- 사용자 로그인 ID (유니크)
                       user_login_password VARCHAR(255) NOT NULL,              -- 사용자 비밀번호 (해싱된 비밀번호를 저장)
                       user_nickname VARCHAR(255) NOT NULL,                    -- 사용자 닉네임
                       user_email VARCHAR(255) NOT NULL UNIQUE,                -- 사용자 이메일 (유니크)
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP,         -- 계정 생성일 (기본값: 현재 시간)
                       updated_at DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,  -- 계정 정보 수정일 (자동으로 갱신)
                       is_deleted BOOLEAN DEFAULT FALSE,                       -- 삭제 여부 (기본값: FALSE)
                       deleted_at DATETIME NULL                                -- 삭제된 날짜 (NULL이면 삭제되지 않음)
);

CREATE TABLE news (
                      id INT AUTO_INCREMENT PRIMARY KEY,       -- 자동 증가하는 ID
                      title VARCHAR(500) NOT NULL,             -- 뉴스 제목
                      url VARCHAR(255) NOT NULL,               -- 뉴스 URL
                      recommendation INT DEFAULT 0,            -- 추천 수 (없으면 0으로 설정)
                      news_date DATETIME,                      -- 뉴스 게시 날짜
                      saved_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP  -- 데이터 저장 시간 (기본값: 현재 시간)
);

CREATE TABLE Apartment (
                           id VARCHAR(255) PRIMARY KEY,
                           name VARCHAR(255),
                           local1 VARCHAR(255),
                           local2 VARCHAR(255),
                           local3 VARCHAR(255),
                           oldAddress VARCHAR(255),
                           roadAddress VARCHAR(255),
                           lat DECIMAL(10, 8),
                           lng DECIMAL(11, 8),
                           사용승인일 VARCHAR(255),
                           분양세대수 INT,
                           시공사 VARCHAR(255),
                           총세대수 INT,
                           총동수 INT,
                           최고층수 INT,
                           최저층수 INT,
                           가구당주차대수 DECIMAL(3, 1),
                           총주차대수 INT,
                           주차위치 VARCHAR(255),
                           난방방식 VARCHAR(255),
                           난방연료 VARCHAR(255),
                           편의시설 JSON,  -- JSON 형식으로 데이터를 저장
                           용적율 DECIMAL(5, 2),
                           건폐율 DECIMAL(5, 2),
                           건설사 VARCHAR(255),
                           관리사무소전화번호 VARCHAR(20),
                           description TEXT,
                           내진설계 CHAR(1), -- Y or N
                           image VARCHAR(255),
                           현장투어 JSON, -- JSON 형식의 데이터 저장
                           현장투어작성일 VARCHAR(255),
                           danjiPriceChart JSON, -- JSON 형식의 데이터 저장
                           relateDanji JSON, -- JSON 형식의 데이터 저장
                           room_types JSON, -- JSON 형식의 데이터 저장
                           elementary_zone_name VARCHAR(255),
                           elementary_zone_code VARCHAR(20),
                           middle_zone_name VARCHAR(255),
                           middle_zone_code VARCHAR(20),
                           high_zone_name VARCHAR(255),
                           high_zone_code VARCHAR(20)
);

-- ??시 ??구 ??동 처럼 띄어 쓰기 2개로만 이루어진 local address 저
CREATE TABLE localSigudong (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY, -- 고유 식별자
                               law_dong_name VARCHAR(255) NOT NULL, -- 법정동명
                               sigungu_name VARCHAR(255) NOT NULL, -- 시/군/구 이름
                               region_name VARCHAR(255) NOT NULL -- 읍/면/동 이름
);

INSERT INTO localSigudong (law_dong_name, sigungu_name, region_name)
SELECT
    law_dong_name,
    SUBSTRING_INDEX(SUBSTRING_INDEX(law_dong_name, ' ', 2), ' ', -1) AS sigungu_name, -- 시/군/구
    SUBSTRING_INDEX(law_dong_name, ' ', -1) AS region_name -- 읍/면/동
FROM home
WHERE
        status = '존재' -- 상태가 '존재'인 것만 추출
  AND (LENGTH(law_dong_name) - LENGTH(REPLACE(law_dong_name, ' ', ''))) = 2; -- 띄어쓰기 2개

-- 댓글
CREATE TABLE comment (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 댓글 ID
                         user_id BIGINT NOT NULL,  -- 작성자 ID (사용자 테이블과 연관)
                         nickname VARCHAR(255) NOT NULL,  -- 작성자 닉네임
                         content TEXT NOT NULL,  -- 댓글 내용
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 작성일
                         likes INT DEFAULT 0,  -- 댓글 좋아요 수
                         image_urls TEXT,  -- 이미지 URL (최대 3개 이미지)
                         apt_id varchar(255) NOT NULL,  -- 아파트 ID (댓글이 달리는 아파트)
                         FOREIGN KEY (user_id) REFERENCES users(user_id),  -- 사용자 테이블과 연관 (외래키)
                         FOREIGN KEY (apt_id) REFERENCES apartment(id)  -- 아파트 테이블과 연관 (외래키)
);
CREATE TABLE comment_likes (
                               id BIGINT AUTO_INCREMENT PRIMARY KEY,  -- 좋아요 ID
                               comment_id BIGINT NOT NULL,  -- 댓글 ID (comment 테이블과 연관)
                               user_id BIGINT NOT NULL,  -- 좋아요를 누른 사용자 ID
                               created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,  -- 좋아요를 누른 시간
                               UNIQUE (comment_id, user_id),  -- 동일한 댓글에 중복 좋아요를 방지
                               FOREIGN KEY (comment_id) REFERENCES comment(id),
                               FOREIGN KEY (user_id) REFERENCES users(user_id)  -- 사용자 테이블과 연관 (외래키)
);