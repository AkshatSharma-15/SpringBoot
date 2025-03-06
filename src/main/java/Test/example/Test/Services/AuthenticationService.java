package Test.example.Test.Services;

import Test.example.Test.dto.AuthUserDTO;
import Test.example.Test.modals.AuthUser;
import Test.example.Test.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import Test.example.Test.dto.LoginDTO;
import Test.example.Test.Services.JwtTokenService;
import Test.example.Test.Services.EmailService;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {
    UserRepository userRepository;
    EmailService emailService;
    JwtTokenService jwtTokenService;

    public AuthenticationService(UserRepository userRepository, EmailService emailService) {
        this.userRepository = userRepository;
        this.emailService = emailService;
    }

    public String register(AuthUserDTO user){

        List<AuthUser> l1 = userRepository.findAll().stream().filter(authuser -> user.getEmail().equals(authuser.getEmail())).collect(Collectors.toList());

        if(l1.size()>0){
            return "User already registered";
        }

        //creating hashed password using bcrypt
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
        String hashPass = bcrypt.encode(user.getPassword());

        //creating new user
        AuthUser newUser = new AuthUser(user.getFirstName(), user.getLastName(), user.getEmail(), user.getPassword(), hashPass);

        //setting the new hashed password
        newUser.setHashPass(hashPass);

        //saving the user in the database
        userRepository.save(newUser);

        //sending the confirmation mail to the user
        emailService.sendEmail(user.getEmail(), "Regitration Status", user.getFirstName()+" you are registered!");

        return "user registered";
    }

    public String login(LoginDTO user){

        List<AuthUser> l1 = userRepository.findAll().stream().filter(authuser -> authuser.getEmail().equals(user.getEmail())).collect(Collectors.toList());
        if(l1.size() == 0)
            return "User not registered";

        AuthUser foundUser = l1.get(0);

        //matching the stored hashed password with the password provided by user
        BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

        if(!bcrypt.matches(user.getPassword(), foundUser.getHashPass()))
            return "Invalid password";

        //creating Jwt Token
        String token = jwtTokenService.createToken(foundUser.getId());

        //setting token for user login
        foundUser.setToken(token);

        //saving the current status of user in database
        userRepository.save(foundUser);

        return "user logged in"+"\ntoken : "+token;
    }
}
