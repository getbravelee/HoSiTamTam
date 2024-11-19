import csv
import requests

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
    return list(set(danjiList))

# 아파트 정보 가져오기
def getAptInfoData(danjiId):
    url = "https://www.zigbang.com/_next/data/AO8NL8mFDk0Di9PbIFnDU/home/apt_danjis_detail.json?area_danji_id={}".format(danjiId)
    headers = {
        "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.88 Safari/537.36"
    }
    req = requests.get(url, headers=headers)
    if req.status_code == 200:
        data = req.json()
        a = data["pageProps"]["SSRData"]["danjis"]
        filtered_data = {k: v for k, v in a.items() if k not in exclude_columns}

        room_types = data["pageProps"]["SSRData"]["danjisRoomTypes"]["room_types"]
        school_zones = data["pageProps"]["SSRData"]["schoolZones"]

        elementary = school_zones["elementary"]
        middle = school_zones["middle"]
        high = school_zones["high"]

        additional_data = {
            "room_types": room_types,
            "elementary_zone_name": elementary["zoneName"],
            "elementary_zone_code": elementary["zoneCode"],
            "middle_zone_name": middle["zoneName"],
            "middle_zone_code": middle["zoneCode"],
            "high_zone_name": high["zoneName"],
            "high_zone_code": high["zoneCode"],
        }

        filtered_data.update(additional_data)
        return filtered_data
    return None

# CSV 파일로 변경
def makeCSV(fileName, dataList):
    with open(fileName, 'w', encoding='utf-8', newline='') as f:
        writer = csv.writer(f)
        writer.writerow(dataList[0].keys())  # Write header
        for data in dataList:
            writer.writerow(data.values())
    print(f"{fileName} has been created")

# main
exclude_columns = [
    "local2_code", "local3_code", "bunji", "bcode", "시행사",
    "서비스구분", "관심단지툴팁", "isShowInquiryGuideComponent", "isNewStay",
    "jibunAddress", "분양년월", "분양년월표기", "roadview", "brand",
    "brand_img", "review_score", "review_cnt", "review_recent",
    "resident_review", "순위", "vr_hometours", "vr_hometours_summeries",
    "규제지역", "real_type", "is후분양", "zedExist", "zedMaintenance",
    "zedEnable"
]

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
