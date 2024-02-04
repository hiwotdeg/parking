//package et.com.gebeya.authservice.service;
//
//import et.com.gebeya.authservice.dto.request_dto.OtpRequest;
//import et.com.gebeya.authservice.dto.request_dto.UsersCredential;
//import et.com.gebeya.authservice.enums.Authority;
//import et.com.gebeya.authservice.util.Constant;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.reactivestreams.Publisher;
//import org.springframework.http.ResponseEntity;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.kafka.support.SendResult;
//import org.springframework.util.concurrent.ListenableFuture;
//
//import javax.annotation.meta.When;
//
//
//import java.lang.reflect.Method;
//import java.util.concurrent.CompletableFuture;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//import static reactor.core.publisher.Mono.when;
//
//@ExtendWith(MockitoExtension.class)
//class AuthenticationServiceTest {
//
//    @Mock
//    private RedisService redisService;
//
//    @Mock
//    private KafkaTemplate<String, OtpRequest> kafkaTemplate;
//
//
//
//   @InjectMocks
//   private AuthenticationService authenticationService;
//
//    @Test
//    void testSignIn() {
//
//        UsersCredential usersCredential = UsersCredential.builder().phoneNo("0978904679").role(Authority.USER).build();
//        AuthenticationService spyYourClass = spy(authenticationService);
////        doReturn(12345).when(spyYourClass).otpGenerator();
//        doNothing().when(redisService).setObject(anyString(), any(UsersCredential.class));
//
//        ListenableFuture<SendResult<String, OtpRequest>> listenableFuture = Mockito.mock(ListenableFuture.class);
//        when((Publisher<?>) kafkaTemplate.send(anyString(), any(OtpRequest.class)))
//                .thenReturn(listenableFuture);
//        when(kafkaTemplate.send(Constant.NOTIFICATION_TOPIC,any(OtpRequest.class))).thenReturn()
//
//        ResponseEntity<Object> response = spyYourClass.signIn(usersCredential);
//        verify(redisService).setObject(anyString(), any(UsersCredential.class));
//        verify(kafkaTemplate).send(anyString(), any(OtpRequest.class));
//        assertTrue(response.getStatusCode().is2xxSuccessful());
//
//    }
//}