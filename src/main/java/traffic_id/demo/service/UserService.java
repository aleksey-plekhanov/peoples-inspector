package traffic_id.demo.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import traffic_id.demo.repository.UserRepository;
import traffic_id.demo.exceptions.EmailAlreadyExistException;
import traffic_id.demo.exceptions.LoginAlreadyExistException;
import traffic_id.demo.exceptions.PasswordIncorrectException;
import traffic_id.demo.exceptions.UserNotFoundException;
import traffic_id.demo.interfaces.IUserService;
import traffic_id.demo.model.Moderator;
import traffic_id.demo.model.User;
import traffic_id.demo.model.UserData;
import traffic_id.demo.repository.ModeratorRepository;
import traffic_id.demo.repository.UserDataRepository;

@Service
@Transactional
public class UserService implements IUserService {
    @PersistenceContext
    private EntityManager em;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private ModeratorRepository moderatorRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
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

    @Override
    public User editUserAccount(UserDto userDto) throws EmailAlreadyExistException, LoginAlreadyExistException, 
                                                            PasswordIncorrectException, UserNotFoundException {
        
        UserData userData = userDataRepository.findByLogin(userDto.getLogin());
        
        if (!userDto.getEmail().equals(userData.getEmail()))
        {
            if (emailExists(userDto.getEmail())) {
                throw new EmailAlreadyExistException("Аккаунт с данной почтой уже существует: "
                + userDto.getEmail());
            }
        }

        if (!userDto.getLogin().equals(userData.getLogin()))
        {
            if (loginExists(userDto.getLogin())) {
                throw new LoginAlreadyExistException("Аккаунт с данным логином уже существует: "
                + userDto.getLogin());
            }
        }
        
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

    public Moderator findModeratorById(Integer moderatorId) {
        Optional<Moderator> moderatorFromDb = moderatorRepository.findById(moderatorId);
        return moderatorFromDb.orElse(null);
    }

    public Moderator findModeratorByUser(User user) {
        Optional<Moderator> moderatorFromDb = moderatorRepository.findLastActive(user);
        return moderatorFromDb.orElse(null);
    }

    public List<Moderator> allModerators() {
        return moderatorRepository.findAll();
    }

    public boolean addModerator(Integer userId) {
        Optional<User> user = userRepository.findById(userId);
        if (!user.isPresent()) return false;
        
        Optional<Moderator> t_moderator = moderatorRepository.findLastActive(user.get());
        if (t_moderator.isPresent()) return false;
        
        Moderator moderator = new Moderator(null, user.get(), new Date(), null);
        moderatorRepository.save(moderator);
        user.get().getData().setRoles("ROLE_MODERATOR");

        return true;
    }

    public boolean removeModerator(Integer moderatorId) {
        Optional<Moderator> moderator = moderatorRepository.findById(moderatorId);
        if (!moderator.isPresent()) return false;
        
        moderator.get().setEnd_post(new Date());
        moderator.get().getUser().getData().setRoles("ROLE_USER");
        
        return true;
    }
}
