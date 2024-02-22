package et.com.gebeya.paymentservice.service;

import et.com.gebeya.paymentservice.dto.request.PriceRequestDto;
import et.com.gebeya.paymentservice.model.OperationHour;
import et.com.gebeya.paymentservice.repository.OperationHourRepository;
import et.com.gebeya.paymentservice.repository.specification.OperationHourSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

//@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {

    @InjectMocks
    private PaymentService pricingService;

    @Mock
    private OperationHourRepository operationHourRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }
    //    @Test
//    void testDynamicPricing() {
//        // Arrange
//        PriceRequestDto request = new PriceRequestDto();
//        request.setParkingLotId(1);
//        request.setDuration(LocalTime.of(2,0));
//
//        OperationHour operationHour1 = new OperationHour();
//        operationHour1.setStartTime(LocalDateTime.of(2024, 1, 1, 6, 0));
//        operationHour1.setEndTime(LocalDateTime.of(2024, 1, 1, 17, 59));
//        operationHour1.setPricePerHour(new BigDecimal("10.00"));
//
//        OperationHour operationHour2 = new OperationHour();
//        operationHour2.setStartTime(LocalDateTime.of(2024, 1, 1, 18, 0));
//        operationHour2.setEndTime(LocalDateTime.of(2024, 1, 2, 5, 59));
//        operationHour2.setPricePerHour(new BigDecimal("15.00"));
//
//        List<OperationHour> operationHours = Arrays.asList(operationHour1, operationHour2);
//
//        when(operationHourRepository.findAll(OperationHourSpecification.hasParkingLotId(request.getParkingLotId()))).thenReturn(operationHours);
//
//        // Act
//        BigDecimal result = pricingService.dynamicPricing(request);
//
//        // Assert
//        assertEquals(new BigDecimal("20.00"), result);
//    }
//    @Test
//    void testDynamicPricing() {
//        OperationHour operationHour1 = new OperationHour();
//        operationHour1.setStartTime(LocalDateTime.of(2024, 1, 1, 6, 0));
//        operationHour1.setEndTime(LocalDateTime.of(2024, 1, 1, 17, 59));
//        operationHour1.setPricePerHour(new BigDecimal("10.00"));
//
//        OperationHour operationHour2 = new OperationHour();
//        operationHour2.setStartTime(LocalDateTime.of(2024, 1, 1, 18, 0));
//        operationHour2.setEndTime(LocalDateTime.of(2024, 1, 2, 5, 59));
//        operationHour2.setPricePerHour(new BigDecimal("15.00"));
//
//        List<OperationHour> operationHours = Arrays.asList(operationHour1, operationHour2);
//        when(operationHourRepository.findAll(OperationHourSpecification.hasParkingLotId(1))).thenReturn(operationHours);
//
//
//        List<OperationHour> actualOperationHours = pricingService.getOperationHoursById(1);
//        assertEquals(operationHours, actualOperationHours);
//    }

}



