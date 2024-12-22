package traffic_id.demo.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import traffic_id.demo.repository.FileRepository;

@Service
public class FileService {

    private final Path fileStorageLocation;

    @Autowired
    private FileRepository fileRepository;

    public FileService() {
        this.fileStorageLocation = Paths.get("Applications").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.fileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Не удалось создать каталог, в котором будут храниться загруженные файлы.", ex);
        }
    }

    /**
     * Сохраняет загруженный файл в указанном месте.
     * @param files Многостраничные файлы, которые нужно сохранить
     * @param applicationFolder ID папки заявления
     */
    public void saveFiles(MultipartFile[] files, String applicationFolder) {
        for (MultipartFile file : files)
        {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Path targetLocation = this.fileStorageLocation.resolve(applicationFolder);
                Files.createDirectories(targetLocation);
                Files.copy(file.getInputStream(), targetLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException ex) {
                throw new RuntimeException("Не удалось сохранить файл " + fileName + ". Пожалуйста, попробуйте снова!", ex);
            }
        }
    }

    /**
     * Добавляет файлы в БД.
     * @param file Многостраничный файл, который нужно сохранить
     */
    public Boolean saveFilesIntoDB(MultipartFile[] files, Integer idData) {
        for (MultipartFile file : files)
        {
            String extension = StringUtils.getFilenameExtension(file.getOriginalFilename());
            switch (extension)
            {
                case "png", "jpeg", "jpg", "webp" -> extension = "Фото";
                case "mp4", "m4v", "m4p", "wmv" -> extension = "Видео";
                case "mp3", "aac", "wma", "aiff" -> extension = "Фото";
                default -> {return false;}
            }
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            Path path = this.fileStorageLocation.resolve(idData.toString()).resolve(fileName);
            fileRepository.addFile(idData, path.toString(), extension);
        }
        return true;
    }

    /**
     * Загружает файл в качестве ресурса для скачивания.
     * @param fileName имя загружаемого файла
     * @return Ресурс, представляющий файл
     */
    public Resource loadFile(String fileName, String applicationFolder) {
        try {
            Path filePath = this.fileStorageLocation.resolve(applicationFolder).resolve(fileName).normalize();
            
            Resource resource = new UrlResource(filePath.toUri());
            if (!resource.exists())
                throw new RuntimeException("Файл не найден " + fileName);
            
            return resource;
        } catch (MalformedURLException ex) {
            throw new RuntimeException("Файл не найден " + fileName, ex);
        }
    }

    /**
     * Конвертирует путь в MultipartFile.
     * @return MultipartFile
     */
    public MultipartFile[] convertStringsToMultipartFiles(String[] paths) {
        MultipartFile[] files = new MultipartFile[paths.length];
        for (int i = 0; i < paths.length; i++)
        {
            File file = new File(paths[i]);
            //files[i] = new CommonsMultipartFile(file);
        }
        return files;
    }

    /**
     * Извлекает все имена файлов из хранилища.
     * @return Список имен файлов
     */
    public List<String> getAllFiles(String applicationFolder) {
        try {
            return Files.walk(this.fileStorageLocation.resolve(applicationFolder), 1)
                    .filter(path -> !path.equals(this.fileStorageLocation))
                    .map(this.fileStorageLocation::relativize)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException("Не удалось вывести список файлов!", ex);
        }
    }
}