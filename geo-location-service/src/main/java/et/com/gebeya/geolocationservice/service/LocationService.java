package et.com.gebeya.geolocationservice.service;

import et.com.gebeya.geolocationservice.dto.GetLocationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.params.GeoRadiusParam;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
   private final Jedis jedis;

    public void addLocation(String key, double longitude,double latitude, String member) {
        jedis.geoadd(key, longitude, latitude, member);
    }

    public List<GeoRadiusResponse> getPointsWithinRadius(GetLocationRequest request) {
        List<GeoRadiusResponse> responses = jedis.georadius("locations", request.getLongitude(), request.getLatitude(), 5, GeoUnit.KM,  GeoRadiusParam.geoRadiusParam().withDist().withCoord()  );
        return new ArrayList<>(responses);
    }
}
