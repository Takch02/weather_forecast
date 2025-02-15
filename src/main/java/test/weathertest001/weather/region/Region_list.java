package test.weathertest001.weather.region;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Region_list {

    private List<Region> region_data;  // 속초 (87, 141), 상주 (81, 102), 대구(90, 91)

    public Region_list() {

        region_data = new ArrayList<Region>();

        region_data.add(create_region("sokcho", "87", "141"));
        region_data.add(create_region("sangju", "81", "102"));
        region_data.add(create_region("deagu", "90", "91"));
    }

    public Region create_region(String region_name, String x, String y) {

        Region region = new Region();
        region.setRegion(region_name);
        region.setX(x);
        region.setY(y);
        return region;
    }

    public Region get_region(String region_name) {

        Region region_result = new Region();

        for (Region region : region_data) {
            if (region.getRegion().equals(region_name)) {
                region_result= region;
                break;
            }
        }
        return region_result;
    }
}
