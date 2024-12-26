package traffic_id.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import traffic_id.demo.repository.UserRepository;
import traffic_id.demo.controller.UserController;
import traffic_id.demo.exceptions.EmailAlreadyExistException;
import traffic_id.demo.exceptions.LoginAlreadyExistException;
import traffic_id.demo.exceptions.PasswordIncorrectException;
import traffic_id.demo.exceptions.UserNotFoundException;
import traffic_id.demo.model.Moderator;
import traffic_id.demo.model.User;
import traffic_id.demo.model.UserData;
import traffic_id.demo.repository.ModeratorRepository;
import traffic_id.demo.repository.UserDataRepository;

@Service
@Transactional
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private FileService fileService;

    public User registerNewUserAccount(UserDto userDto, PasswordDto passwordDto) throws EmailAlreadyExistException, LoginAlreadyExistException, 
                                                                PasswordIncorrectException, UserNotFoundException {
        if (emailExists(userDto.getEmail())) {
            throw new EmailAlreadyExistException("Аккаунт с данной почтой уже существует: "
              + userDto.getEmail());
        }

        if (loginExists(userDto.getLogin())) {
            throw new LoginAlreadyExistException("Аккаунт с данным логином уже существует: "
              + userDto.getLogin());
        }

        if (!passwordDto.getPassword().equals(passwordDto.getMatchingPassword())) {
            throw new PasswordIncorrectException("Пароли не совпадают");
        }

        UserData userData = new UserData(null, userDto.getLogin(), bCryptPasswordEncoder.encode(passwordDto.getPassword()),
                                            userDto.getEmail(), null, "ROLE_USER");
        userDataRepository.save(userData);

        UserData addedData = userDataRepository.findByLogin(userDto.getLogin());
        
        User user = new User(null, userDto.getSurname(), userDto.getName(), userDto.getPatronymic(),
                            addedData, new Date());

        return userRepository.save(user);
    }

    public User editUserAccount(UserDto userDto, MultipartFile file) throws EmailAlreadyExistException, LoginAlreadyExistException, 
                                                            PasswordIncorrectException, UserNotFoundException {
        
        UserData userData = userDataRepository.findByLogin(userDto.getLogin());
        
        if (!userDto.getEmail().equals(userData.getEmail())) {
            if (emailExists(userDto.getEmail())) {
                throw new EmailAlreadyExistException("Аккаунт с данной почтой уже существует: "
                + userDto.getEmail());
            }
        }

        if (!userDto.getLogin().equals(userData.getLogin())) {
            if (loginExists(userDto.getLogin())) {
                throw new LoginAlreadyExistException("Аккаунт с данным логином уже существует: "
                + userDto.getLogin());
            }
        }

        if (userDto.getAvatar() == null)
            userData.setAvatar(null);

        if (file != null)
            userData.setAvatar(fileService.saveAvatarFile(file, userData.getId().toString()));
        
        userData.setEmail(userDto.getEmail());
        userData.setLogin(userDto.getLogin());
        userDataRepository.save(userData);
        
        User user = userRepository.findByData(userData).get();
        user.setName(userDto.getName());
        user.setSurname(userDto.getSurname());
        user.setPatronymic(userDto.getPatronymic());
        return userRepository.save(user);
    }

    private boolean emailExists(String email) {
        return userDataRepository.findByEmail(email) != null;
    }

    private boolean loginExists(String login) {
        return userDataRepository.findByLogin(login) != null;
    }

    public User findUserById(Integer userId) {
        Optional<User> userFromDb = userRepository.findById(userId);
        return userFromDb.orElse(null);
    }

    public User findUserByLogin(String login) {
        UserData userDataFromDb = userDataRepository.findByLogin(login);
        if (userDataFromDb == null)
            return null;
        
        return userDataFromDb.getUser();
    }

    public List<User> allUsers() {
        return userRepository.findAll();
    }

    public User getUser() {
        String userLogin = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return findUserByLogin(userLogin);
    }

    public Boolean isModeratorHasRole() {
        return (findModeratorByUser(getUser()) != null);
    }

    public Moderator findModeratorById(Integer moderatorId) {
        Optional<Moderator> moderatorFromDb = moderatorRepository.findById(moderatorId);
        return moderatorFromDb.orElse(null);
    }

    public Moderator findModeratorByUser(User user) {
        return moderatorRepository.findLastActive(user);
    }

    public List<Moderator> allModerators() {
        return moderatorRepository.findAll();
    }

    public boolean addModerator(Integer userId) {
        User user = findUserById(userId);
        if (findModeratorByUser(user) != null) return false;
        
        Moderator moderator = new Moderator(null, user, new Date(), null);
        moderatorRepository.save(moderator);
        user.getData().setRoles("ROLE_USER,ROLE_MODERATOR");

        return true;
    }

    public boolean removeModerator(Integer moderatorId) {
        Moderator moderator = findModeratorById(moderatorId);
        if (moderator == null) return false;
        
        moderator.setEndPost(new Date());
        moderator.getUser().getData().setRoles("ROLE_USER");
        
        return true;
    }

    public Resource loadAvatar(String fileName, String folder) {
        return fileService.loadAvatarFile(fileName, folder);
    }

    public String getAvatar(String path, String folder)
    {
        if (path == null || path == "")
            return null;
            
        return MvcUriComponentsBuilder.fromMethodName(UserController.class, "getAvatar", fileService.getFileName(path), folder)
                                        .build()
                                        .toString();
    }
}
