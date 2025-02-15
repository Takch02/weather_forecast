package test.weathertest001.weather.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class JsonResponseDto {

    @JsonIgnoreProperties(ignoreUnknown = true) // JSON의 일부 필드만 매핑할 때 필요
        @JsonProperty("response")
        private Response response;

        public Response getResponse() {
            return response;
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Response {
            @JsonProperty("body")
            private Body body;

            public Body getBody() {
                return body;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Body {
            @JsonProperty("items")
            private Items items;

            public Items getItems() {
                return items;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class Items {
            @JsonProperty("item")
            private List<WeatherItem> itemList;

            public List<WeatherItem> getItemList() {
                return itemList;
            }
        }

        @JsonIgnoreProperties(ignoreUnknown = true)
        public static class WeatherItem {
            private String baseDate;
            private String baseTime;
            private String category;
            private String fcstDate;
            private String fcstTime;
            private String fcstValue;
            private int nx;
            private int ny;

            // Getter 추가
            public String getBaseDate() { return baseDate; }
            public String getBaseTime() { return baseTime; }
            public String getCategory() { return category; }
            public String getFcstDate() { return fcstDate; }
            public String getFcstTime() { return fcstTime; }
            public String getFcstValue() { return fcstValue; }
            public int getNx() { return nx; }
            public int getNy() { return ny; }
        }
}


