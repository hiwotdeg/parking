package et.com.gebeya.parkinglotservice.controller;

import et.com.gebeya.parkinglotservice.dto.requestdto.AddMessage;
import et.com.gebeya.parkinglotservice.service.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/parking-lot")
public class MessageController {
    private final MessageService messageService;
    @PostMapping("/sendMessage")
    public ResponseEntity<String> sendMessage(){
        return ResponseEntity.ok(messageService.test());
    }
}
