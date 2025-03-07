package Test.example.Test.interfaces;

import Test.example.Test.dto.AuthUserDTO;
import Test.example.Test.dto.LoginDTO;
import Test.example.Test.dto.PassDTO;
import org.springframework.stereotype.Service;

@Service
public interface IAuthInterface {
    public String register(AuthUserDTO user);


    public String login(LoginDTO user);

    public AuthUserDTO forgotPassword(PassDTO pass, String email);
}
