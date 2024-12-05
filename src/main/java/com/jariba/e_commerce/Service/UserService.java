package com.jariba.e_commerce.Service;


import com.jariba.e_commerce.Model.User;
import com.jariba.e_commerce.Repo.UserRepo;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

@Service
public class UserService implements UserDetailsService {
    @Autowired
    UserRepo userRepo;
    @Autowired
    JwtService jwtService;

    final int ENCODER_STRENGTH = 10;

    public String addUser(User user){



        if(userRepo.existsByEmailId(user.getEmailId()))
            throw new IllegalArgumentException("Email Id Already Exists");

        if(userRepo.existsByUserName(user.getUsername()))
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

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(ENCODER_STRENGTH);
        return encoder.encode(password);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        if(username == null)
            throw new UsernameNotFoundException("Username is Null");

        User user = userRepo.findByUserName(username);

        if(user == null)
        {
            //System.out.println("User Not Found");
            throw new UsernameNotFoundException("Username Not Valid");
        }
        return user;
    }

    public String verifyUserLogin(User user)
    {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(ENCODER_STRENGTH);
        try {
            UserDetails validUser = this.loadUserByUsername(user.getUsername());
            // Hashing the password is unique for evey time for the same input. so direct comparison won't work
            if(encoder.matches(user.getPassword(), validUser.getPassword()))
                return jwtService.generateToken(user.getUsername());
        }
        catch (UsernameNotFoundException e)
        {
            System.out.println(e.getMessage());
            return "Username Not Found";
        }

        return "Invalid Password";
    }

}
