package traffic_id.demo.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import traffic_id.demo.model.District;
import traffic_id.demo.model.Status;
import traffic_id.demo.model.Application;
import traffic_id.demo.model.ApplicationData;
import traffic_id.demo.model.ApplicationDataVideo;
import traffic_id.demo.model.ApplicationDataViolation;
import traffic_id.demo.model.ApplicationDataPhoto;
import traffic_id.demo.model.ApplicationDataAudio;
import traffic_id.demo.repository.DistrictRepository;
import traffic_id.demo.repository.StatusRepository;
import traffic_id.demo.repository.ApplicationRepository;
import traffic_id.demo.repository.ApplicationDataRepository;
import traffic_id.demo.repository.ApplicationDataAudioRepository;
import traffic_id.demo.repository.ApplicationDataVideoRepository;
import traffic_id.demo.repository.ApplicationDataPhotoRepository;
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
    private ApplicationDataAudioRepository applicationDataAudioRepository;

    @Autowired
    private ApplicationDataVideoRepository applicationDataVideoRepository;

    @Autowired
    private ApplicationDataPhotoRepository applicationDataPhotoRepository;

    @Autowired
    private ApplicationDataViolationRepository applicationDataViolationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private FileService fileService;

    @GetMapping("/all")
    public List<Application> getAllApplication() {
        return applicationRepository.findAll();
    }

    @GetMapping("/data")
    public List<ApplicationData> getAllApplicationData() {
        return applicationDataRepository.findAll();
    }

    @GetMapping("/data/audio")
    public List<ApplicationDataAudio> getAllApplicationDataAudio() {
        return applicationDataAudioRepository.findAll();
    }

    @GetMapping("/data/video")
    public List<ApplicationDataVideo> getAllApplicationDataVideo() {
        return applicationDataVideoRepository.findAll();
    }

    @GetMapping("/data/photo")
    public List<ApplicationDataPhoto> getAllApplicationDataPhoto() {
        return applicationDataPhotoRepository.findAll();
    }

    @GetMapping("/data/violation")
    public List<ApplicationDataViolation> getAllApplicationDataViolation() {
        return applicationDataViolationRepository.findAll();
    }

    @GetMapping("/create")
    public String createApplication(Model model) {
        model.addAttribute("applicationDatas", new ApplicationDto());
        return "application";
    }

    @PostMapping("/send")
    public String sendApplication(@Validated @ModelAttribute("applicationDatas") ApplicationDto applicationDto, BindingResult result, Model model) {
        
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByLogin(userLogin);
        
        District district = districtRepository.findByDistrict(applicationDto.getDistrict());
        Status status = statusRepository.findByStatus("Зарегистрировано");

        Integer applicationDataID = applicationDataRepository.createApplicationData(applicationDto.getInformation());
        String [] audios = applicationDto.getAudios().toArray(new String[0]);
        String [] photos = applicationDto.getPhotos().toArray(new String[0]);
        String [] videos = applicationDto.getVideos().toArray(new String[0]);
        Integer [] violations = applicationDto.getViolations().toArray(new Integer[0]);
        applicationDataRepository.fillApplicationData(applicationDataID, audios, photos, videos, violations);

        Optional<ApplicationData> applicationData = applicationDataRepository.findById(applicationDataID);

        Application application = new Application(user, applicationData.get(), district, status);
        applicationRepository.save(application);

        return "application";
    }

    /**
     * Загрузка файла от пользователя в систему
     */ 
    @PostMapping("/uploadFile")
    public String uploadFile(@RequestParam MultipartFile file, @ModelAttribute("applicationDatas") ApplicationDto applicationDto, Model model) {
        
        model.addAttribute("applicationDatas", applicationDto);
        
        String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        switch (extension)
        {
            case "png", "jpeg", "jpg": 
                applicationDto.addPhoto(file.getOriginalFilename());
                break;
            case "mp4", "m4v", "m4p", "wmv": 
                applicationDto.addVideo(file.getOriginalFilename());
                break;
            case "mp3", "aac", "wma", "aiff": 
                applicationDto.addAudio(file.getOriginalFilename());
                break;
            default: 
                model.addAttribute("errorUpload", "Ошибка! Недопустимый формат файла");
                return "registration";
        }
        
        Integer applicationDataID = applicationDataRepository.createApplicationData(applicationDto.getInformation());
        fileService.saveFile(file, applicationDataID.toString());
        
        return "application";
    }

    /**
     * Загрузка файла из системы к пользователю
     */ 
    @GetMapping("/downloadFile")
    public ResponseEntity<Resource> downloadFile(@RequestParam MultipartFile file, @ModelAttribute("applicationDatas") ApplicationDto applicationDto) {
        Integer applicationDataID = applicationDataRepository.createApplicationData(applicationDto.getInformation());
        Resource resource = fileService.loadFile(file.getOriginalFilename(), applicationDataID.toString());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
