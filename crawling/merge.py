import pandas as pd
from glob import glob

file_names = glob("../../processedData/*.csv")  # 폴더 내의 모든 csv 파일 목록을 불러온다
total = pd.DataFrame()  # 빈 데이터프레임 생성

for file_name in file_names:
    temp = pd.read_csv(file_name, sep=',', encoding='utf-8')  # CSV 파일을 하나씩 열어 임시 데이터프레임으로 생성
    total = pd.concat([total, temp], ignore_index=True)  # 데이터프레임을 합칠 때 인덱스를 재설정

total.to_csv("../../processedData/total.csv", index=False)  # 결과를 total.csv로 저장 (index 제외)