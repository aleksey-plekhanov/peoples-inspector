package traffic_id.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.District;
import traffic_id.demo.repository.DistrictRepository;

@RestController
@RequestMapping("district")
public class DistrictController {

    private final DistrictRepository repository;

    public DistrictController(DistrictRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<District> getAll() {
        return repository.findAll();
    }
}
