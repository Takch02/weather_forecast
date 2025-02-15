package test.weathertest001.weather.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import test.weathertest001.weather.dto.JsonResponseDto;
import java.net.URI;

@Service
@Slf4j
public class WeatherService {

    private final WebClient webClient;
    private static final String BASIC_URL = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getVilageFcst";
    private static final String SERVICE_KEY = "jEfTgvZXSekk7cYEpikMwxnGKtB%2BgHmHcDMnD1FlPhekWfm4bGfXI8Itc5iJpmmhap2PcMQ6lhNnjHjA51lI3w%3D%3D";

    public WeatherService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl(BASIC_URL).build();
    }

    public Mono<JsonResponseDto> getWeather(String x, String y) {

        TimeConfig timeConfig = new TimeConfig();
        String baseDate = timeConfig.getDate();
        String baseTime = timeConfig.getTime(); // 0200, 0500, 0800, 1100, 1400, 1700, 2000, 2300 (1일 8회)
        log.info("current date, time: {}, {}", baseDate, baseTime);
        String numOfRows = "12";
        String pageNo = "1";
        String nx = x;   // 속초 (87, 141), 상주 (81, 102), 대구(90, 91)
        String ny = y;

        String url = BASIC_URL + "?serviceKey=" + SERVICE_KEY +
                "&numOfRows=" + numOfRows + "&pageNo="+ pageNo +"&dataType=JSON" +
                "&base_date=" + baseDate + "&base_time=" + baseTime +
                "&nx=" + nx + "&ny=" + ny;

        log.info("Generated URL: {}", url); // ✅ 확인용 출력

        return webClient.get()
                .uri(URI.create(url)) // 🔹 직접 만든 URI 전달
                .retrieve()
                .bodyToMono(JsonResponseDto.class);
    }
}
