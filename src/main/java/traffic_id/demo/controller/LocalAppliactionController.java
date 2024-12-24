package traffic_id.demo.controller;

import java.util.List;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
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

    private ApplicationDto unsendApplication = null;

    @GetMapping("/moderator/application")
    public String getAllUncheckedApplications(Model model) {
        if (!localApplicationService.isModeratorHasRole())
            return "redirect:/logout";
        List<Application> apps = localApplicationService.getAllUncheckedApplications();
        if (apps.size() != 0) {
            model.addAttribute("localApplication", apps.get(0));
            model.addAttribute("applications", apps);
            model.addAttribute("violations", localApplicationService.getApplicationViolations(apps.get(0)));
            model.addAttribute("files", localApplicationService.getApplicationFiles(apps.get(0)));
        }
        else {
            model.addAttribute("localApplication", new Application());
            model.addAttribute("applications", new ArrayList<Application>());
        }
        return "applicationForModerator";
    }

    @GetMapping("/moderator/application/{applicationId}")
    public String getUncheckedApplication(@PathVariable Integer applicationId, Model model) {
        if (!localApplicationService.isModeratorHasRole())
            return "redirect:/logout";
        Application application = localApplicationService.findApplicationById(applicationId);
        model.addAttribute("localApplication", application);
        model.addAttribute("applications", localApplicationService.getAllUncheckedApplications());
        model.addAttribute("violations", localApplicationService.getApplicationViolations(application));
        model.addAttribute("files", localApplicationService.getApplicationFiles(application));
        return "applicationForModerator";
    }

    @PostMapping("/moderator/application/send/{status}")
    public String sendCheckedApplication(@ModelAttribute Application localApplication, BindingResult result,
    @PathVariable String status,  Model model) {
        if (!localApplicationService.isModeratorHasRole())
            return "redirect:/logout";
        localApplicationService.sendCheckedApplication(localApplication, status);
        return "redirect:/moderator/application";
    }

    @GetMapping("/moderator/application/data/{type}")
    public List<File> getAllApplicationDataAudio(@PathVariable String type) {
        if (!localApplicationService.isModeratorHasRole())
            return null;
        return localApplicationService.getAllApplicationDataByType(type);
    }

    @GetMapping("/moderator/application/data/violation")
    public List<ApplicationViolation> getAllApplicationViolation() {
        if (!localApplicationService.isModeratorHasRole())
            return null;
        return localApplicationService.getAllApplicationViolations();
    }

    @GetMapping("/user/application")
    public String createApplication(Model model) {
        if (unsendApplication != null)
            model.addAttribute("applicationDto", unsendApplication);
        else 
            model.addAttribute("applicationDto", new ApplicationDto());

        model.addAttribute("applications", localApplicationService.getAllUserApplications());
        model.addAttribute("districts", localApplicationService.getAllDistricts());
        model.addAttribute("violations", localApplicationService.getAllViolations());
        return "applicationForUser";
    }

    @PostMapping("/user/application/send")
    public String sendApplicationForUser(@Validated @ModelAttribute ApplicationDto applicationDto, @RequestParam MultipartFile[] files, BindingResult result, Model model) {
        unsendApplication = null;
        localApplicationService.sendApplicationDto(applicationDto, files, "На рассмотрении");
        return "redirect:/user/application";
    }

    // @PostMapping("/user/draft")
    // public String draftApplicationForUser(@Validated @ModelAttribute ApplicationDto applicationDto, BindingResult result, Model model) {
    //     localApplicationService.sendApplicationDto(applicationDto, "Черновик");
    //     return "redirect:/user";
    // }

    @GetMapping("/user/application/{applicationId}")
    public String getApplicationForUser(@PathVariable Integer applicationId, @ModelAttribute ApplicationDto applicationDto, Model model) {
        if (applicationDto != null)
            unsendApplication = applicationDto;
        
        Application application = localApplicationService.findApplicationById(applicationId);
        if (application == null)
            //TO DO: переделать return в bad gateway
            return "applicationForUser";

        if (!localApplicationService.isUserOwnApplication(application))
            //TO DO: переделать return в bad gateway
            return "applicationForUser";

        model.addAttribute("localApplication", application);
        model.addAttribute("applications", localApplicationService.getAllUserApplications());
        model.addAttribute("violations", localApplicationService.getApplicationViolations(application));
        model.addAttribute("files", localApplicationService.getApplicationFiles(application));
        return "application";
    }

    /**
     * Загрузка файла из системы к пользователю
     */ 
    @GetMapping("/application/download/{applicationId}/{file}")
    public ResponseEntity<Resource> downloadFile(@PathVariable Integer applicationId, @PathVariable String file) {
        return localApplicationService.downloadFile(file, applicationId);
    }
}
