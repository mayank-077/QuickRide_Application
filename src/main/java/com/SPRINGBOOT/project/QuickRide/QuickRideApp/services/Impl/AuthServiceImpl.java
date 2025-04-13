package com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.Impl;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.DriverDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.SignupDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto.UserDto;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.Driver;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.User;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.Role;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.exceptions.ResourceNotFoundException;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.exceptions.RuntimeConflictException;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.repositories.UserRepository;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.security.JWTService;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.AuthService;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.DriverService;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.RiderService;
import com.SPRINGBOOT.project.QuickRide.QuickRideApp.services.WalletService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import java.util.Set;

import static com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.Role.DRIVER;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final RiderService riderService;
    private final WalletService walletService;
    private final DriverService driverService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

    @Override
    public String[] login(String email, String password) {
        //here we are passing the email and pass for authentication
        // in this authentication is authenticated is also it will contain true and false
        Authentication authentication = authenticationManager.authenticate( // this object contains the authorities with credentials
                new UsernamePasswordAuthenticationToken(email, password)
        );

        // getting the current user
        User user = (User) authentication.getPrincipal();

        // create the tokens
        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        // returning the string array
        return new String[]{accessToken, refreshToken};
    }

    @Override
    // @Transactional // so this will make sure the rider is created if not it will roll back to the start
    //ATOMICITY of database
    public UserDto signup(SignupDto signupDto) {
        User user = userRepository.findByEmail(signupDto.getEmail()).orElse(null);
        if(user != null)
            throw new RuntimeConflictException("Cannot signup, User already exists with email "+signupDto.getEmail());

        User mappedUser = modelMapper.map(signupDto, User.class);
        mappedUser.setRoles(Set.of(Role.RIDER));
        //encode the password and then save the password
        mappedUser.setPassword(passwordEncoder.encode(mappedUser.getPassword()));
        User savedUser = userRepository.save(mappedUser);

        //create user related entities
        riderService.createNewRider(savedUser); // inside the riderservice we have the newrider
        //add wallet related service here
        walletService.createNewWallet(savedUser);

        return modelMapper.map(savedUser, UserDto.class);
    }

    @Override
    public DriverDto onboardNewDriver(Long userId, String vehicleId) {
        //only board it when it user if it is not a user then no
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id "+userId));

        if(user.getRoles().contains(DRIVER))
            throw new RuntimeConflictException("User with id "+userId+" is already a Driver");

        //creating the driver
        Driver createDriver = Driver.builder()
                .user(user)
                .rating(0.0)
                .vehicleId(vehicleId)
                .available(true)
                .build();
        user.getRoles().add(DRIVER);
        userRepository.save(user);
        Driver savedDriver = driverService.createNewDriver(createDriver);
        return modelMapper.map(savedDriver, DriverDto.class);
    }

    @Override
    public String refreshToken(String refreshToken) {
        Long userId = jwtService.getUserIdFromToken(refreshToken);
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found " +
                "with id: "+userId));

        return jwtService.generateAccessToken(user);
    }
}

