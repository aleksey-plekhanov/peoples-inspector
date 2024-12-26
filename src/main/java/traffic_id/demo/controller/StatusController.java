package traffic_id.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.Status;
import traffic_id.demo.repository.StatusRepository;


@RestController
@RequestMapping("status")
public class StatusController {
    
    @Autowired
    private StatusRepository repository;

    @GetMapping
    public List<Status> getAll() {
        return repository.findAll();
    }
}
