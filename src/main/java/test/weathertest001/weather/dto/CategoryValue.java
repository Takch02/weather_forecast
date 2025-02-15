package test.weathertest001.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class CategoryValue {

    private String category;
    private String value;
    private String time;

    public CategoryValue(String category, String value, String time) {
        this.category = category;
        this.value = value;
        this.time = time;
    }
}
