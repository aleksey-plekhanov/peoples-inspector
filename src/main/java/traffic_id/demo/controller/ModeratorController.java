package traffic_id.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.service.UserService;

@RestController
@RequestMapping("moderator")
public class ModeratorController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String moderatorList(Model model) {
        model.addAttribute("allModerators", userService.allModerators());
        return "moderator";
    }

    @PostMapping("/add")
    public String addModerator(@RequestParam(required = true, defaultValue = "" ) Integer userId,
                              Model model) {
        userService.addModerator(userId);
        return "redirect:/moderator";
    }

    @PostMapping("/remove")
    public String removeModerator(@RequestParam(required = true, defaultValue = "" ) Integer moderatorId,
                              Model model) {
        userService.removeModerator(moderatorId);
        return "redirect:/moderator";
    }

    @GetMapping("/gt/{moderatorId}")
    public String gtModerator(@PathVariable("moderatorId") Integer moderatorId, Model model) {
        model.addAttribute("allModerators", userService.moderatorgtList(moderatorId));
        return "moderator";
    }
}
