import time
from datetime import datetime, timedelta
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
import pymysql

# 크롬 웹드라이버 실행 경로
# path = "C:\\Users\\SSAFY\\Desktop\\pjt\\chromedriver-win64\\chromedriver.exe"
path = '/Users/isu/Downloads/chromedriver-mac-arm64/chromedriver'

# 크롤링할 주소 - 뉴스 -> 경제 -> 부동산
target_url = "https://news.naver.com/breakingnews/section/101/260/"

# 크롬 드라이버 설정 및 실행
service = Service(executable_path=path)
options = webdriver.ChromeOptions()
options.add_argument("--no-sandbox")
options.add_argument("--disable-dev-shm-usage")
options.add_argument("--headless")  # 헤드리스 모드 추가
options.add_argument("window-size=1920x1080")  # 창 크기 설정 (필요시)
driver = webdriver.Chrome(service=service, options=options)

# MySQL DB 연결 설정
db = pymysql.connect(
    host="localhost",
    user="ssafy",  # DB 사용자명
    password="ssafy",  # DB 비밀번호
    database="hositamtam",  # DB 이름
    charset="utf8mb4"
)

cursor = db.cursor()

# 설정할 기준 시간: 현재 시점에서 2주 전으로 설정
what_times_ago = datetime.now() - timedelta(hours=3)

# 2주 이전의 오래된 기사 삭제
delete_query = "DELETE FROM news WHERE news_date < %s"
cursor.execute(delete_query, (what_times_ago,))
db.commit()

# 기사 데이터 저장 및 업데이트 함수
def save_or_update_article(title, url, recommendation, formatted_date):
    query = """
    INSERT INTO news (title, url, recommendation, news_date)
    VALUES (%s, %s, %s, %s)
    ON DUPLICATE KEY UPDATE
        recommendation = VALUES(recommendation),
        news_date = VALUES(news_date)
    """
    values = (title, url, recommendation, formatted_date)
    cursor.execute(query, values)
    db.commit()

# 크롤링할 페이지 열기
driver.get(target_url)
driver.implicitly_wait(10)

# 뉴스 리스트 전체 박스에서 ul/li 구조를 통해 뉴스 항목 접근
i = 1
while True:
    # 뉴스 리스트 전체 박스 접근
    news_container = driver.find_element(By.XPATH, '//*[@id="newsct"]/div[2]/div/div[1]/div[' + str(i) + ']')

    # ul/li 구조에서 각 li 요소에 접근
    news_list = news_container.find_elements(By.XPATH, ".//ul/li")

    for news in news_list:
        # 뉴스 URL
        url_element = news.find_element(By.XPATH, ".//a")
        url = url_element.get_attribute('href')

        # 기사 상세 페이지로 이동
        driver.execute_script("window.open(arguments[0]);", url)
        driver.switch_to.window(driver.window_handles[1])

        # 제목 가져오기
        try:
            title = driver.find_element(By.XPATH, '//*[@id="title_area"]/span').text
        except:
            title = ""

        # 날짜 및 추천 수 가져오기
        try:
            # 날짜 문자열을 가져옴
            date_str = driver.find_element(By.XPATH, '//*[@id="ct"]/div[1]/div[3]/div[1]/div/span').text

            # 오전/오후 구분하여 파싱
            if '오전' in date_str:
                date_str = date_str.replace("오전", "AM")
            elif '오후' in date_str:
                date_str = date_str.replace("오후", "PM")

            # 형식 변환 및 datetime 객체 생성
            news_date = datetime.strptime(date_str, "%Y.%m.%d. %p %I:%M")
            formatted_date = news_date.strftime("%Y-%m-%d %H:%M")

            # 추천 수 가져오기
            recommendation_element = driver.find_element(By.XPATH, '//*[@id="commentFontGroup"]/div[1]/div/a/span[2]')
            recommendation = int(recommendation_element.text.replace(",", "")) if recommendation_element.text.isdigit() else 0

        except Exception as e:
            print("Error fetching article details:", e)
            formatted_date = None  # 날짜 정보가 없으므로 None으로 설정
            recommendation = 0  # 추천수 기본값
            driver.close()
            driver.switch_to.window(driver.window_handles[0])
            continue  # 다음 기사로 넘어감

        # 뉴스 날짜가 기준 시간(2주 이전)보다 이른 경우, 크롤링 종료
        if news_date < what_times_ago:
            print("Reached news older than 2 weeks. Stopping crawl.")
            driver.quit()
            cursor.close()
            db.close()
            exit()  # 스크립트 종료

        # 데이터베이스에 저장 또는 업데이트
        save_or_update_article(title, url, recommendation, formatted_date)

        # 출력 테스트
        print("Title:", title)
        print("URL:", url)
        print("Recommendation:", recommendation)
        print("Date:", formatted_date)
        print("-" * 40)

        # 현재 기사 탭 닫고, 원래 페이지로 돌아옴
        driver.close()
        driver.switch_to.window(driver.window_handles[0])

    # `i` 값 증가
    i += 1

    # 6개의 기사마다 더보기 버튼 클릭
    if i % 6 == 0:
        try:
            more_button = driver.find_element(By.XPATH, "//a[contains(text(), '더보기')]")
            if more_button.is_displayed():
                more_button.click()
                time.sleep(2)
            else:
                break
        except Exception:
            print("No more '더보기' button found. Exiting...")
            break

# 드라이버 종료 및 DB 연결 종료
driver.quit()
cursor.close()
db.close()
