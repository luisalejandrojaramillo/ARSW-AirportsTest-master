package airportAPI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RequestMapping;

@SpringBootApplication
@ComponentScan(basePackages = {"airportAPI"})
public class AirportAPIApplication {
    @RequestMapping


    public static void main(String[] args) {
        SpringApplication.run(AirportAPIApplication.class, args);
    }
}
