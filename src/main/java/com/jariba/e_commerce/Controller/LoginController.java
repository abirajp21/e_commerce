package com.jariba.e_commerce.Controller;


import com.jariba.e_commerce.Model.*;
import com.jariba.e_commerce.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class LoginController {

    @Autowired
    UserService userService;

//    @RequestMapping("/login")
//    public String login() {
//        return userService.login();
//    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody @Valid User user) {

        String response;
        try{
            response = userService.addUser(user);
        }
        catch (Exception e)
        {
            response = e.getMessage();
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
    @DeleteMapping("")
    public ResponseEntity<?> deleteUser(@RequestParam String userName )
    {

        //System.out.println(userName);
        Boolean stauts = userService.deleteUser(userName);

        if(stauts)
            return new ResponseEntity<>(HttpStatus.OK);

        else
            return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

}
