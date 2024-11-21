package com.suleekyuri.hositamtam.aptTrade;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Configuration
@Profile({"dev"})
@ConditionalOnProperty(name = "data.loading.apt.enabled", havingValue = "true")
public class AptTradeBaseData {

    @Value("${public.data.key}")
    private String apiKey;

    @Bean
    CommandLineRunner initAptTradeData(AptTradeService aptTradeService) {
        List<Integer> list = lawDongCodeSet();
        List<Integer> monthList = monthList(202301, 202411);
        return args -> {
            aptTradeService.callApiAndSaveData(apiKey, list, monthList);

            log.info("APT_TRADE_DATA SUCCESS!");
        };
    }
    static final int[] lawDongCodes = {
            11110, 11140, 11170, 11200, 11215, 11230, 11260, 11290, 11305, 11320,
            11350, 11380, 11410, 11440, 11470, 11500, 11530, 11545, 11560, 11590,
            11620, 11650, 11680, 11710, 11740, 26110, 26140, 26170, 26200, 26230,
            26260, 26290, 26320, 26350, 26380, 26410, 26440, 26470, 26500, 26530,
            26710, 27110, 27140, 27170, 27200, 27230, 27260, 27290, 27710, 27720,
            28110, 28140, 28177, 28185, 28200, 28237, 28245, 28260, 28710, 28720,
            29110, 29140, 29155, 29170, 29200, 30110, 30140, 30170, 30200, 30230,
            31110, 31140, 31170, 31200, 31710, 36110, 36110, 36110, 36110, 36110,
            36110, 36110, 36110, 36110, 36110, 36110, 36110, 36110, 36110, 36110,
            36110, 36110, 36110, 36110, 36110, 36110, 36110, 36110, 36110, 36110,
            36110, 36110, 36110, 36110, 36110, 36110, 36110, 41110, 41130, 41150,
            41170, 41190, 41210, 41220, 41250, 41270, 41280, 41290, 41310, 41360,
            41370, 41390, 41410, 41430, 41450, 41460, 41480, 41500, 41550, 41570,
            41590, 41610, 41630, 41650, 41670, 41800, 41820, 41830, 43110, 43130,
            43150, 43720, 43730, 43740, 43745, 43750, 43760, 43770, 43800, 44130,
            44150, 44180, 44200, 44210, 44230, 44250, 44270, 44710, 44760, 44770,
            44790, 44800, 44810, 44825, 46110, 46130, 46150, 46170, 46230, 46710,
            46720, 46730, 46770, 46780, 46790, 46800, 46810, 46820, 46830, 46840,
            46860, 46870, 46880, 46890, 46900, 46910, 47110, 47130, 47150, 47170,
            47190, 47210, 47230, 47250, 47280, 47290, 47730, 47750, 47760, 47770,
            47820, 47830, 47840, 47850, 47900, 47920, 47930, 47940, 48120, 48170,
            48220, 48240, 48250, 48270, 48310, 48330, 48720, 48730, 48740, 48820,
            48840, 48850, 48860, 48870, 48880, 48890, 50110, 50130, 51110, 51130,
            51150, 51170, 51190, 51210, 51230, 51720, 51730, 51750, 51760, 51770,
            51780, 51790, 51800, 51810, 51820, 51830, 52110, 52130, 52140, 52180,
            52190, 52210, 52710, 52720, 52730, 52740, 52750, 52770, 52790, 52800
    };
    private List<Integer> lawDongCodeSet() {
        List<Integer> lawDongCode = new ArrayList<>();

        for(int n : AptTradeBaseData.lawDongCodes) {
            if(!lawDongCode.contains(n)) {
                lawDongCode.add(n);
            }
        }
        Collections.sort(lawDongCode);
        return lawDongCode;
    }

    private List<Integer> monthList(int startMonth, int endMonth) {
        int current = startMonth;
        List<Integer> months = new ArrayList<>();
        while (current <= endMonth) {
            months.add(current);

            // 월 증가
            int year = current / 100;  // 년도 추출
            int month = current % 100; // 월 추출

            if (month == 12) { // 12월에서 다음 년도로
                current = (year + 1) * 100 + 1; // 다음 년도 1월
            } else {
                current++; // 월 증가
            }
        }
        return months;
    }
}

