package traffic_id.demo.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;

import traffic_id.demo.model.Application;
import traffic_id.demo.model.ApplicationViolation;
import traffic_id.demo.model.File;
import traffic_id.demo.service.ApplicationDto;
import traffic_id.demo.service.LocalApplicationService;

@Controller
public class LocalAppliactionController {

    @Autowired
    private LocalApplicationService localApplicationService;

    @GetMapping("/moderator/application")
    public List<Application> getAllApplication() {
        return localApplicationService.allApplications();
    }

    @GetMapping("/moderator/application/{applicationId}")
    public String getApplicationByModerator(@PathVariable Integer applicationId, Model model) {
        Application application = localApplicationService.findApplicationById(applicationId);
        if (application == null)
            //TO DO: переделать return в bad gateway
            return "applicationByModerator";
    
        model.addAttribute("applicationDto", localApplicationService.getApplicationDto(application));
        return "applicationByModerator";
    }

    @PostMapping("/moderator/application/send")
    public String sendApplicationByModerator(@Validated @ModelAttribute ApplicationDto applicationDto, BindingResult result, Model model) {
        localApplicationService.sendApplicationByModerator(applicationDto);
        return "applicationByModerator";
    }

    @GetMapping("/moderator/application/data/{type}")
    public List<File> getAllApplicationDataAudio(@PathVariable String type) {
        return localApplicationService.getAllApplicationDataByType(type);
    }

    @GetMapping("/moderator/application/data/violation")
    public List<ApplicationViolation> getAllApplicationViolation() {
        return localApplicationService.getAllApplicationViolation();
    }

    @GetMapping("/user/application")
    public String createApplication(Model model) {
        model.addAttribute("applicationDto", new ApplicationDto());
        model.addAttribute("districts", localApplicationService.getAllDistricts());
        model.addAttribute("violations", localApplicationService.getAllViolations());
        return "applicationForUser";
    }

    @PostMapping("/user/application/send")
    public String sendApplicationForUser(@Validated @ModelAttribute ApplicationDto applicationDto, @RequestParam MultipartFile[] files, BindingResult result, Model model) {
        localApplicationService.sendApplicationDto(applicationDto, files, "На рассмотрении");
        return "redirect:/user/application";
    }

    // @PostMapping("/user/draft")
    // public String draftApplicationForUser(@Validated @ModelAttribute ApplicationDto applicationDto, BindingResult result, Model model) {
    //     localApplicationService.sendApplicationDto(applicationDto, "Черновик");
    //     return "redirect:/user";
    // }

    @GetMapping("/user/application/{applicationId}")
    public String getApplicationForUser(@PathVariable Integer applicationId, Model model) {
        Application application = localApplicationService.findApplicationById(applicationId);
        if (application == null)
            //TO DO: переделать return в bad gateway
            return "applicationForUser";

        if (!localApplicationService.isUserOwnApplication(application))
            //TO DO: переделать return в bad gateway
            return "applicationForUser";

        model.addAttribute("applicationDto", localApplicationService.getApplicationDto(application));
        model.addAttribute("districts", localApplicationService.getAllDistricts());
        model.addAttribute("violations", localApplicationService.getAllViolations());
        return "applicationForUser";
    }

    /**
     * Загрузка файла из системы к пользователю
     */ 
    @GetMapping("/moderator/application/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam MultipartFile file, @ModelAttribute ApplicationDto applicationDto) {
        return localApplicationService.downloadFile(file, applicationDto);
    }
}
