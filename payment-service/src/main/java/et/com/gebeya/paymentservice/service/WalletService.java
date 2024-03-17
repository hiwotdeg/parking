package et.com.gebeya.paymentservice.service.payment;

import et.com.gebeya.paymentservice.dto.request.*;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.dto.response.ResponseModel;
import et.com.gebeya.paymentservice.util.IdConvertorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static et.com.gebeya.paymentservice.util.Constant.TRANSFER_MESSAGE;

@Service
@RequiredArgsConstructor
public class WalletService {
    private final BalanceNewService balanceNewService;
    private final KafkaTemplate<String, CreditOrDebitMessageDto> creditOrDebitMessageDtoKafkaTemplate;
    private final KafkaTemplate<String, TransferMessageDto> transferMessageDtoKafkaTemplate;
    public BalanceResponseDto getWalletBalance() {
        UserDto user = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String userId = user.getRole() + "_" + user.getId();
        return balanceNewService.checkBalance(userId);
    }

    public BalanceResponseDto createWalletForDriver(BalanceRequestDto dto){
        String driverId = IdConvertorUtil.driverConvertor(dto.getUserId());
        BalanceDto balanceDto = BalanceDto.builder().userId(driverId).build();
        return balanceNewService.createBalance(balanceDto);
    }

    public BalanceResponseDto createWalletForProvider(BalanceRequestDto dto){
        String providerId = IdConvertorUtil.providerConvertor(dto.getUserId());
        BalanceDto balanceDto = BalanceDto.builder().userId(providerId).build();
        return balanceNewService.createBalance(balanceDto);
    }

    public ResponseModel deleteWalletForDriver(Integer id){
        String driverId = IdConvertorUtil.driverConvertor(id);
        return balanceNewService.deleteUser(driverId);
    }

    public ResponseModel deleteWalletForProvider(Integer id){
        String providerId = IdConvertorUtil.providerConvertor(id);
        return balanceNewService.deleteUser(providerId);
    }

    public BalanceResponseDto checkBalanceForDriver(Integer dId){
        String driverId = IdConvertorUtil.driverConvertor(dId);
        return balanceNewService.checkBalance(driverId);
    }

    public BalanceResponseDto transferBalance(TransferBalanceRequestDto dto){
        String driverId = IdConvertorUtil.driverConvertor(dto.getDriverId());
        String providerId = IdConvertorUtil.providerConvertor(dto.getProviderId());
        TransferBalanceDto transferBalanceDto = TransferBalanceDto.builder().driverId(driverId).providerId(providerId).amount(dto.getAmount()).build();
        BalanceResponseDto balanceResponseDto = balanceNewService.transferBalance(transferBalanceDto);
        TransferMessageDto transferMessageDto = TransferMessageDto.builder().driverId(dto.getDriverId()).providerId(dto.getProviderId()).amount(dto.getAmount()).build();
        transferMessageDtoKafkaTemplate.send(TRANSFER_MESSAGE,transferMessageDto);
        return balanceResponseDto;
    }

}
