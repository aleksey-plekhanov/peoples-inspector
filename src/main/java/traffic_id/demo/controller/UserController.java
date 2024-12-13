package traffic_id.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.User;
import traffic_id.demo.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "user";
    }

    @GetMapping
    public List<User> getAll() {
        return userService.allUsers();
    }

    @GetMapping("/get/{userId}")
    public String getUser(@PathVariable Integer userId, Model model) {
        model.addAttribute("allUsers", userService.usergtList(userId));
        return "user";
    }
}
