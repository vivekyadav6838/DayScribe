package com.vktech.journalApp.service;

import com.vktech.journalApp.apiResponse.WeatherResponse;
import com.vktech.journalApp.cache.AppCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class WeatherService {

    @Autowired
    private RestTemplate restTemplate;


    @Autowired
    private RedisService redisService;
    @Value("${weather.api.key}")
    public    String apiKey ;

    @Autowired
    private AppCache appCache;

    public  WeatherResponse getWeather(String city)
    {
      try {
          WeatherResponse weatherResponse = redisService.get("Weather_of" + city, WeatherResponse.class);
          if(weatherResponse!=null)
          {
              return weatherResponse;
          }
          else {
              String finalApi = appCache.APP_CACHE.get("weather_api").replace("<api_key>", apiKey).replace("<city>", city);
              ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalApi, HttpMethod.GET, null, WeatherResponse.class);
              WeatherResponse body = response.getBody();
              if(body !=null)
              {
                  redisService.set("Weather_of" + city,body,500L);
              }
              return body;
          }
      }
      catch (Exception e)
      {
          System.err.println("Weather API call failed: " + e.getMessage());
          e.printStackTrace();

          // Rethrow with more context
          throw new RuntimeException("Weather API failed: " + e.getMessage(), e);
      }

    }

}
