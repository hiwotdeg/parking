package et.com.gebeya.geolocationservice.service;

import et.com.gebeya.geolocationservice.dto.AddLocationDto;
import et.com.gebeya.geolocationservice.dto.DeleteLocationDto;
import et.com.gebeya.geolocationservice.dto.GetLocationRequest;
import et.com.gebeya.geolocationservice.exception.InvalidLocationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.GeoRadiusParam;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationService {
   private final Jedis jedis;

    public void addLocation(AddLocationDto addLocationDto) {
        jedis.geoadd("location", addLocationDto.getLongitude(), addLocationDto.getLatitude(), String.valueOf(addLocationDto.getId()));
    }

    public List<GeoRadiusResponse> getPointsWithinRadius(GetLocationRequest request) {
        try{
            List<GeoRadiusResponse> responses = jedis.georadius("location", request.getLongitude(), request.getLatitude(), 10, GeoUnit.KM,  GeoRadiusParam.geoRadiusParam().withDist().withCoord()  );
            return new ArrayList<>(responses);
        }
        catch (JedisDataException exception){
            throw new InvalidLocationException(exception.getMessage());
        }

    }
    public void deleteLocation(DeleteLocationDto deleteLocationDto){
        jedis.zrem("location",deleteLocationDto.getId().toString());
    }



}
