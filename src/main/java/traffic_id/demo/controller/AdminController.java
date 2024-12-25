package traffic_id.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.User;
import traffic_id.demo.model.Moderator;
import traffic_id.demo.service.UserService;

@RestController
@RequestMapping("admin")
public class AdminController {

    @Autowired
    private UserService userService;

    // @GetMapping("/user/all")
    // public String userList(Model model) {
    //     model.addAttribute("allUsers", userService.allUsers());
    //     return "user";
    // }

    @GetMapping("/user/all")
    public List<User> userList() {
        return userService.allUsers();
    }

    @GetMapping("/user/{userId}")
    public String getUser(@PathVariable Integer userId, Model model) {
        model.addAttribute("allUsers", userService.findUserById(userId));
        return "user";
    }

    // @GetMapping("/moderator/all")
    // public String moderatorList(Model model) {
    //     model.addAttribute("allModerators", userService.allModerators());
    //     return "moderator";
    // }

    @GetMapping("/moderator/all")
    public List<Moderator> moderatorList() {
        return userService.allModerators();
    }

    @GetMapping("/moderator/add/{userId}")
    public ResponseEntity<String> addModerator(@PathVariable Integer userId) {
        if (userService.findUserById(userId) == null) {
            return ResponseEntity.badRequest().body("Данного пользователя не существует");
        }

        if (!userService.addModerator(userId)) {
            return ResponseEntity.badRequest().body("Данный пользователь уже назначен модератором");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/admin/moderator/all");    
        return new ResponseEntity<String>(headers,HttpStatus.FOUND);
    }

    @GetMapping("/moderator/remove/{moderatorId}")
    public ResponseEntity<String> removeModerator(@PathVariable Integer moderatorId) {
        if (userService.findModeratorById(moderatorId) == null) {
            return ResponseEntity.badRequest().body("Данного модератора не существует");
        }

        if (!userService.removeModerator(moderatorId)) {
            return ResponseEntity.badRequest().body("Данный модератор уже снят с должности");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/admin/moderator/all");    
        return new ResponseEntity<String>(headers,HttpStatus.FOUND);
    }

    @GetMapping("/moderator/{moderatorId}")
    public String getModerator(@PathVariable Integer moderatorId, Model model) {
        model.addAttribute("allModerators", userService.findModeratorById(moderatorId));
        return "moderator";
    }
}
