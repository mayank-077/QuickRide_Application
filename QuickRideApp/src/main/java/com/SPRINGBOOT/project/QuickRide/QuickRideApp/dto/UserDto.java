package com.SPRINGBOOT.project.QuickRide.QuickRideApp.dto;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private String name;
    private String email;
    private Set<Role> roles;
}

