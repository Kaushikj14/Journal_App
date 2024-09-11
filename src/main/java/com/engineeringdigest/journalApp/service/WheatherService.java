package com.engineeringdigest.journalApp.service;

import com.engineeringdigest.journalApp.api_response.WheatherResponse;
import com.engineeringdigest.journalApp.cache.AppCache;
import com.engineeringdigest.journalApp.constant.Placeholders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

//@Component
@Service
public class WheatherService {

    @Value("${weather.api.key}")
    private String apiKey;

//    private static final String API = "http://api.weatherapi.com/v1/current.json?key=API_KEY&q=CITY&aqi=yes";

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private AppCache appCache;

    public WheatherResponse getWheather(String city){
//            String finalurl = appCache.APP_CACHE.get("weather_api").replace("CITY",city).replace("API_KEY",apiKey);
        String finalurl = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace("<city>",city).replace("<apiKey>",apiKey);
//        String finalurl = appCache.appCache.get(AppCache.keys.WEATHER_API.toString()).replace(Placeholders.CITY,city).replace(Placeholders.API_KEY,apiKey);

        ResponseEntity<WheatherResponse> response = restTemplate.exchange(finalurl, HttpMethod.GET, null, WheatherResponse.class);
//        response.getStatusCode();
        WheatherResponse body = response.getBody();
        return body;

//        To send post request

//        String requestBody = "{\n"+
//                "\"userName\":\"vipul\",\n"+
//                        "   \"password\":\"vipul\"\n"+
//                        "}";
//        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody);
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//        httpHeaders.set("key","value");
//        HttpEntity<String> httpEntity = new HttpEntity<>(requestBody,httpHeaders);

//
//        ResponseEntity<WheatherResponse> response = restTemplate.exchange(finalurl, HttpMethod.POST, httpEntity, WheatherResponse.class);

    }
}
