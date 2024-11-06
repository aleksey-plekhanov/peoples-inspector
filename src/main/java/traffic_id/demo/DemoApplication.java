package traffic_id.demo;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import traffic_id.demo.model.*;
import traffic_id.demo.repository.*;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private TypeViolationRepository repositoryType;
	
	public static void main(String[] args) {
		// для отладки
		// System.setProperty("java.awt.headless", "false");
		// System.setProperty("spring.flyway.enabled", "false");
		// System.setProperty("spring.flyway.baseline-on-migrate", "true");
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(repositoryType.findAll());

		// для отладки
		// java.awt.Desktop.getDesktop().browse(new URI("http://localhost:8000"));
	}

}
