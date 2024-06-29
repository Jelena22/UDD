package com.example.ddmdemo.infrastructure;


import com.example.ddmdemo.dto.GeolocationResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(value = "locationClient", url = "https://api.geoapify.com/v1/geocode")
public interface LocationClient {

    @GetMapping("/search")
    GeolocationResponseDTO getGeolocation(@RequestParam String apiKey, @RequestParam String text, @RequestParam String format);
}
