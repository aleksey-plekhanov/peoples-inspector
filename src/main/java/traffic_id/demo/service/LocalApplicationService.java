package traffic_id.demo.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import traffic_id.demo.controller.LocalAppliactionController;
import traffic_id.demo.controller.UserController;
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
        User user = userService.getUser();
        
        // TO DO: обработка исключений
        Integer applicationID = applicationRepository.createApplication(user.getId(), applicationDto.getTitle(), 
                                            applicationDto.getInformation(), applicationDto.getDateViolation(), 
                                            applicationDto.getDistrict(), applicationDto.getAddress(), status);

        fileService.saveApplicationFiles(files, applicationID.toString());
        fileService.saveApplicationFilesIntoDB(files, applicationID);  
        String[] violations = applicationDto.getViolations().toArray(new String[0]);
        applicationViolationRepository.addViolations(applicationID, violations);
    }

    public void sendCheckedApplication(ApplicationDto applicationDto, String status) {
        Moderator moderator = userService.findModeratorByUser(userService.getUser());

        applicationRepository.moderateApplication(applicationDto.getId(), moderator.getId(), 
        applicationDto.getCommentary(), status);
    }

    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }

    public List<Application> getAllUncheckedApplications() {
        return applicationRepository.findUncheckedApplications(userService.getUser().getId());
    }

    public List<Application> getAllUncheckedApplications(String titleFragment) {
        return applicationRepository.findUncheckedApplications(userService.getUser().getId(), titleFragment);
    }

    public List<Application> sortApplicationsByDateArrive(List<Application> apps, String sort) {
        Comparator<Application> compareByDateArrive = Comparator.comparing(Application::getDateArrive);
        if (sort.equals("desc"))
            compareByDateArrive = Comparator.comparing(Application::getDateArrive, (s1, s2) -> {
                return s2.compareTo(s1);
            });
        apps.sort(compareByDateArrive);
        return apps;
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
        return applicationRepository.findByUser(userService.getUser());
    }

    public List<Application> getAllUserApplications(String titleFragment) {
        return applicationRepository.findByUserAndTitleFragment(userService.getUser().getId(), titleFragment);
    }

    public Boolean isTitleExist(String title) {
        return (applicationRepository.findByTitle(title) != null);
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

    public List<String> getApplicationFilesFullPath(Application application) {
        return fileRepository.findByApplication(application.getId());
    }

    public String getApplicationFileStorageLocation() {
        return fileService.getApplicationFileStorageLocation().toString();
    }

    public Boolean isUserOwnApplication(Application application) {
        return (application.getUser() == userService.getUser());
    }

    public Boolean isModeratorHasRole() {
        return userService.isModeratorHasRole();
    }

    public ResponseEntity<Resource> downloadFile(String file, Integer applicationId) {
        return fileService.downloadFile(file, applicationId);
    }
}
