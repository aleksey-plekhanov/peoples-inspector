package traffic_id.demo.interfaces;

import traffic_id.demo.exceptions.EmailAlreadyExistException;
import traffic_id.demo.exceptions.LoginAlreadyExistException;
import traffic_id.demo.exceptions.PasswordIncorrectException;
import traffic_id.demo.exceptions.UserNotFoundException;
import traffic_id.demo.model.User;
import traffic_id.demo.service.UserDto;

public interface IUserService {
    User registerNewUserAccount(UserDto userDto) throws EmailAlreadyExistException, LoginAlreadyExistException, 
                                                        PasswordIncorrectException, UserNotFoundException;
}
