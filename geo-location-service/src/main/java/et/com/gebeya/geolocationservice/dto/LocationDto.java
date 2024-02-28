package et.com.gebeya.geolocationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import redis.clients.jedis.GeoCoordinate;
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LocationDto {
    private Integer id;
    private String title;
    private String description;
    private Double distance;
    private GeoCoordinate coordinate;


}
