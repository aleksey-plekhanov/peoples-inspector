package traffic_id.demo.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String userList(Model model) {
        model.addAttribute("allUsers", userService.allUsers());
        return "user";
    }

    @GetMapping("/gt/{userId}")
    public String gtUser(@PathVariable("userId") Integer userId, Model model) {
        model.addAttribute("allModerators", userService.usergtList(userId));
        return "user";
    }
}
