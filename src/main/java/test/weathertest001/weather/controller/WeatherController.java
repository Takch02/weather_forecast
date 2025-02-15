package test.weathertest001.weather.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import test.weathertest001.weather.dto.CategoryValue;
import test.weathertest001.weather.dto.JsonResponseDto;
import test.weathertest001.weather.dto.TempValue;
import test.weathertest001.weather.region.Region;
import test.weathertest001.weather.region.Region_list;
import test.weathertest001.weather.service.WeatherService;

import java.util.*;


@Slf4j
@Controller
@RequestMapping("/weather")
public class WeatherController {

    private final WeatherService weatherService;

    public WeatherController(WeatherService weatherService) {

        this.weatherService = weatherService;
    }

    private List<CategoryValue> resultList = new ArrayList<>();

    /**
     * 지역 추가 / region_list에도 추가해야 함
     */
    
    @ModelAttribute("regions")
    public Map<String, String> getRegions() {

        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("속초", "sokcho");
        regions.put("상주", "sangju");
        regions.put("대구", "deagu");

        return regions;
    }

    @GetMapping("/region")
    public String start() {
        return "weather/region";
    }

    @PostMapping("/region")
    public String select_region(@RequestParam("tempRegion") String tempRegion, Model model) {

        Region_list regionList = new Region_list();
        log.info("tempRegion: {}", tempRegion);

        switch (tempRegion) {
            case "sokcho" :
            case "sangju" :
            case "deagu" :
                start(regionList.get_region(tempRegion));
                break;
            default:
                // 유효하지 않은 region 처리 (필요시)
                break;
        }
        model.addAttribute("map", temperature());
        // 동적 뷰 이름 반환 - tempRegion 값에 따라 실제 뷰 경로가 결정됨.
        return "weather/weatherInfo";
    }

    /**
     * api 요청으로 가져온 모든 값을 list화 시킴
     */
    void start(Region region) {

        List<CategoryValue> listValue = weatherService.getWeather(region.getX(), region.getY())
                .map(JsonResponseDto::getResponse)
                .map(JsonResponseDto.Response::getBody)
                .map(JsonResponseDto.Body::getItems)
                .map(JsonResponseDto.Items::getItemList)
                .flatMapMany(Flux::fromIterable)
                .map(item -> new CategoryValue(item.getCategory(), item.getFcstValue(), item.getBaseTime()))
                .collectList()
                .block(); // 🔥 동기적으로 데이터 변환 (주의: WebFlux 환경에서는 블로킹을 최소화해야 함)

        resultList = listValue;
    }

    @GetMapping("/server")
    public String server(Model model) {

        model.addAttribute("map", resultList);
        return "weather/server";
    }

    /**
     * 데이터 가공시키는 코드
     */
    public Map<Integer, TempValue> temperature() {

        Map<Integer, TempValue> map = new HashMap<>();
        TempValue tempValue = null;
        int count = 0;

        for (CategoryValue categoryValue : resultList) {

            if (tempValue == null) {
                tempValue = new TempValue();
            }

            if (categoryValue.getCategory().equals("TMP")) {
                tempValue.setTemperature(categoryValue.getValue());

                String strTime = categoryValue.getTime();
                tempValue.setTime(strTime.substring(0, 2) + ":" + strTime.substring(2));
            }


            if (categoryValue.getCategory().equals("SKY")) {
                switch (categoryValue.getValue()) {
                    case "1":
                        tempValue.setSky("맑음");
                        break;
                    case "3":
                        tempValue.setSky("구름많음");
                        break;
                    case "4":
                        tempValue.setSky("흐림");
                        break;
                }
            }

            if (categoryValue.getCategory().equals("PTY")) {
                switch (categoryValue.getValue()) {
                    case "0":
                        tempValue.setPty("비/눈 없음");
                        break;
                    case "1":
                        tempValue.setPty("비");
                        break;
                    case "2":
                        tempValue.setPty("비/눈");
                        break;
                    case "3":
                        tempValue.setPty("눈");
                        break;
                    case "4":
                        tempValue.setPty("소나기");
                        break;
                }
            }
            if (tempValue.getTemperature() != null && tempValue.getSky() != null && tempValue.getPty() != null
            && tempValue.getTime()!=null) {

                map.put(count++, tempValue);
                tempValue = null;
            }
        }
        return map;
    }

}
