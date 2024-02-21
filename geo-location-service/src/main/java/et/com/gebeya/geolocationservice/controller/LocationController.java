package et.com.gebeya.geolocationservice.controller;

import et.com.gebeya.geolocationservice.dto.GetLocationRequest;
import et.com.gebeya.geolocationservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/geo-location")
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/get-parking-lots")
    public ResponseEntity<List<GeoRadiusResponse>> getLocation(@ModelAttribute GetLocationRequest request){
        return ResponseEntity.ok(locationService.getPointsWithinRadius(request));
    }
}
