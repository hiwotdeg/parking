package et.com.gebeya.geolocationservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import et.com.gebeya.geolocationservice.dto.LocationDto;
import et.com.gebeya.geolocationservice.dto.ParkingLotDto;
import et.com.gebeya.geolocationservice.exception.JsonParsingException;
import redis.clients.jedis.resps.GeoRadiusResponse;

import java.util.ArrayList;
import java.util.List;

public class MappingUtil {
    private MappingUtil() {
    }

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static List<LocationDto> mapListOfGeoRadiusResponseToListOfLocationDto(List<GeoRadiusResponse> geoRadiusResponseList) {
        List<LocationDto> locationDtoList = new ArrayList<>();
        geoRadiusResponseList.forEach(geoRadiusResponse -> {
            try {
                locationDtoList.add(mapGeoRadiusResponseToLocationDto(geoRadiusResponse));
            } catch (JsonProcessingException e) {
                throw new JsonParsingException(e.getMessage());
            }
        });
        return locationDtoList;
    }

    public static LocationDto mapGeoRadiusResponseToLocationDto(GeoRadiusResponse geoRadiusResponse) throws JsonProcessingException {
        ParkingLotDto parkingLotDto = objectMapper.readValue(geoRadiusResponse.getMemberByString(), ParkingLotDto.class);
        return LocationDto.builder()
                .id(parkingLotDto.getId())
                .title(parkingLotDto.getTitle())
                .description(parkingLotDto.getAddress())
                .coordinate(geoRadiusResponse.getCoordinate())
                .distance(geoRadiusResponse.getDistance())
                .build();
    }

}
