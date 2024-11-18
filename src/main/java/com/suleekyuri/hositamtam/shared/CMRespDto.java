package com.suleekyuri.hositamtam.shared;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CMRespDto<T> {
    private int code; // 성공: 1, 실패: -1
    private String msg; // 실행 결과
    private T data;
}
