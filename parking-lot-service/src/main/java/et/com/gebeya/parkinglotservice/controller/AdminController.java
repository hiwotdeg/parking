package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateAdminRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AdminResponseDto;
import et.com.gebeya.parkinglotservice.model.Admin;
import et.com.gebeya.parkinglotservice.service.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;
    @GetMapping("/admins/")
    public ResponseEntity<List<Admin>> getAdmins(){
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/admin/{id}")
    public ResponseEntity<Admin> getAdminById(@PathVariable("id") Integer id){
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @PostMapping("/admin")
    public ResponseEntity<Admin> registerAdmin(@RequestBody Admin admin){
        return ResponseEntity.ok(adminService.registerAdmin(admin));
    }

    @PatchMapping("/admin/{id}")
    public ResponseEntity<AdminResponseDto> updateAdmin(@RequestBody UpdateAdminRequestDto dto, @PathVariable("id") Integer id){
        return ResponseEntity.ok(adminService.updateAdmin(dto,id));
    }


}
