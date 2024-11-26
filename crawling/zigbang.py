import csv
import requests
import time

# 5레벨 geoHash 생성
def getHigherGeohash(strgh):
    returnList = []
    for i in range(10):
        returnList.append(strgh + str(i))
    startIdx = ord('b')
    endIdx = ord('z')
    for i in range(startIdx, endIdx + 1):
        if (chr(i) != 'i' and chr(i) != 'l' and chr(i) != 'o'):
            returnList.append(strgh + chr(i))
    return returnList

# 단지 id 가져오기
def getDanjiList(geohash):
    url = "https://apis.zigbang.com/apt/locals/prices/on-danjis?minPynArea=10평이하&maxPynArea=60평대이상&geohash={}".format(geohash)
    req = requests.get(url)
    items = req.json()
    danjiList = []
    if items["filtered"] != []:
        for i in items["filtered"]:
            danjiList.append(i["id"])
    return list(set(danjiList))  # 중복 제거 후 반환

# 아파트 정보 가져오기
def getAptInfoData(danjiId):
    url = "https://www.zigbang.com/_next/data/Ryz0pNdvduno7yAGdDqQF/home/apt_danjis_detail.json?area_danji_id={}".format(danjiId)
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
    }
    req = requests.get(url, headers=headers)
    if req.status_code == 200:
        data = req.json()
        a = data["pageProps"]["SSRData"]["danjis"]

        # 제외할 컬럼 리스트
        exclude_columns = [
            "시행사", "danjiPriceChart", "편의시설", "room_types", "서비스구분", "관심단지툴팁", "isShowInquiryGuideComponent", "isNewStay", "현장투어", "현장투어작성일",
            "jibunAddress", "분양년월", "분양년월표기", "roadview", "brand", "brand_img", "review_score", "review_cnt",
            "review_recent", "resident_review", "순위", "vr_hometours", "vr_hometours_summeries", "규제지역",
            "real_type", "is후분양", "zedExist", "zedMaintenance", "zedEnable"
        ]

        # 필터링한 데이터
        filtered_data = {k: v for k, v in a.items() if k not in exclude_columns}

        danjisRoomTypes = data["pageProps"]["SSRData"].get("danjisRoomTypes", None)
        school_zones = data["pageProps"]["SSRData"].get("schoolZones", None)

        # danjisRoomTypes와 school_zones가 None인 경우 처리
        if danjisRoomTypes and school_zones:
            average_rent_price = danjisRoomTypes.get("average_rent_price", "N/A")
            average_sales_price = danjisRoomTypes.get("average_sales_price", "N/A")
            minSalesPrice = danjisRoomTypes.get("minSalesPrice", "N/A")
            maxSalesPrice = danjisRoomTypes.get("maxSalesPrice", "N/A")
            # room_types = danjisRoomTypes.get("room_types", "N/A")

            elementary = school_zones.get("elementary", {})
            middle = school_zones.get("middle", {})
            high = school_zones.get("high", {})

            additional_data = {
                "average_rent_price": average_rent_price,
                "average_sales_price": average_sales_price,
                "minSalesPrice": minSalesPrice,
                "maxSalesPrice": maxSalesPrice,
                # "room_types": room_types,
                "elementary_zone_name": elementary.get("zoneName", "N/A"),
                "elementary_zone_code": elementary.get("zoneCode", "N/A"),
                "middle_zone_name": middle.get("zoneName", "N/A"),
                "middle_zone_code": middle.get("zoneCode", "N/A"),
                "high_zone_name": high.get("zoneName", "N/A"),
                "high_zone_code": high.get("zoneCode", "N/A")
            }

            filtered_data.update(additional_data)
        return filtered_data
    return None  # 데이터가 없을 경우 None 반환

# CSV 파일로 변경
def makeCSV(fileName, dataList):
    with open(fileName, 'w', encoding='utf-8', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(dataList[0].keys())  # Write header
        for data in dataList:
            writer.writerow(data.values())
    print(f"{fileName} has been created")

# 실행 시간 측정을 위한 시작 시간 기록
start_time = time.time()

# geohash 설정
geohash = ['wyd']
totalGeohash = []
for i in geohash:
    tmp4Lv = getHigherGeohash(i)
    for j in tmp4Lv:
        tmp5Lv = getHigherGeohash(j)
        totalGeohash.extend(tmp5Lv)

all_apartment_data = []  # 모든 아파트 정보를 저장할 리스트

# 아파트 정보 가져오기
for i in totalGeohash:
    DanㅌjiListPerGeohash = getDanjiList(i)
    for j in DanjiListPerGeohash:
        apt_info = getAptInfoData(j)
        if apt_info:  # 아파트 정보가 있는 경우
            all_apartment_data.append(apt_info)

# 마지막에 모든 아파트 정보를 CSV 파일로 저장
if all_apartment_data:
    makeCSV("wyd.csv", all_apartment_data)
else:
    print("No apartment data found.")

# 실행 시간 측정을 위한 종료 시간 기록
end_time = time.time()

# 소요 시간 계산 및 출력
elapsed_time = end_time - start_time
print(f"Total execution time: {elapsed_time:.2f} seconds")