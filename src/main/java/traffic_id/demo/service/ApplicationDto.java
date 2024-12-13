package traffic_id.demo.service;

import java.util.Arrays;
import java.util.List;

import traffic_id.demo.model.User;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ApplicationDto {

    private Integer applicationId;
    private User user;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    @NotEmpty
    private String information;

    @NotNull
    @NotEmpty
    private String district;

    @NotNull
    @NotEmpty
    private String address;
    
    private String commentary;
    private String status;
    private List<String> audios;
    private List<String> videos;
    private List<String> photos;
    private List<String> violations;

    public ApplicationDto(Integer applicationId, User user, String information, String title, String district, String address,
    String commentary, String status, String[] audios, String[] videos, String[] photos, String[] violations) {
        this.applicationId = applicationId;
        this.user = user;
        this.information = information;
        this.title = title;
        this.district = district;
        this.address = address;
        this.commentary = commentary;
        this.status = status;
        this.audios = Arrays.asList(audios);
        this.videos = Arrays.asList(videos);
        this.photos = Arrays.asList(photos);
        this.violations = Arrays.asList(violations);
    }

    public ApplicationDto(String information, String title, String district, String address,
        String status, String[] audios, String[] videos, String[] photos, String[] violations) {
        this.information = information;
        this.title = title;
        this.district = district;
        this.address = address;
        this.status = status;
        this.audios = Arrays.asList(audios);
        this.videos = Arrays.asList(videos);
        this.photos = Arrays.asList(photos);
        this.violations = Arrays.asList(violations);
    }

    public ApplicationDto() {
    }

    public void setApplicationId(Integer applicationId) {
        this.applicationId = applicationId;
    }

    public Integer getApplicationId() {
        return applicationId;
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

    public List<String> getAudios() {
        return audios;
    }

    public void setAudios(List<String> audios) {
        this.audios = audios;
    }

    public void addAudio(String audio) {
        audios.add(audio);
    }

    public void removeAudio(String audio) {
        audios.remove(audio);
    }

    public List<String> getVideos() {
        return videos;
    }

    public void setVideos(List<String> videos) {
        this.videos = videos;
    }

    public void addVideo(String video) {
        audios.add(video);
    }

    public void removeVideo(String video) {
        audios.remove(video);
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public void addPhoto(String photo) {
        audios.add(photo);
    }

    public void removePhoto(String photo) {
        audios.remove(photo);
    }

    public List<String> getViolations() {
        return violations;
    }

    public void setViolations(List<String> violations) {
        this.violations = violations;
    }

    public void addViolation(String violation) {
        audios.add(violation);
    }

    public void removeViolation(String violation) {
        audios.remove(violation);
    }
}
