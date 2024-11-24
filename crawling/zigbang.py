import csv
import time
import requests
import json

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

    danjiList = list(set(danjiList))
    return danjiList


# 리뷰 데이터에는 \r이나 \n이 섞여있는 경우 CSV파일에서 줄넘어가기가 되기 때문에 이것을 없애는 함수
def rmCR(text):
    while ("\n" in text or "\r" in text):
        text = text.replace("\n"," ")
        text = text.replace("\r", " ")
    return text


# 아파트 정보 가져오기
filtered_data = {}
def getAptInfoData(danjiId):
    global filtered_data
    url = "https://www.zigbang.com/_next/data/AO8NL8mFDk0Di9PbIFnDU/home/apt_danjis_detail.json?area_danji_id={}".format(danjiId)
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
    }
    req = requests.get(url, headers=headers)

    if req.status_code == 200:
        data = req.json()
        a = data["pageProps"]["SSRData"]["danjis"]

        exclude_columns = [
            "시행사", "danjiPriceChart",
            "서비스구분", "관심단지툴팁", "isShowInquiryGuideComponent", "isNewStay",
            "jibunAddress", "분양년월", "분양년월표기", "roadview", "brand",
            "brand_img", "review_score", "review_cnt", "review_recent",
            "resident_review", "순위", "vr_hometours", "vr_hometours_summeries",
            "규제지역", "real_type", "is후분양", "zedExist", "zedMaintenance",
            "zedEnable"
        ]

        filtered_data = {k: v for k, v in a.items() if k not in exclude_columns}

        # 안전하게 키를 가져오기 위해 get 사용
        average_rent_price = data["pageProps"]["SSRData"]["danjisRoomTypes"].get("average_rent_price", None)
        average_sales_price = data["pageProps"]["SSRData"]["danjisRoomTypes"].get("average_sales_price", None)
        minSalesPrice = data["pageProps"]["SSRData"]["danjisRoomTypes"].get("minSalesPrice", None)
        maxSalesPrice = data["pageProps"]["SSRData"]["danjisRoomTypes"].get("maxSalesPrice", None)
        room_types = data["pageProps"]["SSRData"]["danjisRoomTypes"].get("room_types", None)
        school_zones = data["pageProps"]["SSRData"].get("schoolZones", {})

        elementary = school_zones.get("elementary", {})
        middle = school_zones.get("middle", {})
        high = school_zones.get("high", {})

        additional_data = {
            "average_rent_price": average_rent_price,
            "average_sales_price": average_sales_price,
            "minSalesPrice": minSalesPrice,
            "maxSalesPrice": maxSalesPrice,
            "room_types": room_types,
            "elementary_zone_name": elementary.get("zoneName", ""),
            "elementary_zone_code": elementary.get("zoneCode", ""),
            "middle_zone_name": middle.get("zoneName", ""),
            "middle_zone_code": middle.get("zoneCode", ""),
            "high_zone_name": high.get("zoneName", ""),
            "high_zone_code": high.get("zoneCode", ""),
        }

        filtered_data.update(additional_data)

    return list(filtered_data.keys()), list(filtered_data.values())

# CSV 파일로 변경
def makeCSV(geohashName, reviewList):
    fileName = geohashName + ".csv"
    f = open(fileName, 'w', encoding='utf-8', newline='')
    wr = csv.writer(f)
    for i in reviewList:
        wr.writerow(i)
    f.close()
    print(fileName+" has been created")


# 실행 시간 측정을 위한 시작 시간 기록
start_time = time.time()

geohash = ['wyd']
totalGeohash = []
for i in geohash:
    tmp4Lv = getHigherGeohash(i)
    for j in tmp4Lv:
        tmp5Lv = getHigherGeohash(j)
        totalGeohash.extend(tmp5Lv)

all_apartment_data = []  # 모든 아파트 정보를 저장할 리스트

for i in totalGeohash:
    DanjiListPerGeohash = getDanjiList(i)
    for j in DanjiListPerGeohash:
        apt_info = getAptInfoData(j)
        if apt_info:  # 아파트 정보가 있는 경우
            all_apartment_data.append(apt_info)

# 마지막에 모든 아파트 정보를 CSV 파일로 저장
if all_apartment_data:
    makeCSV("apartment_data.csv", all_apartment_data)
else:
    print("No apartment data found.")

# 실행 시간 측정을 위한 종료 시간 기록
end_time = time.time()

# 소요 시간 계산 및 출력
elapsed_time = end_time - start_time
print(f"Total execution time: {elapsed_time:.2f} seconds")

# # main
# geohash = ['wyd','wye','wy6','wy7']
# tmp = []
# for i in geohash:
#     tmp4Lv = getHigherGeohash(i)
#     for j in tmp4Lv:
#         tmp5Lv = getHigherGeohash(j)
#         tmp.append(tmp5Lv)
#
#
# totalGeohash = []   # 4096개
# for i in tmp:
#     totalGeohash += i
#
#
# for i in totalGeohash:
#     DanjiListPerGeohash = getDanjiList(i)
#     InfoPerGeohash=[]
#     keys = []
#     for j in DanjiListPerGeohash:
#         key, values = getAptInfoData(j)
#         keys = key
#         InfoPerGeohash.append(values)
#     if len(InfoPerGeohash) > 0:
#         InfoPerGeohash.insert(0, keys)
#         makeCSV(i, InfoPerGeohash)
#     else:
#         print(i + " doesn't have any data")



# test
# InfoPerGeohash=[]
# # keys = []
# key, values = getAptInfoData("11265")
# # keys = key
# InfoPerGeohash.append(values)
# if len(InfoPerGeohash) > 0:
#     InfoPerGeohash.insert(0, key)
#     makeCSV("11265", InfoPerGeohash)