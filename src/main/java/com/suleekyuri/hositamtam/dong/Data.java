package com.suleekyuri.hositamtam.dong;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity(name = "home")
@lombok.Data
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;            // 고유 ID

    private String lawDongName; // 법정동명
    private String status;      // 폐지여부
    private long lawDongCode;   // 법정동코드
}
