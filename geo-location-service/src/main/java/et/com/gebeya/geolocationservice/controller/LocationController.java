package et.com.gebeya.geolocationservice.controller;

import et.com.gebeya.geolocationservice.dto.GetLocationRequest;
import et.com.gebeya.geolocationservice.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class LocationController {
    private final LocationService locationService;

    @GetMapping("/addLocation")
    public ResponseEntity<?> addLocation(){
        locationService.addLocation("locations", -119.5419754, 37.7459353, "New York");
        locationService.addLocation("locations", -119.5494403, 37.747687, "Los Angeles");
        locationService.addLocation("locations", -119.5439144, 37.7274497, "London");
        locationService.addLocation("locations", -119.63778, 37.73391, "London2");
        locationService.addLocation("locations", -119.6466014, 37.7167227, "London3");

        return ResponseEntity.ok("");
    }

    @PostMapping("/getLocation")
    public ResponseEntity<List<GeoRadiusResponse>> getLocation(@RequestBody GetLocationRequest request){
        return ResponseEntity.ok(locationService.getPointsWithinRadius(request));
    }
}
