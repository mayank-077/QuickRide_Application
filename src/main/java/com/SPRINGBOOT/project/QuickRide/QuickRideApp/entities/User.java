package com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities;

import com.SPRINGBOOT.project.QuickRide.QuickRideApp.entities.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity //Make the Table
@Table(name = "app_user", indexes = {
        @Index(name = "idx_user_email", columnList = "email")
})
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    @Id// Primary Key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // generate the
    private long id;


    private String name;

    @Column(unique=true) // NO duplicates
    private String email;

    private String password;

    @ElementCollection(fetch = FetchType.EAGER) //Create another table for this
    @Enumerated(EnumType.STRING) //storing format is string
    private Set<Role> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_"+role.name()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getUsername() {
        return email;
    }
}
