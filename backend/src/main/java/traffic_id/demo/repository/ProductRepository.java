package traffic_id.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import traffic_id.demo.entity.Product;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
