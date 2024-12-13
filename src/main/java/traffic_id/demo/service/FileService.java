package traffic_id.demo.service;

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
     * @param file Многостраничный файл, который нужно сохранить
     */
    public void saveFile(MultipartFile file, String applicationFolder) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        try {
            Path targetLocation = this.fileStorageLocation.resolve(applicationFolder).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            throw new RuntimeException("Не удалось сохранить файл " + fileName + ". Пожалуйста, попробуйте снова!", ex);
        }
    }

    /**
     * Добавляет файлы в БД.
     * @param file Многостраничный файл, который нужно сохранить
     */
    public void saveFilesIntoDB(List<String> files, Integer idData, String type) {
        for (String file : files)
        {
            Path path = this.fileStorageLocation.resolve(idData.toString()).resolve(file);
            path.toString();
            fileRepository.addFile(idData, path.toString(), type);
        }
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
     * Извлекает все имена файлов из хранилища.
     * @return Список имен файлов
     */
    public List<String> getAllFiles() {
        try {
            return Files.walk(this.fileStorageLocation, 1)
                    .filter(path -> !path.equals(this.fileStorageLocation))
                    .map(this.fileStorageLocation::relativize)
                    .map(Path::toString)
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            throw new RuntimeException("Не удалось вывести список файлов!", ex);
        }
    }
}