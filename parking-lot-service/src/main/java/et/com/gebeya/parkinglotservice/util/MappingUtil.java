package et.com.gebeya.parkinglotservice.util;

import et.com.gebeya.parkinglotservice.dto.requestdto.*;
import et.com.gebeya.parkinglotservice.dto.responsedto.DriverResponseDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ParkingLotResponseDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.ProviderResponseDto;
import et.com.gebeya.parkinglotservice.enums.Authority;
import et.com.gebeya.parkinglotservice.enums.ParkingLotRole;
import et.com.gebeya.parkinglotservice.model.Driver;
import et.com.gebeya.parkinglotservice.model.ParkingLot;
import et.com.gebeya.parkinglotservice.model.ParkingLotProvider;
import et.com.gebeya.parkinglotservice.model.Review;

public class MappingUtil {
    private MappingUtil(){}
    public static AddUserRequest mapParkingLotProviderToAddUserRequest(ParkingLotProvider provider) {
        return AddUserRequest.builder()
                .phoneNo(provider.getPhoneNo())
                .role(Authority.PROVIDER)
                .roleId(provider.getId())
                .build();
    }

    public static Driver mapAddDiverRequestDtoToDriver(AddDriverRequestDto dto){
        return Driver.builder().firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .phoneNo(dto.getPhoneNo())
                .email(dto.getEmail())
                .isActive(true).imageUrl("")
                .build();
    }

    public static Driver mapUpdateDiverRequestDtoToDriver(UpdateDriverRequestDto dto, Driver driver){
       if(dto.getFirstName()!=null)
           driver.setFirstName(dto.getFirstName());
       if(dto.getLastName()!=null)
           driver.setLastName(dto.getLastName());
       if(dto.getEmail()!=null)
           driver.setEmail(dto.getEmail());
       if(dto.getPhoneNo()!=null)
           driver.setEmail(dto.getEmail());
       if(dto.getImageUrl()!=null)
           driver.setImageUrl(dto.getImageUrl());
       return driver;
    }

public static DriverResponseDto mapDriverToDriverResponseDto(Driver driver){
        return DriverResponseDto.builder()
                .id(driver.getId())
                .firstName(driver.getFirstName())
                .lastName(driver.getLastName())
                .email(driver.getEmail())
                .phoneNo(driver.getPhoneNo())
                .imageUrl(driver.getImageUrl())
                .build();
}

    public static AddUserRequest mapCustomerToAddUserRequest(Driver driver){
        return AddUserRequest.builder()
                .phoneNo(driver.getPhoneNo())
                .role(Authority.USER)
                .roleId((driver.getId()))
                .build();
    }

    public static ParkingLotProvider mapToParkingLotProvider(AddUserRequest request) {
        ParkingLotProvider provider = new ParkingLotProvider();
        provider.setPhoneNo(request.getPhoneNo());
        return provider;
    }

    public static ParkingLot mapAddParkingLotToParkingLot(AddParkingLotDto parkingLotRequest)
    {
        return ParkingLot.builder().name(parkingLotRequest.getName())
                .address(parkingLotRequest.getAddress())
                .rating(5.0f)
                .availableSlot(parkingLotRequest.getCapacity())
                .isActive(true)
                .latitude(parkingLotRequest.getLatitude())
                .longitude(parkingLotRequest.getLongitude())
                .capacity(parkingLotRequest.getCapacity())
                .imageUrl(parkingLotRequest.getImageUrl())
                .parkingType(parkingLotRequest.getParkingType()).build();
    }

    public static ParkingLot updateParkingLot(ParkingLot parkingLot, UpdateParkingLotDto dto){
        if(dto.getImageUrl()!=null)
            parkingLot.setImageUrl(dto.getImageUrl());
        if(dto.getAvailableSlot()!=null)
            parkingLot.setAvailableSlot(dto.getAvailableSlot());
        if(dto.getName()!=null)
            parkingLot.setName(dto.getName());
        if(dto.getAddress()!=null)
            parkingLot.setAddress(dto.getAddress());
        if(dto.getLatitude()!=null)
            parkingLot.setLatitude(dto.getLatitude());
        if(dto.getLongitude()!=null)
            parkingLot.setLongitude(dto.getLongitude());
        if(dto.getCapacity()!=null)
            parkingLot.setCapacity(dto.getCapacity());
        if(dto.getParkingType()!=null)
            parkingLot.setParkingType(dto.getParkingType());
        return parkingLot;
    }

    public static ParkingLotResponseDto parkingLotResponse(ParkingLot parkingLot){
        return ParkingLotResponseDto.builder().id(parkingLot.getId())
                .name(parkingLot.getName())
                .address(parkingLot.getAddress())
                .latitude(parkingLot.getLatitude())
                .longitude(parkingLot.getLongitude())
                .capacity(parkingLot.getCapacity())
                .imageUrl(parkingLot.getImageUrl())
                .availableSlot(parkingLot.getAvailableSlot())
                .parkingType(parkingLot.getParkingType())
                .rating(parkingLot.getRating())
                .build();

    }

    public static ParkingLotProvider mapAddProviderDtoToParkingLotProvider(AddProviderDto dto){
        return ParkingLotProvider.builder().firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .email(dto.getEmail())
                .phoneNo(dto.getPhoneNo())
                .imageUrl("")
                .isActive(true)
                .isVerified(true)
                .role(ParkingLotRole.ADMIN)
                .build();
    }

    public static Review mapAddReviewRequestDtoToReview(AddReviewRequestDto dto){
        return Review.builder()
                .rate(dto.getRate())
                .comment(dto.getComment())
                .build();
    }

    public static ProviderResponseDto mapParkingLotProviderToProviderResponseDto(ParkingLotProvider provider){
        return ProviderResponseDto.builder()
                .id(provider.getId())
                .firstName(provider.getFirstName())
                .lastName(provider.getLastName())
                .email(provider.getEmail())
                .phoneNo(provider.getPhoneNo())
                .imageUrl(provider.getImageUrl())
                .role(provider.getRole()).build();
    }

    public static ParkingLotProvider updateParkingLotProvider(UpdateProviderRequestDto dto,ParkingLotProvider provider){
        if(dto.getFirstName()!=null)
            provider.setFirstName(dto.getFirstName());
        if(dto.getLastName()!=null)
            provider.setLastName(dto.getLastName());
        if(dto.getEmail()!=null)
            provider.setEmail(dto.getEmail());
        if(dto.getPhoneNo()!=null)
            provider.setPhoneNo(dto.getPhoneNo());
        if(dto.getImageUrl()!=null)
            provider.setImageUrl(dto.getImageUrl());
        return provider;
    }
}
