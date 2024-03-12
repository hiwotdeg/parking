package et.com.gebeya.parkinglotservice.service;

import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateAdminRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AdminResponseDto;
import et.com.gebeya.parkinglotservice.exception.AdminIdNotFound;
import et.com.gebeya.parkinglotservice.model.Admin;
import et.com.gebeya.parkinglotservice.repository.AdminRepository;
import et.com.gebeya.parkinglotservice.repository.specification.AdminSpecification;
import et.com.gebeya.parkinglotservice.util.MappingUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final AdminRepository adminRepository;

    public AdminResponseDto registerAdmin(Admin admin){
        adminRepository.save(admin);
        return MappingUtil.mapAdminToAdminResponseDto(admin);
    }

    public List<Admin> getAllAdmins(){
        return adminRepository.findAll();
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
        Integer adminId = (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal(); // fix this line later
        return getAdminById(adminId);
    }
    public AdminResponseDto updateAdmin(UpdateAdminRequestDto dto, Integer id){
        Admin admin = getAdmin(id);
        admin = MappingUtil.mapUpdateAdminRequestDtoToAdmin(dto,admin);
        return MappingUtil.mapAdminToAdminResponseDto(admin);
    }
}
