package et.com.gebeya.geolocationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.gebeya.geolocationservice.dto.AddLocationDto;
import et.com.gebeya.geolocationservice.dto.DeleteLocationDto;
import et.com.gebeya.geolocationservice.dto.GetLocationRequest;
import et.com.gebeya.geolocationservice.dto.LocationDto;
import et.com.gebeya.geolocationservice.exception.InvalidLocationException;
import et.com.gebeya.geolocationservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.args.GeoUnit;
import redis.clients.jedis.exceptions.JedisDataException;
import redis.clients.jedis.exceptions.JedisException;
import redis.clients.jedis.params.GeoRadiusParam;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor

public class LocationService {
   private final Jedis jedis;
   private final ObjectMapper objectMapper;
    @SneakyThrows
    public void addLocation(AddLocationDto addLocationDto) {
        Map<String,Object> member = new HashMap<>();
        member.put("id",addLocationDto.getId());
        member.put("title",addLocationDto.getTitle());
        member.put("address",addLocationDto.getAddress());
        String memberString = objectMapper.writeValueAsString(member);
        jedis.geoadd("location", addLocationDto.getLongitude(), addLocationDto.getLatitude(), memberString);
    }

    public List<LocationDto> getPointsWithinRadius(GetLocationRequest request) {
        try{
            List<GeoRadiusResponse> responses = jedis.georadius("location", request.getLongitude(), request.getLatitude(), 10, GeoUnit.KM,  GeoRadiusParam.geoRadiusParam().withDist().withCoord()  );
            return MappingUtil.mapListOfGeoRadiusResponseToListOfLocationDto(responses);
        }
        catch (JedisDataException exception){
            throw new InvalidLocationException(exception.getMessage());
        }

    }
    @SneakyThrows
    public void deleteLocation(DeleteLocationDto deleteLocationDto){
        Map<String,Object> member = new HashMap<>();
        member.put("id",deleteLocationDto.getId());
        member.put("title",deleteLocationDto.getTitle());
        member.put("address",deleteLocationDto.getAddress());
        String memberString = objectMapper.writeValueAsString(member);
        jedis.zrem("location",memberString);
    }



}
