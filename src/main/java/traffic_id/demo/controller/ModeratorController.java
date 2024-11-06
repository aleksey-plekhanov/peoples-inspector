package traffic_id.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.Moderator;
import traffic_id.demo.repository.ModeratorRepository;

@RestController
@RequestMapping("user/moderator")
public class ModeratorController {

    private final ModeratorRepository repository;

    public ModeratorController(ModeratorRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Moderator> getAll() {
        return repository.findAll();
    }
}
