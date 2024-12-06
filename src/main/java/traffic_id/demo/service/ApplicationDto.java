package traffic_id.demo.service;

import java.util.List;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

public class ApplicationDto {

    @NotNull
    @NotEmpty
    private String information;

    @NotNull
    @NotEmpty
    private String district;

    private List<String> audios;
    private List<String> videos;
    private List<String> photos;
    private List<Integer> violations;

    public ApplicationDto() {
    }

    public String getDistrict() {
        return district;
    }

    public void setInformation(String information) {
        this.information = information;
    }

    public String getInformation() {
        return information;
    }

    public void setDistrict(String district) {
        this.district = district;
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

    public List<Integer> getViolations() {
        return violations;
    }

    public void setViolations(List<Integer> violations) {
        this.violations = violations;
    }

    public void addViolation(Integer violation) {
        audios.add(violation);
    }

    public void removeViolation(Integer violation) {
        audios.remove(violation);
    }
}
