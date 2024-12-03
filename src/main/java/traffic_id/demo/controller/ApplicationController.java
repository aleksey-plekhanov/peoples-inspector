package traffic_id.demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import traffic_id.demo.model.Application;
import traffic_id.demo.model.ApplicationData;
import traffic_id.demo.model.ApplicationDataVideo;
import traffic_id.demo.model.ApplicationDataViolation;
import traffic_id.demo.model.ApplicationDataPhoto;
import traffic_id.demo.model.ApplicationDataAudio;
import traffic_id.demo.repository.ApplicationRepository;
import traffic_id.demo.repository.ApplicationDataRepository;
import traffic_id.demo.repository.ApplicationDataAudioRepository;
import traffic_id.demo.repository.ApplicationDataVideoRepository;
import traffic_id.demo.repository.ApplicationDataPhotoRepository;
import traffic_id.demo.repository.ApplicationDataViolationRepository;

@RestController
@RequestMapping("application")
public class ApplicationController {

    @Autowired
    private ApplicationRepository repApplication;

    @Autowired
    private ApplicationDataRepository repApplicationData;

    @Autowired
    private ApplicationDataAudioRepository repApplicationDataAudio;

    @Autowired
    private ApplicationDataVideoRepository repApplicationDataVideo;

    @Autowired
    private ApplicationDataPhotoRepository repApplicationDataPhoto;

    @Autowired
    private ApplicationDataViolationRepository repApplicationDataViolation;

    @GetMapping
    public List<Application> getAllApplication() {
        return repApplication.findAll();
    }

    @GetMapping("/data")
    public List<ApplicationData> getAllApplicationData() {
        return repApplicationData.findAll();
    }

    @GetMapping("/data/audio")
    public List<ApplicationDataAudio> getAllApplicationDataAudio() {
        return repApplicationDataAudio.findAll();
    }

    @GetMapping("/data/video")
    public List<ApplicationDataVideo> getAllApplicationDataVideo() {
        return repApplicationDataVideo.findAll();
    }

    @GetMapping("/data/photo")
    public List<ApplicationDataPhoto> getAllApplicationDataPhoto() {
        return repApplicationDataPhoto.findAll();
    }

    @GetMapping("/data/violation")
    public List<ApplicationDataViolation> getAllApplicationDataViolation() {
        return repApplicationDataViolation.findAll();
    }
}
