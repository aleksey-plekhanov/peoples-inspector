package traffic_id.demo.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

import traffic_id.demo.model.User;
import traffic_id.demo.model.Application;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ApplicationDto {

    private Integer id;
    private User user;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String information;
    
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    private Date dateViolation;

    @NotNull
    @NotEmpty
    private String district;

    @NotNull
    @NotEmpty
    private String address;
    
    private Date dateArrive;
    private String commentary;
    private String status;
    private List<String> violations;

    public ApplicationDto(Integer applicationId, User user, String information, Date dateViolation, Date dateArrive, 
    String title, String district, String address, String commentary, String status, String[] violations) {
        this.id = applicationId;
        this.user = user;
        this.information = information;
        this.dateViolation = dateViolation;
        this.dateArrive = dateArrive;
        this.title = title;
        this.district = district;
        this.address = address;
        this.commentary = commentary;
        this.status = status;
        this.violations = Arrays.asList(violations);
    }

    public ApplicationDto(String information, String title, String district, String address,
        String status, String[] violations) {
        this.information = information;
        this.title = title;
        this.district = district;
        this.address = address;
        this.status = status;
        this.violations = Arrays.asList(violations);
    }

    public ApplicationDto(Application application) {
        this.id = application.getId();
        this.user = application.getUser();
        this.information = application.getInformation();
        this.dateViolation = application.getDateViolation();
        this.dateArrive = application.getDateArrive();
        this.title = application.getTitle();
        this.district = application.getDistrict().getDistrictName();
        this.address = application.getAddress();
        this.commentary = application.getCommentary();
        this.status = application.getStatus().getStatusName();
    }

    public ApplicationDto() {
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public Date getDateViolation() {
        return dateViolation;
    }

    public void setDateViolation(Date dateViolation) {
        this.dateViolation = dateViolation;
    }

    public Date getDateArrive() {
        return dateArrive;
    }

    public void setDateArrive(Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
    }

    public String getCommentary() {
        return commentary;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public List<String> getViolations() {
        return violations;
    }

    public void setViolations(List<String> violations) {
        this.violations = violations;
    }
}
