package group4.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "group4.backend")
public class Main {

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}
