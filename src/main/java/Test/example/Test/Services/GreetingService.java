package Test.example.Test.Services;


import org.springframework.stereotype.Service;
import org.springframework.stereotype.Service;

@Service
public class GreetingService {
    String message;

    GreetingService(){
        message = "Hello World!";
    }

    public String getGreetings(){
        return this.message;
    }
}
