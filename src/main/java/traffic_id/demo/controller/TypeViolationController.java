package traffic_id.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.TypeViolation;
import traffic_id.demo.repository.TypeViolationRepository;


@RestController
@RequestMapping("violation/type")
public class TypeViolationController {

    private final TypeViolationRepository repository;

    public TypeViolationController(TypeViolationRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<TypeViolation> getAll() {
        return repository.findAll();
    }
}
