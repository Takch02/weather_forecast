package test.weathertest001.weather.service;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Data
public class TimeConfig {

    private String date;
    private String time;

    public TimeConfig() {

        LocalDateTime now = LocalDateTime.now();

        DateTimeFormatter date = DateTimeFormatter.ofPattern("yyyyMMdd");
        String formattedDate = now.format(date);

        DateTimeFormatter time = DateTimeFormatter.ofPattern("HHmm");
        int formattedTime = Integer.parseInt(now.format(time));
        String stringTime  = currentTime(formattedTime);

        this.date = formattedDate;
        this.time = stringTime;

    }

    public static String currentTime(int time) {

        int[] timeSet = {200, 500, 800, 1100, 1400, 1700, 2000, 2300};
        int returnValue = timeSet[0];

        for (int result : timeSet) {
            if (time >=  result) {
                returnValue = result;
            }
            else{
                break;
            }
        }
        return String.format("%04d", returnValue);
    }
}
