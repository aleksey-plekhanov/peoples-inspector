package traffic_id.demo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import traffic_id.demo.repository.*;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

    @Autowired
    private ViolationTypeRepository repositoryType;

    // Менять вручную
    private static final boolean isDebug = false;
    // для создания билда: 
    // gradlew build && docker-compose build && docker-compose up -d

    public static void main(String[] args) {
        if (isDebug) {
            System.setProperty("java.awt.headless", "false");
            System.setProperty("spring.flyway.enabled", "false");
        } else {
            System.setProperty("spring.flyway.baseline-on-migrate", "true");
        }

        SpringApplication.run(DemoApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println(repositoryType.findAll());

        if (isDebug) {
            java.awt.Desktop.getDesktop().browse(new URI("http://localhost:8000"));
        }
    }

}
