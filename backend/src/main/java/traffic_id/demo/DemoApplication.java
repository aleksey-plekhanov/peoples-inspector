package traffic_id.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import traffic_id.demo.entity.Product;
import traffic_id.demo.repository.ProductRepository;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	@Autowired
	private ProductRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Product product = new Product(null, "name");
		repository.save(product);

		java.util.List<Product> all = repository.findAll();
		System.out.println(all);
	}

}
