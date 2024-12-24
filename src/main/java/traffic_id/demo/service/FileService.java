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

    private final Path applicationFileStorageLocation;
    private final Path avatarFileStorageLocation;

    @Autowired
    private FileRepository fileRepository;

    public FileService() {
        this.applicationFileStorageLocation = Paths.get("Applications").toAbsolutePath().normalize();
        this.avatarFileStorageLocation = Paths.get("Avatars").toAbsolutePath().normalize();
        try {
            Files.createDirectories(this.applicationFileStorageLocation);
        } catch (IOException ex) {
            throw new RuntimeException("Не удалось создать каталог, в котором будут храниться загруженные файлы.", ex);
        }
    }

    /**
     * Сохраняет загруженный файл в указанном месте.
     * @param file Многостраничные файлы, которые нужно сохранить
     * @param folder ID данных пользователя
     * @return путь к загруженному файлу
     */
    public String saveAvatarFile(MultipartFile file, String folder) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path targetLocation = this.applicationFileStorageLocation.resolve(folder);
            Files.createDirectories(targetLocation);
            Files.copy(file.getInputStream(), targetLocation.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
            return targetLocation.resolve(fileName).toString();
            } catch (IOException ex) {
                throw new RuntimeException("Не удалось сохранить файл " + fileName + ". Пожалуйста, попробуйте снова!", ex);
            }
    }

    /**
     * Сохраняет загруженный файл в указанном месте.
     * @param files Многостраничные файлы, которые нужно сохранить
     * @param folder ID заявления
     */
    public void saveApplicationFiles(MultipartFile[] files, String folder) {
        for (MultipartFile file : files)
        {
            String fileName = StringUtils.cleanPath(file.getOriginalFilename());
            try {
                Path targetLocation = this.applicationFileStorageLocation.resolve(folder);
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
    public Boolean saveApplicationFilesIntoDB(MultipartFile[] files, Integer idData) {
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
            Path path = this.applicationFileStorageLocation.resolve(idData.toString()).resolve(fileName);
            fileRepository.addFile(idData, path.toString(), extension);
        }
        return true;
    }

    /**
     * Загружает файл в качестве ресурса для скачивания.
     * @param fileName имя загружаемого файла
     * @return Ресурс, представляющий файл
     */
    public Resource loadFile(String fileName, String folder) {
        try {
            Path filePath = this.applicationFileStorageLocation.resolve(folder).resolve(fileName).normalize();
            
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
            return Files.walk(this.applicationFileStorageLocation.resolve(applicationFolder), 1)
                    .filter(Files::isRegularFile)
                    .map(this.applicationFileStorageLocation::relativize)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException("Не удалось вывести список файлов!", ex);
        }
    }
}