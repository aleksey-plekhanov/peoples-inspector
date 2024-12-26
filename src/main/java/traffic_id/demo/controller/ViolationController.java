package traffic_id.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.Violation;
import traffic_id.demo.model.ViolationType;
import traffic_id.demo.repository.ViolationRepository;
import traffic_id.demo.repository.ViolationTypeRepository;


@RestController
@RequestMapping("violation")
public class ViolationController {

    @Autowired
    private ViolationRepository repViolation;

    @Autowired
    private ViolationTypeRepository repViolationType;

    @GetMapping("/type")
    public List<ViolationType> getAllViolationType() {
        return repViolationType.findAll();
    }

    @GetMapping
    public List<Violation> getAllViolation() {
        return repViolation.findAll();
    }
}
