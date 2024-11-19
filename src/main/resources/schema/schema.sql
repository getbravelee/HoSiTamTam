package schema

CREATE TABLE users (
                       user_id INT AUTO_INCREMENT PRIMARY KEY,            -- 사용자 고유 ID
                       user_login_id VARCHAR(255) NOT NULL UNIQUE,         -- 사용자 로그인 ID (유니크)
                       user_login_password VARCHAR(255) NOT NULL,          -- 사용자 비밀번호 (해싱된 비밀번호를 저장)
                       user_nickname VARCHAR(255) NOT NULL,                -- 사용자 닉네임
                       user_email VARCHAR(255) NOT NULL UNIQUE,            -- 사용자 이메일 (유니크)
                       created_at DATETIME DEFAULT CURRENT_TIMESTAMP       -- 계정 생성일 (기본값: 현재 시간)
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