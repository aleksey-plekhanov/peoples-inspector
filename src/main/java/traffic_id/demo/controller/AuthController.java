package traffic_id.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import traffic_id.demo.exceptions.EmailAlreadyExistException;
import traffic_id.demo.exceptions.LoginAlreadyExistException;
import traffic_id.demo.exceptions.PasswordIncorrectException;
import traffic_id.demo.exceptions.UserNotFoundException;
import traffic_id.demo.service.UserDto;
import traffic_id.demo.service.UserService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login(Model model) {                     
        return "login";
    }

    @GetMapping("/registration")
    public String userForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "registration";
    }

    @PostMapping("/registration/save")
    public String userSubmit(@Validated @ModelAttribute("user") UserDto userDto,
                            BindingResult result, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("user", userDto);
            return "registration";
        }

        try {
            userService.registerNewUserAccount(userDto);
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
            return "registration";
        }
        
        return "redirect:/login";
    }

    // @GetMapping()
    // public ModelAndView registerUserAccount(@ModelAttribute("user") @Valid UserDto userDto,
    //                                         HttpServletRequest request, Errors errors) {
    
    //     try {
    //         userService.registerNewUserAccount(userDto);
    //     } catch (EmailAlreadyExistException | LoginAlreadyExistException | UserNotFound ex) {
    //         ModelAndView mav = new ModelAndView();
    //         mav.addObject("message", ex.toString());
    //         return mav;
    //     }
        
    //     return new ModelAndView("registration", "user", userDto);
    // }
}
