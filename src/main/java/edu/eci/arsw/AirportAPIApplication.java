package edu.eci.arsw;

import edu.eci.arsw.model.Airport;
import edu.eci.arsw.service.AirportFinderServices;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@SpringBootApplication
//@ComponentScan(basePackages = {""})
public class AirportAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(AirportAPIApplication.class, args);
    }
    @Bean
    public CommandLineRunner demo(AirportFinderServices service) {
        return (args) -> {
            List<Airport> tempo = service.findAirportsByName("bogota");
            for (Airport te : tempo) {
                System.out.println(te.getName());
            }
        };
    }
}
