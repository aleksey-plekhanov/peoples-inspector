package traffic_id.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.Violation;
import traffic_id.demo.repository.ViolationRepository;


@RestController
@RequestMapping("violation")
public class ViolationController {

    private final ViolationRepository repository;

    public ViolationController(ViolationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Violation> getAll() {
        return repository.findAll();
    }
}
