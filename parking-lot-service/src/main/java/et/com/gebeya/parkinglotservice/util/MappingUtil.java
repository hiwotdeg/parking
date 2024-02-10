package et.com.gebeya.parkinglotservice.util;

import et.com.gebeya.parkinglotservice.dto.AddParkingLotRequest;
import et.com.gebeya.parkinglotservice.dto.AddUserRequest;
import et.com.gebeya.parkinglotservice.enums.Authority;
import et.com.gebeya.parkinglotservice.model.Customer;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;

public class MappingUtil {
    private MappingUtil(){}
    public static AddUserRequest mapParkingLotProviderToAddUserRequest(ParkingLotProvider provider) {
        return AddUserRequest.builder()
                .phoneNo(provider.getPhoneNo())
                .role(Authority.PROVIDER)
                .roleId(provider.getId())
                .build();
    }

    public static AddUserRequest mapCustomerToAddUserRequest(Customer customer){
        return AddUserRequest.builder()
                .phoneNo(customer.getPhoneNo())
                .role(Authority.USER)
                .roleId((customer.getId()))
                .build();
    }

    public static ParkingLotProvider mapToParkingLotProvider(AddUserRequest request) {
        ParkingLotProvider provider = new ParkingLotProvider();
        provider.setPhoneNo(request.getPhoneNo());
        return provider;
    }

    public static ParkingLot mapAddParkingLotToParkingLot(AddParkingLotRequest parkingLotRequest)
    {
        return ParkingLot.builder().name(parkingLotRequest.getName())
                .address(parkingLotRequest.getAddress())
                .latitude(parkingLotRequest.getLatitude())
                .longitude(parkingLotRequest.getLongitude())
                .capacity(parkingLotRequest.getCapacity())
                .parkingType(parkingLotRequest.getParkingType()).build();
    }
}
