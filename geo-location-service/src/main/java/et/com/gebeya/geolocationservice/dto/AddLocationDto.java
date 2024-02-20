package et.com.gebeya.geolocationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AddLocationDto {
    private Integer id;
    private Double latitude;
    private Double longitude;
}
