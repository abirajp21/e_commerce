package com.jariba.e_commerce.Model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @Column(unique = true)
    @NotBlank(message = "Username cannot be empty")
    private String userName;

    @NotNull
    @NotBlank(message = "Password cannot be Empty")
    private String password;


    @NotBlank(message = "Email field can't be Empty")
    @Column(unique = true)
    @Email(message = "Email Id should be valid Id")
    private String emailId;

    private String name;

    @Pattern(regexp = "\\d{10}", message = "Phone Number must br 10 digits")
    private String phoneNo;


    private Boolean activeStatus = true;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getUsername() {
        return this.userName;
    }
}
