package traffic_id.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.UserData;
import traffic_id.demo.repository.UserDataRepository;


@RestController
@RequestMapping("user/data")
public class UserDataController {

    @Autowired
    private UserDataRepository repository;

    @GetMapping
    public List<UserData> getAll() {
        return repository.findAll();
    }
}
