package et.com.gebeya.authservice.service;


import et.com.gebeya.authservice.dto.request_dto.*;
import et.com.gebeya.authservice.dto.response_dto.AddUserResponse;
import et.com.gebeya.authservice.dto.response_dto.OtpVerificationResponseDto;
import et.com.gebeya.authservice.dto.response_dto.ValidationResponse;
import et.com.gebeya.authservice.enums.Authority;
import et.com.gebeya.authservice.enums.Code;
import et.com.gebeya.authservice.model.Users;
import et.com.gebeya.authservice.repository.UsersRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

import static et.com.gebeya.authservice.util.Constant.NOTIFICATION_TOPIC;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UsersRepository userRepository;
    private final JwtService jwtService;
    private final KafkaTemplate<String, OtpRequest> kafkaTemplate;
    private final AuthenticationManager authenticationManager;
    private final UsersService usersService;
    private final RedisService redisService;

    private final PasswordEncoder passwordEncoder;

    public ResponseEntity<Object> signIn(@RequestBody UsersCredential usersCredential) {


        String generatedOtp = String.valueOf(otpGenerator());
        redisService.setObject(generatedOtp, usersCredential);
        OtpRequest otp = OtpRequest.builder().otp(generatedOtp).phoneNo(usersCredential.getPhoneNo()).build();
        kafkaTemplate.send(NOTIFICATION_TOPIC, otp);
        return ResponseEntity.ok("");
    }

    public int otpGenerator() {
        int size = 5;
        int min = (int) Math.pow(10, size - (double) 1);
        int max = (int) Math.pow(10, size) - 1;
        return ThreadLocalRandom.current().nextInt(min, max + 1);
    }

    public ResponseEntity<OtpVerificationResponseDto> otpVerification(@RequestBody VerificationRequest request) {
        UsersCredential redisResponse = redisService.getObject(request.getOtp());
        if (redisResponse != null && redisResponse.getPhoneNo().equals(request.getPhoneNo())) {
            Optional<Users> users = userRepository.findFirstByUserName(request.getPhoneNo());
            if (users.isEmpty()) {
                redisService.deleteValue(request.getOtp());
                OtpVerificationResponseDto response = OtpVerificationResponseDto.builder()
                        .token("").code(Code.U100).build();
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {

                if (users.get().getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals(Authority.USER.name()))) {
                    redisService.deleteValue(request.getOtp());
                    String jwt = jwtService.generateToken(users.get());
                    OtpVerificationResponseDto response = OtpVerificationResponseDto.builder()
                            .token(jwt).code(Code.U101).build();
                    return new ResponseEntity<>(response, HttpStatus.OK);

                } else if (users.get().getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals(Authority.PROVIDER.name()))) {
                    redisService.deleteValue(request.getOtp());
                    String jwt = jwtService.generateToken(users.get());
                    OtpVerificationResponseDto response = OtpVerificationResponseDto.builder()
                            .token(jwt).code(Code.U102).build();
                    return new ResponseEntity<>(response, HttpStatus.OK);
                }
                else if(users.get().getAuthorities().stream()
                        .anyMatch(authority -> authority.getAuthority().equals(Authority.ADMIN.name()))){
                    redisService.deleteValue(request.getOtp());
                    String jwt = jwtService.generateToken(users.get());
                    OtpVerificationResponseDto response = OtpVerificationResponseDto.builder()
                            .token(jwt).code(Code.U103).build();
                    return new ResponseEntity<>(response,HttpStatus.OK);
                }

            }
        }


        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    // this method is used to add user credential.
    public ResponseEntity<AddUserResponse> addUser(AddUserRequest dto)
    {
        Users users = Users.builder()
                .userName(dto.getPhoneNo())
                .password(passwordEncoder.encode(dto.getPhoneNo()))
                .isActive(true)
                .authority(dto.getRole())
                .roleId(dto.getRoleId())
                .build();
        users=userRepository.save(users);
      if(users!=null)
      {
          String jwt = jwtService.generateToken(users);
          AddUserResponse response = AddUserResponse.builder().token(jwt).build();
          return new ResponseEntity<>(response,HttpStatus.OK);
      }


      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    //this function is used to validate the token
    public ResponseEntity<ValidationResponse> validate(ValidationRequest validationRequest) {
        final String userName;
        userName = jwtService.extractUserName(validationRequest.getToken());
        if (StringUtils.isNotEmpty(userName)) {
            Users users = usersService.loadUserByUsername(userName);
            if (jwtService.isTokenValid(validationRequest.getToken(), users)) {
                ValidationResponse response = ValidationResponse.builder()
                        .role(users.getAuthority())
                        .roleId(users.getRoleId())
                        .build();

                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

}
