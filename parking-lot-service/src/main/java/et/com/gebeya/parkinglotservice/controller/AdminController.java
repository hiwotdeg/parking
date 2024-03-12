package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddAdminRequestDto;
import et.com.gebeya.parkinglotservice.dto.requestdto.UpdateAdminRequestDto;
import et.com.gebeya.parkinglotservice.dto.responsedto.AddUserResponse;
import et.com.gebeya.parkinglotservice.dto.responsedto.AdminResponseDto;
import et.com.gebeya.parkinglotservice.service.AdminService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/parking-lot")
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping("/admins")
    public ResponseEntity<List<AdminResponseDto>> getAdmins() {
        return ResponseEntity.ok(adminService.getAllAdmins());
    }

    @GetMapping("/admins/{id}")
    public ResponseEntity<AdminResponseDto> getAdminById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(adminService.getAdminById(id));
    }

    @GetMapping("/admins/my")
    public ResponseEntity<AdminResponseDto> getMyAdminProfile() {
        return ResponseEntity.ok(adminService.getMyAdminProfile());
    }

    @PostMapping("/admins")
    public ResponseEntity<AddUserResponse> registerAdmin(@Valid @RequestBody AddAdminRequestDto admin) {
        return ResponseEntity.ok(adminService.registerAdmin(admin));
    }

    @PatchMapping("/admins/{id}")
    public ResponseEntity<AdminResponseDto> updateAdmin(@RequestBody UpdateAdminRequestDto dto, @PathVariable("id") Integer id) {
        return ResponseEntity.ok(adminService.updateAdmin(dto, id));
    }


}
