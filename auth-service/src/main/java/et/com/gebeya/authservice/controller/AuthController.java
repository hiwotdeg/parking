package et.com.gebeya.authservice.controller;


import et.com.gebeya.authservice.dto.request_dto.AddUserRequest;
import et.com.gebeya.authservice.dto.request_dto.UsersCredential;
import et.com.gebeya.authservice.dto.request_dto.ValidationRequest;
import et.com.gebeya.authservice.dto.request_dto.VerificationRequest;
import et.com.gebeya.authservice.dto.response_dto.AddUserResponse;
import et.com.gebeya.authservice.dto.response_dto.OtpVerificationResponseDto;
import et.com.gebeya.authservice.dto.response_dto.ValidationResponse;
import et.com.gebeya.authservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validate(@RequestBody ValidationRequest request)
    {
        return authenticationService.validate(request);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody UsersCredential credential)
    {
        return authenticationService.signIn(credential);
    }

    @PostMapping("/otp")
    public ResponseEntity<OtpVerificationResponseDto> otpVerification(@RequestBody VerificationRequest request)
    {
        return authenticationService.otpVerification(request);
    }

    @PostMapping("/addUser")
    public ResponseEntity<AddUserResponse> addUser(@RequestBody AddUserRequest request)
    {
        return authenticationService.addUser(request);
    }
}
