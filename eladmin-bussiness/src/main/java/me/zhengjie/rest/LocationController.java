package me.zhengjie.rest;

import com.alibaba.fastjson.JSONObject;
import lombok.RequiredArgsConstructor;
import me.zhengjie.domain.Location;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/location")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LocationController {

    @PostMapping
//    @PreAuthorize("admin")
    public String getLocation(@RequestBody Location location) {
        String url = "http://api.map.baidu.com/reverse_geocoding/v3/?ak={ak}&output=json&coordtype=wgs84ll&location={lat},{lng}";
        String ak = "NRLkmp5ELkuF2gpi5qNc52HLtGAwbeBt"; // 请替换为自己的百度地图API的ak
        double lat = location.getLat();  //纬度
        double lng = location.getLng();  //经度
        RestTemplate restTemplate = new RestTemplate();
        String response = restTemplate.getForObject(url, String.class, ak, lat, lng);

        JSONObject jsonObject = JSONObject.parseObject(response);
        JSONObject addressComponent = jsonObject.getJSONObject("result").getJSONObject("addressComponent");
        String city = addressComponent.getString("city");

        return city;
    }
}
