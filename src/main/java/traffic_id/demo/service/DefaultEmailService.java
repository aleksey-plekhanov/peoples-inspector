package traffic_id.demo.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import traffic_id.demo.model.Application;

@Service
public class DefaultEmailService {

    @Autowired
    private JavaMailSender emailSender;

    @Autowired
    private SpringTemplateEngine templateEngine;

    @Autowired
    private LocalApplicationService localApplicationService;
   
    public void sendMail(ApplicationDto applicationDto) throws MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message,
                MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                StandardCharsets.UTF_8.name());
        Context context = new Context();
        Application application = localApplicationService.findApplicationById(applicationDto.getId());
        context.setVariable("localApplication", applicationDto);
        context.setVariable("violations", localApplicationService.getApplicationViolations(application));
        String emailContent = templateEngine.process("template-email", context);

        mimeMessageHelper.setTo("slava_samodurov@mail.ru");
        mimeMessageHelper.setSubject("Заявление №" + applicationDto.getId());
        mimeMessageHelper.setFrom("for_local_programms@mail.ru");
        mimeMessageHelper.setText(emailContent, true);
        List<String> filePaths = localApplicationService.getApplicationFilesFullPath(application);
        for (int i = 0; i < filePaths.size(); i++) {
            try {
                FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(filePaths.get(i)));
                mimeMessageHelper.addAttachment(file.getFilename(), file);
            } catch (FileNotFoundException ex) {
            }
        }
        emailSender.send(message);
    }
}
