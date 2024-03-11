package et.com.gebeya.parkinglotservice.aspects;

import et.com.gebeya.parkinglotservice.dto.requestdto.UserDto;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * The AuthAspect class is an aspect component that fixes a bug related to security matchers in the authentication process.
 * It ensures that the following endpoints have the correct role-based access:
 * <br>
 *
 * <p>GET /api/v1/parking-lot/providers/my: Only users with the role 'ROLE_PROVIDER' are allowed access.</p>
 * <p>GET /api/v1/parking-lot/lots/my: Only users with the role 'ROLE_PROVIDER' are allowed access.</p>
 * <p>GET /api/v1/parking-lot/drivers/my: Only users with the role 'ROLE_DRIVER' are allowed access.</p>
 * <p>GET /api/v1/parking-lot/vehicles/my: Only users with the role 'ROLE_DRIVER' are allowed access.</p>
 * <p>GET /api/v1/parking-lot/reservations/my: Only users with the role 'ROLE_PROVIDER' are allowed access.</p>
 *
 * <p>
 * The bug caused incorrect access to be granted to users with the opposite role. This aspect addresses the issue
 * by intercepting the execution of the corresponding controller methods and performing role-based authentication checks.
 * </p>
 * <p>If the authenticated user does not have the required role, a BadCredentialsException is thrown, preventing access to the endpoint.</p>
 */
@Aspect
@Component
@Slf4j
public class AuthAspect {

    @Before("execution(* et.com.gebeya.parkinglotservice.controller.ParkingLotProviderController.getMyParkingLotProvider()) || " +
            "execution(* et.com.gebeya.parkinglotservice.controller.ParkingLotController.getMyParkingLot()) ||" +
            "execution(* et.com.gebeya.parkinglotservice.controller.ReservationController.getReservationByProviderID())"
    )
    public void checkForProviderAuth() {
        UserDto provider = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!provider.getRole().equals("ROLE_PROVIDER"))
            throw new BadCredentialsException("");
    }

    @Before("execution(* et.com.gebeya.parkinglotservice.controller.DriverController.getMyDriver()) ||"+
            "execution(* et.com.gebeya.parkinglotservice.controller.VehicleController.getMyVehicle())"
    )
    public void checkForDriverAuth() {
        UserDto driver = (UserDto) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!driver.getRole().equals("ROLE_DRIVER"))
            throw new BadCredentialsException("");
    }
}
