package com.SPRINGBOOT.project.QuickRide.QuickRideApp.controllers;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.*;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/signup")
    ResponseEntity<UserDto> signUp(@RequestBody SignupDto signupDto) {
        return new ResponseEntity<>(authService.signup(signupDto), HttpStatus.CREATED);
    }

    @Secured("ROLE_ADMIN")
    @PostMapping("/onBoardNewDriver/{userId}")
    ResponseEntity<DriverDto> onBoardNewDriver(@PathVariable Long userId, @RequestBody OnboardDriverDto onboardDriverDto) {
        return new ResponseEntity<>(authService.onboardNewDriver(userId,
                onboardDriverDto.getVehicleId()), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto loginRequestDto,
                                           HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        // getting the access, ref tokens
        String tokens[] = authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());

        // storing the refresh token in the cookie
        Cookie cookie = new Cookie("token", tokens[1]);
        cookie.setHttpOnly(true);  // java script off this cookie is not seen by java script

        // response also have the cookie
        httpServletResponse.addCookie(cookie);

        // returning the access token
        return ResponseEntity.ok(new LoginResponseDto(tokens[0]));
    }

    // refresh API for refreshing the tokens
    // return the access token by matching the refresh token
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refresh(HttpServletRequest request) {
        String refreshToken = Arrays.stream(request.getCookies()).
                filter(cookie -> "refreshToken".equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElseThrow(() -> new AuthenticationServiceException("Refresh token not found inside the Cookies"));

        String accessToken = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(new LoginResponseDto(accessToken));
    }

}



