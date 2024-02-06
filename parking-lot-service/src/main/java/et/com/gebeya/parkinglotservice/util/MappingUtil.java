package et.com.gebeya.parkinglotservice.util;

import et.com.gebeya.parkinglotservice.dto.AddUserRequest;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;

public class MappingUtil {
    public static AddUserRequest mapParkingLotProviderToAddUserRequest(ParkingLotProvider provider) {
        return AddUserRequest.builder()
                .phoneNo(provider.getPhoneNo())
                .role(provider.getRole()) // Assuming ParkingLotRole has a method getAuthority() to retrieve Authority enum
                .roleId(provider.getId()) // Mapping roleId to the id of ParkingLotProvider
                .build();
    }


    public static ParkingLotProvider mapToParkingLotProvider(AddUserRequest request) {
        ParkingLotProvider provider = new ParkingLotProvider();
        // Map fields from AddUserRequest to ParkingLotProvider
        provider.setPhoneNo(request.getPhoneNo());
        // Set other fields as needed
        return provider;
    }
}
