package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateAdminRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AdminResponseDto;
import et.com.gebeya.parkinglotservice.exception.AdminIdNotFound;
import et.com.gebeya.parkinglotservice.model.Admin;
import et.com.gebeya.parkinglotservice.repository.AdminRepository;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public Admin registerAdmin(Admin admin){
        return adminRepository.save(admin);
    }

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
    }

    public Admin getAdminById(Integer id){
        Optional<Admin> adminOptional = adminRepository.findById(id);
        if(adminOptional.isPresent()) return adminOptional.get();
        throw new AdminIdNotFound("Admin not found");
    }

    public AdminResponseDto updateAdmin(UpdateAdminRequestDto dto, Integer id){
        Admin admin = getAdminById(id);
        admin = MappingUtil.mapUpdateAdminRequestDtoToAdmin(dto,admin);
        return MappingUtil.mapAdminToAdminResponseDto(admin);
    }
}
