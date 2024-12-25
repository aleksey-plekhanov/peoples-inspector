package traffic_id.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import traffic_id.demo.exceptions.EmailAlreadyExistException;
import traffic_id.demo.exceptions.LoginAlreadyExistException;
import traffic_id.demo.exceptions.PasswordIncorrectException;
import traffic_id.demo.exceptions.UserNotFoundException;
import traffic_id.demo.model.User;
import traffic_id.demo.service.UserDto;
import traffic_id.demo.service.UserService;

@Controller
@RequestMapping("user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public String getUserAccount(Model model) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByLogin(userLogin);
        UserDto userDto = new UserDto(user);

        String avatar = userService.getAvatar(userDto.getAvatar(), user.getData().getId().toString());
        model.addAttribute("userDto", userDto);
        model.addAttribute("urlAvatar", avatar);
        return "user";
    }

    @PostMapping("/save")
    public String editUserAccount(@RequestParam("file") MultipartFile avatar, @Validated @ModelAttribute UserDto userDto,
                            BindingResult result, Model model) {
        
        if (result.hasErrors()) {
            model.addAttribute("userDto", userDto);
            return "user";
        }

        try {
            userService.editUserAccount(userDto, avatar);
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
            model.addAttribute("userDto", userDto);
            return "user";
        }
        
        return "redirect:/user";
    }

    @PostMapping("/avatar/delete")
    public String deleteAvatar(@ModelAttribute UserDto userDto,
                            BindingResult result, Model model) {
        User user = userService.findUserByLogin(userDto.getLogin());
        userDto.setAvatar("img/emptyProfile.png");
        String avatar = userService.getAvatar(userDto.getAvatar(), "static/img");
        model.addAttribute("userDto", userDto);
        model.addAttribute("urlAvatar", avatar);
        return "user";
    }

    @GetMapping("/avatar/{filename:.+}")
    public ResponseEntity<Resource> getAvatar(@PathVariable String fileName, Integer folder) {
        Resource file = userService.loadAvatar(fileName, folder.toString());
        return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                    .body(file);
    }
}
