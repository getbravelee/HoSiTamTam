-- 아파트 매매
CREATE TABLE apt_trade (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           apt_name VARCHAR(100) NOT NULL,         -- 단지명
                           build_year INT,                         -- 건축년도
                           buyer_type VARCHAR(50),                 -- 매수자 유형
                           deal_amount BIGINT,                     -- 거래금액 (만원 단위)
                           deal_year INT NOT NULL,                 -- 계약년도
                           deal_month INT NOT NULL,                -- 계약월
                           deal_day INT,                           -- 계약일
                           dealing_type VARCHAR(50),               -- 거래유형 (직거래/중개거래)
                           exclu_use_area DECIMAL(10, 2),          -- 전용면적
                           floor INT,                              -- 층
                           jibun VARCHAR(50),                      -- 지번
                           land_leasehold_gbn CHAR(1),             -- 토지임대부 여부
                           sgg_code VARCHAR(5),                    -- 지역코드
                           umd_name VARCHAR(50),                   -- 법정동명
                           estate_agent_address VARCHAR(300)       -- 중개사 소재지
);

-- 아파트 전월세
CREATE TABLE apt_rent (
                          id BIGINT AUTO_INCREMENT PRIMARY KEY,          -- 고유 식별자
                          apt_name VARCHAR(255) NOT NULL,               -- 아파트 이름
                          build_year INT NOT NULL,                      -- 건축 연도
                          contract_term VARCHAR(255),                   -- 계약 기간
                          contract_type VARCHAR(255),                   -- 계약 유형
                          deal_day INT NOT NULL,                        -- 거래 일
                          deal_month INT NOT NULL,                      -- 거래 월
                          deal_year INT NOT NULL,                       -- 거래 연도
                          deposit varchar(50) NOT NULL,                      -- 보증금
                          exclu_use_area DECIMAL(10, 2) NOT NULL,       -- 전용 면적
                          floor INT NOT NULL,                           -- 층수
                          jibun VARCHAR(255),                           -- 지번
                          monthly_rent BIGINT NOT NULL,                 -- 월세
                          pre_deposit BIGINT,                           -- 이전 보증금
                          pre_monthly_rent BIGINT,                      -- 이전 월세
                          sgg_code VARCHAR(10) NOT NULL,               -- 시군구 코드
                          umd_name VARCHAR(255) NOT NULL,              -- 읍면동명
                          use_rr_right VARCHAR(255),                   -- 사용 권리
                          created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP, -- 생성 일시
                          updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP -- 갱신 일시
);

-- 법정동
CREATE TABLE home (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      law_dong_name VARCHAR(255) NOT NULL, -- 법정동명
                      status VARCHAR(50) NOT NULL,         -- 폐지여부
                      law_dong_code BIGINT NOT NULL        -- 법정동코드
);

-- 구주소(코드 5자리)
CREATE TABLE old_sgg (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         old_law_dong_name VARCHAR(255) NOT NULL, -- 수정된 법정동명
                         law_dong_code_prefix VARCHAR(5) NOT NULL -- 법정동 코드 접두사
);

-- 법정동 -> 구주소
INSERT INTO old_sgg (old_law_dong_name, law_dong_code_prefix)
SELECT
    CASE
        WHEN law_dong_name LIKE '강원특별자치도%' THEN REPLACE(law_dong_name, '강원특별자치도', '강원도')
        WHEN law_dong_name LIKE '%광역시%' THEN REPLACE(law_dong_name, '광역시', '시')
        WHEN law_dong_name LIKE '전북특별자치도%' THEN REPLACE(law_dong_name, '전북특별자치도', '전라북도')
        WHEN law_dong_name LIKE '제주특별자치도%' THEN REPLACE(law_dong_name, '제주특별자치도', '제주도')
        WHEN law_dong_name LIKE '세종특별자치시%' THEN REPLACE(law_dong_name, '세종특별자치시', '세종시')
        WHEN law_dong_name LIKE '서울특별시%' THEN REPLACE(law_dong_name, '서울특별시', '서울시')
        ELSE law_dong_name
        END AS old_law_dong_name,
    SUBSTRING(law_dong_code, 1, 5) AS law_dong_code_prefix
FROM
    home
WHERE
    status = '존재'
  AND (LENGTH(law_dong_name) - LENGTH(REPLACE(law_dong_name, ' ', ''))) = 1;

-- 트랜잭션 분리해서 실행시키기

ALTER TABLE apt_rent
    ADD COLUMN sgg_name VARCHAR(255);
ALTER TABLE apt_rent ADD COLUMN old_address VARCHAR(500);
ALTER TABLE apt_trade ADD COLUMN old_address VARCHAR(500);
ALTER TABLE apt_trade ADD COLUMN sgg_name VARCHAR(500);

CREATE INDEX idx_law_dong_code ON home (law_dong_code);
CREATE INDEX idx_sgg_code ON apt_rent (sgg_code);

-- sgg_code의 특정 범위로 트랜잭션을 쪼개서 여러번 실행 시켜서 update 2: 1~6분 소요 sgg_code가 뒤로 갈 수록 느려져서 문제인 것 같다
UPDATE apt_trade at
    JOIN (
    SELECT
    law_dong_code_prefix,
    old_law_dong_name
    FROM old_sgg
    where law_dong_code_prefix between '10000' and '60000'
    ) o
ON at.sgg_code = o.law_dong_code_prefix
    SET at.sgg_name = o.old_law_dong_name
WHERE at.sgg_code BETWEEN '10000' AND '60000'; -- 범위 제한

-- 임시 테이블 사용: 필요한 데이터를 한 번에 추출하여 임시 테이블에 저장한 후 JOIN 시 사용하면, 반복적으로 SELECT 쿼리를 실행하지 않아도 된다.

-- 1. 임시 테이블 생성
CREATE TEMPORARY TABLE temp_old_sgg AS
SELECT law_dong_code_prefix, old_law_dong_name
FROM old_sgg
WHERE law_dong_code_prefix BETWEEN '10000' AND '60000';

-- 2. apt_trade 업데이트
UPDATE apt_trade at
    JOIN temp_old_sgg o
ON at.sgg_code = o.law_dong_code_prefix
    SET at.sgg_name = o.old_law_dong_name
WHERE at.sgg_code BETWEEN '10000' AND '60000';

-- 2. apt_rent 업데이트
UPDATE apt_rent ar
    JOIN temp_old_sgg o
ON ar.sgg_code = o.law_dong_code_prefix
    SET ar.sgg_name = o.old_law_dong_name
WHERE ar.sgg_code BETWEEN '10000' AND '60000';

-- 3. 임시 테이블 삭제
DROP TEMPORARY TABLE temp_old_sgg;

-- 구주소 만들기
UPDATE apt_rent
SET old_address = CONCAT(
        COALESCE(sgg_name, ''), ' ',
        COALESCE(umd_name, ''), ' ',
        COALESCE(jibun, '')
                  );

UPDATE apt_trade
SET old_address = CONCAT(
        COALESCE(sgg_name, ''), ' ',
        COALESCE(umd_name, ''), ' ',
        COALESCE(jibun, '')
                  );