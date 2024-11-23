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

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int id;

    @NotBlank(message = "Username cannot be empty")
    private String userName;

    @NotNull
    @NotBlank(message = "Password cannot be Empty")
    private String password;


    @NotBlank
    @Email(message = "Email Id should be valid Id")
    private String emailId;

    private String name;

    @Pattern(regexp = "\\d{10}", message = "Phone Number must br 10 digits")
    private String phoneNo;


    private Boolean activeStatus = true;

    @Enumerated(EnumType.STRING)
    private UserRole userRole;

}
