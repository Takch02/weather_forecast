package test.weathertest001.weather.dto;

import lombok.Data;

@Data
public class TempValue {

    private String temperature;
    private String sky;  // 맑음(1), 구름많음(3), 흐림(4)
    private String pty;  // 없음(0), 비(1), 비/눈(2), 눈(3), 소나기(4)
    private String time;
    private String date;
}
