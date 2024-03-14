package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddAdminRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.AddUserRequest;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateAdminRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UserDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.AdminResponseDto;
import et.com.gebeya.parkinglotservice.exception.AdminIdNotFound;
import et.com.gebeya.parkinglotservice.exception.AuthException;
import et.com.gebeya.parkinglotservice.exception.ClientErrorException;
import et.com.gebeya.parkinglotservice.model.Admin;
import et.com.gebeya.parkinglotservice.repository.AdminRepository;
import et.com.gebeya.parkinglotservice.repository.specification.AdminSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AdminService {
    private final AdminRepository adminRepository;
    private final AuthService authService;
    @CircuitBreaker(name = "default", fallbackMethod = "adminFallBack")
    @Retry(name = "default")
    public AddUserResponse registerAdmin(AddAdminRequestDto dto){
        Admin admin = MappingUtil.mapAddAdminRequestDtoToAdmin(dto);
        admin = adminRepository.save(admin);
        AddUserRequest addUserRequest = MappingUtil.mapAdminToAddUserRequest(admin);
        Mono<ResponseEntity<AddUserResponse>> responseMono = authService.getAuthServiceResponseEntityMono(addUserRequest);
        return responseMono.blockOptional()
                .map(ResponseEntity::getBody)
                .orElseThrow(() -> new AuthException("Error occurred during generating of token"));
    }

    private AddUserResponse adminFallBack(Throwable throwable) {
        log.error("fallback error=>{}, message=>{}", throwable.getClass(), throwable.getMessage());
        if (throwable instanceof WebClientResponseException.BadRequest) {
            throw new ClientErrorException(((WebClientResponseException.BadRequest) throwable).getResponseBodyAsString());
        }
        throw new RuntimeException(throwable.getMessage());
    }

    public List<AdminResponseDto> getAllAdmins(){
        List<Admin> adminList = adminRepository.findAll(AdminSpecification.getAllAdmins());
        return MappingUtil.listOfAdminToListOfAdminResponseDto(adminList);
    }

    public AdminResponseDto getAdminById(Integer id){
        Admin admin = getAdmin(id);
        return MappingUtil.mapAdminToAdminResponseDto(admin);
    }

    private Admin getAdmin(Integer id){
        List<Admin> adminOptional = adminRepository.findAll(AdminSpecification.getAdminById(id));
        if(adminOptional.isEmpty())
            throw new AdminIdNotFound("Admin not found");
        return adminOptional.get(0);
    }
    public AdminResponseDto getMyAdminProfile(){
        UserDto adminId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return getAdminById(adminId.getId());
    }
    public AdminResponseDto updateAdmin(UpdateAdminRequestDto dto, Integer id){
        Admin admin = getAdmin(id);
        admin = MappingUtil.mapUpdateAdminRequestDtoToAdmin(dto,admin);
        admin = adminRepository.save(admin);
        return MappingUtil.mapAdminToAdminResponseDto(admin);
    }
}
