package main.java.ru.valya.serveback;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class ServeBackApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(ServeBackApplication.class, args);
    }

}
