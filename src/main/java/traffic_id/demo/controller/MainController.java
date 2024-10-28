package traffic_id.demo.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class MainController {

    @GetMapping("/hello")
    public String hello(){
        return "<h2> Привет Всем! </h2>";
    }

    @GetMapping("/user")
    public String user(){
        return "<h2> Привет, Пользователь! </h2>";
    }

    @GetMapping("/admin")
    public String admin(){
        return "<h2> Привет, Админ! </h2>";
    }
}
