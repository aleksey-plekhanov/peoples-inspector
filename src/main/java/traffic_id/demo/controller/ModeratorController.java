package traffic_id.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.Moderator;
import traffic_id.demo.service.UserService;

@RestController
@RequestMapping("moderator")
public class ModeratorController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/all")
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "user";
    }

    @GetMapping("/user/get/{userId}")
    public String getUser(@PathVariable Integer userId, Model model) {
        model.addAttribute("allUsers", userService.findUserById(userId));
        return "user";
    }
}
