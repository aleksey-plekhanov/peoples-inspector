package traffic_id.demo.controller;

import java.util.List;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.util.StringUtils;

import traffic_id.demo.model.User;
import traffic_id.demo.model.Application;
import traffic_id.demo.model.ApplicationData;
import traffic_id.demo.model.ApplicationDataViolation;
import traffic_id.demo.model.File;
import traffic_id.demo.model.Moderator;
import traffic_id.demo.repository.ApplicationRepository;
import traffic_id.demo.repository.ApplicationDataRepository;
import traffic_id.demo.repository.FileRepository;
import traffic_id.demo.repository.ApplicationDataViolationRepository;
import traffic_id.demo.service.ApplicationDto;
import traffic_id.demo.service.FileService;
import traffic_id.demo.service.UserService;

@RestController
@RequestMapping("application")
public class ApplicationController {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private ApplicationDataRepository applicationDataRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ApplicationDataViolationRepository applicationDataViolationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    // Получение заявления - его данных
    private ApplicationDto getApplicationDto(Application application)
    {
        Integer idData = application.getData().getId();
        // Для проверки пока оставлю присваивание
        String information = applicationDataRepository.findByApplicationDataId(idData).getInformation();
        String[] audios = null, photos = null, videos = null, violations = null;
        applicationDataRepository.getApplicationData(application.getData().getId(), information, audios, photos, videos, violations);
    
        ApplicationDto applicationDto = new ApplicationDto(information, application.getTitle(), application.getDistrict().getDistrictName(),
        application.getAddress(), application.getStatus().getStatusName(), audios, photos, videos, violations);

        return applicationDto;
    }

    // Отправка заявления в бд
    private void sendApplicationDto(ApplicationDto applicationDto, String status)
    {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByLogin(userLogin);
        
        Integer applicationDataID = applicationDataRepository.createApplicationData(applicationDto.getInformation());
        fileService.saveFilesIntoDB(applicationDto.getAudios(), applicationDataID, "Аудио");
        fileService.saveFilesIntoDB(applicationDto.getPhotos(), applicationDataID, "Фото");
        fileService.saveFilesIntoDB(applicationDto.getVideos(), applicationDataID, "Видео");
        String[] violations = applicationDto.getViolations().toArray(new String[0]);
        applicationDataViolationRepository.addViolations(applicationDataID, violations);

        // TO DO: обработка исключений
        applicationRepository.createApplication(user.getId(), applicationDto.getTitle(), applicationDataID, applicationDto.getDistrict(),
        applicationDto.getAddress(), status);
    }

    @GetMapping("/moderator/all")
    public List<Application> getAllApplication() {
        return applicationRepository.findAll();
    }

    @GetMapping("/moderator/{applicationId}")
    public String getApplicationByModerator(@PathVariable Integer applicationId, Model model) {
        Application application = applicationRepository.findById(applicationId).get();
        if (application == null)
            //TO DO: переделать return в bad gateway
            return "applicationByModerator";
    
        model.addAttribute("applicationDatas", getApplicationDto(application));
        return "applicationByModerator";
    }

    @PostMapping("/moderator/send")
    public String sendApplicationByModerator(@Validated @ModelAttribute("applicationById") ApplicationDto applicationDto, BindingResult result, Model model) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Moderator moderator = userService.findModeratorByUser(userService.findUserByLogin(userLogin));
        
        applicationRepository.moderateApplication(applicationDto.getApplicationId(), moderator.getId(), 
        applicationDto.getCommentary(), applicationDto.getStatus());

        return "applicationByModerator";
    }

    @GetMapping("/moderator/data")
    public List<ApplicationData> getAllApplicationData() {
        return applicationDataRepository.findAll();
    }

    @GetMapping("/moderator/data/audio")
    public List<File> getAllApplicationDataAudio() {
        return fileRepository.findByTypeName("Аудио");
    }

    @GetMapping("/moderator/data/video")
    public List<File> getAllApplicationDataVideo() {
        return fileRepository.findByTypeName("Видео");
    }

    @GetMapping("/moderator/data/photo")
    public List<File> getAllApplicationDataPhoto() {
        return fileRepository.findByTypeName("Фото");
    }

    @GetMapping("/moderator/data/violation")
    public List<ApplicationDataViolation> getAllApplicationDataViolation() {
        return applicationDataViolationRepository.findAll();
    }

    @GetMapping("/user/create")
    public String createApplication(Model model) {
        model.addAttribute("applicationDatas", new ApplicationDto());
        return "applicationByUser";
    }

    @PostMapping("/user/send")
    public String sendApplicationByUser(@Validated @ModelAttribute("applicationDatas") ApplicationDto applicationDto, BindingResult result, Model model) {
        sendApplicationDto(applicationDto, "Зарегистрировано");
        return "applicationByUser";
    }

    @PostMapping("/user/draft")
    public String draftApplicationByUser(@Validated @ModelAttribute("applicationDatas") ApplicationDto applicationDto, BindingResult result, Model model) {
        sendApplicationDto(applicationDto, "Черновик");
        return "applicationByUser";
    }

    @GetMapping("/user/{applicationId}")
    public String getApplicationByUser(@PathVariable Integer applicationId, Model model) {
        Application application = applicationRepository.findById(applicationId).get();
        if (application == null)
            //TO DO: переделать return в bad gateway
            return "applicationByUser";

        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByLogin(userLogin);
        if (application.getUser() != user)
            //TO DO: переделать return в bad gateway
            return "applicationByUser";

        model.addAttribute("applicationDatas", getApplicationDto(application));
        return "applicationByUser";
    }

    /**
     * Загрузка файла от пользователя в систему
     */ 
    @SuppressWarnings("null")
    @PostMapping("/user/uploadFile")
    public String uploadFile(@RequestParam MultipartFile file, @ModelAttribute("applicationDatas") ApplicationDto applicationDto, Model model) {
        
        model.addAttribute("applicationDatas", applicationDto);
        
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        switch (extension)
        {
            case "png", "jpeg", "jpg" -> applicationDto.addPhoto(file.getOriginalFilename());
            case "mp4", "m4v", "m4p", "wmv" -> applicationDto.addVideo(file.getOriginalFilename());
            case "mp3", "aac", "wma", "aiff" -> applicationDto.addAudio(file.getOriginalFilename());
            default -> { 
                model.addAttribute("errorUpload", "Ошибка! Недопустимый формат файла");
                return "applicationByUser";
            }
        }
        
        Integer applicationDataID = applicationDataRepository.createApplicationData(applicationDto.getInformation());
        fileService.saveFile(file, applicationDataID.toString());
        
        return "applicationByUser";
    }

    /**
     * Загрузка файла из системы к пользователю
     */ 
    @GetMapping("/moderator/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam MultipartFile file, @ModelAttribute("applicationById") ApplicationDto applicationDto) {
        Integer applicationDataID = applicationDataRepository.createApplicationData(applicationDto.getInformation());
        Resource resource = fileService.loadFile(file.getOriginalFilename(), applicationDataID.toString());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
