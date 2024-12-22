package traffic_id.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import traffic_id.demo.model.Application;
import traffic_id.demo.model.ApplicationViolation;
import traffic_id.demo.model.File;
import traffic_id.demo.model.Moderator;
import traffic_id.demo.model.User;
import traffic_id.demo.model.Violation;
import traffic_id.demo.model.District;
import traffic_id.demo.repository.ApplicationViolationRepository;
import traffic_id.demo.repository.ApplicationRepository;
import traffic_id.demo.repository.DistrictRepository;
import traffic_id.demo.repository.ViolationRepository;
import traffic_id.demo.repository.FileRepository;

@Service
public class LocalApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ApplicationViolationRepository applicationViolationRepository;

    @Autowired
    private DistrictRepository districtRepository;

    @Autowired
    private ViolationRepository violationRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    // Получение заявления - его данных
    public ApplicationDto getApplicationDto(Application application)
    {
        // Для проверки пока оставлю присваивание
        String[] files = null, violations = null;
        applicationRepository.getApplicationData(application.getId(), files, violations);
        ApplicationDto applicationDto = new ApplicationDto(application.getInformation(), application.getTitle(), application.getDistrict().getDistrictName(),
        application.getAddress(), application.getStatus().getStatusName(), violations);

        return applicationDto;
    }

    // Отправка заявления в бд
    public void sendApplicationDto(ApplicationDto applicationDto, MultipartFile[] files, String status)
    {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByLogin(userLogin);
        
        // TO DO: обработка исключений
        Integer applicationID = applicationRepository.createApplication(user.getId(), applicationDto.getTitle(), 
                                            applicationDto.getInformation(), applicationDto.getDateViolation(), 
                                            applicationDto.getDistrict(), applicationDto.getAddress(), status);

        fileService.saveFiles(files, applicationID.toString());
        fileService.saveFilesIntoDB(files, applicationID);  
        String[] violations = applicationDto.getViolations().toArray(new String[0]);
        applicationViolationRepository.addViolations(applicationID, violations);
    }

    public void sendApplicationByModerator(ApplicationDto applicationDto) {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        Moderator moderator = userService.findModeratorByUser(userService.findUserByLogin(userLogin));
        
        applicationRepository.moderateApplication(applicationDto.getApplicationId(), moderator.getId(), 
        applicationDto.getCommentary(), applicationDto.getStatus());
    }

    public List<Application> allApplications() {
        return applicationRepository.findAll();
    }

    public Application findApplicationById(Integer applicationId) {
        Optional<Application> applicationFromDb = applicationRepository.findById(applicationId);
        return applicationFromDb.orElse(null);
    }

    public List<District> getAllDistricts() {
        return districtRepository.findAll();
    }

    public List<Violation> getAllViolations() {
        return violationRepository.findAll();
    }

    public List<Application> getAllUserApplications() {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByLogin(userLogin);
        return applicationRepository.findByUser(user);
    }

    public List<File> getAllApplicationDataByType(String type) {
        String rusType = "";
        switch (type)
        {
            case "audio" -> rusType = "Аудио";
            case "video" -> rusType = "Видео";
            case "photo" -> rusType = "Фото";
        }
        return fileRepository.findByTypeName(rusType);
    }

    public List<ApplicationViolation> getAllApplicationViolations() {
        return applicationViolationRepository.findAll();
    }

    public List<Violation> getApplicationViolations(Application application) {
        List<ApplicationViolation> applicationViolations = applicationViolationRepository.findByApplication(application);
        List<Violation> violations = new ArrayList<>();
        for (ApplicationViolation applicationViolation : applicationViolations)
        {
            Violation violation = violationRepository.findByArticle(applicationViolation.getViolation().getArticle());
            violations.add(violation);
        }
        return violations;
    }

    public List<String> getApplicationFiles(Application application) {
        return fileService.getAllFiles(application.getId().toString());
    }

    public Boolean isUserOwnApplication(Application application)
    {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.findUserByLogin(userLogin);
        return (application.getUser() == user);
    }

    public ResponseEntity<Resource> downloadFile(String file, Integer applicationId) {
        Resource resource = fileService.loadFile(file, applicationId.toString());
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }
}
