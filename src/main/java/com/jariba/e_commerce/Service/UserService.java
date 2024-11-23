package com.jariba.e_commerce.Service;


import com.jariba.e_commerce.Model.User;
import com.jariba.e_commerce.Repo.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service

public class UserService {
    @Autowired
    UserRepo userRepo;


    public String addUser(User user){



        if(userRepo.existsByEmailId(user.getEmailId()))
            throw new IllegalArgumentException("Email Id Already Exists");

        if(userRepo.existsByUserName(user.getUserName()))
            throw new IllegalArgumentException("Username Already Exists");

        user.setPassword(passwordEncoder(user.getPassword()));
        userRepo.save(user);
        return "User Added Successfully";
    }

    public Boolean deleteUser(String userName){
        //User user = userRepo.findByUserName(userName);
        //user.setActiveStatus(false);
        userRepo.setNotActiveStatusbyUserName(userName);
        return true;
    }

    private String passwordEncoder(String password){

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(10);
        return encoder.encode(password);
    }
}