package traffic_id.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.PathVariable;

import traffic_id.demo.exceptions.EmailAlreadyExistException;
import traffic_id.demo.exceptions.LoginAlreadyExistException;
import traffic_id.demo.exceptions.PasswordIncorrectException;
import traffic_id.demo.exceptions.UserNotFoundException;
import traffic_id.demo.model.User;
import traffic_id.demo.service.UserDto;
import traffic_id.demo.service.UserService;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user")
    public String getUserAccount(Model model) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByLogin(userLogin);
        model.addAttribute("user", new UserDto(user));
        return "user";
    }

    @PostMapping("/user/save")
    public String editUserAccount(@Validated @ModelAttribute("user") UserDto userDto,
                            BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "user";
        }

        try {
            userService.editUserAccount(userDto);
        } catch (EmailAlreadyExistException eaex) {
            result.rejectValue("email", null, eaex.getLocalizedMessage());
        }
        catch (LoginAlreadyExistException | UserNotFoundException laex) {
            result.rejectValue("login", null, laex.getLocalizedMessage());
        }
        catch (PasswordIncorrectException piex) {
            result.rejectValue("password", null, piex.getLocalizedMessage());
        }

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "user";
        }
        
        return "redirect:/user";
    }
}
