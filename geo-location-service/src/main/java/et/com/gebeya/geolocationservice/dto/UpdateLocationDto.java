package et.com.gebeya.geolocationservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class UpdateLocationDto {
    private Integer id;
    private String existingTitle;
    private String existingAddress;
    private String newTitle;
    private String newAddress;
    private Double latitude;
    private Double longitude;
}
