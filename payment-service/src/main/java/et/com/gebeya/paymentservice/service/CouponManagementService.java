package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.*;
import et.com.gebeya.paymentservice.dto.response.BalanceResponseDto;
import et.com.gebeya.paymentservice.util.IdConvertorUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import static et.com.gebeya.paymentservice.util.Constant.CREDIT_OR_DEBIT_MESSAGE;

@Service
@RequiredArgsConstructor
public class CouponManagementService {
    private final BalanceService balanceService;
    private final KafkaTemplate<String, CreditOrDebitMessageDto> creditOrDebitMessageDtoKafkaTemplate;
    public BalanceResponseDto createBalanceForDriver(BalanceRequestDto dto){
        String driverId = IdConvertorUtil.driverConvertor(dto.getUserId());
        BalanceDto balanceDto = BalanceDto.builder().userId(driverId).build();
        return balanceService.createBalance(balanceDto);
    }

    public BalanceResponseDto createBalanceForProvider(BalanceRequestDto dto){
        String providerId = IdConvertorUtil.providerConvertor(dto.getUserId());
        BalanceDto balanceDto = BalanceDto.builder().userId(providerId).build();
        return balanceService.createBalance(balanceDto);
    }

    public BalanceResponseDto withdrawalBalanceForProvider(WithdrawalRequestDto dto){
        UserDto userId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String providerId = userId.getRole()+"_"+userId.getId();
        BalanceDto balanceDto = BalanceDto.builder().balance(dto.getAmount()).userId(providerId).build();
        BalanceResponseDto balanceResponseDto = balanceService.withdrawalBalance(balanceDto);
        CreditOrDebitMessageDto creditOrDebitMessageDto = CreditOrDebitMessageDto.builder().amount(dto.getAmount()).userId(providerId).build();
        creditOrDebitMessageDtoKafkaTemplate.send(CREDIT_OR_DEBIT_MESSAGE,creditOrDebitMessageDto);
        return balanceResponseDto;
    }

    public BalanceResponseDto depositBalanceForDriver(BalanceRequestDto dto){
        String driverId = IdConvertorUtil.driverConvertor(dto.getUserId());
        BalanceDto balanceDto = BalanceDto.builder().balance(dto.getAmount()).userId(driverId).build();
        BalanceResponseDto balanceResponseDto = balanceService.depositBalance(balanceDto);
        CreditOrDebitMessageDto creditOrDebitMessageDto = CreditOrDebitMessageDto.builder().amount(dto.getAmount()).userId(driverId).build();
        creditOrDebitMessageDtoKafkaTemplate.send(CREDIT_OR_DEBIT_MESSAGE,creditOrDebitMessageDto);
        return balanceResponseDto;
    }

    public BalanceResponseDto transferBalance(TransferBalanceRequestDto dto){
        String driverId = IdConvertorUtil.driverConvertor(dto.getDriverId());
        String providerId = IdConvertorUtil.providerConvertor(dto.getProviderId());
        TransferBalanceDto transferBalanceDto = TransferBalanceDto.builder().driverId(driverId).providerId(providerId).amount(dto.getAmount()).build();
        return balanceService.transferBalance(transferBalanceDto);
    }

    public BalanceResponseDto checkBalance(){
        UserDto userId = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String id = userId.getRole()+"_"+userId.getId();
        return balanceService.checkBalance(id);
    }

}
