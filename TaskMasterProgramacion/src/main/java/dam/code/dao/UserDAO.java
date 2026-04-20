package dam.code.dao;

import dam.code.exceptions.UserException;
import dam.code.models.User;

public interface UserDAO {
    void registrar (User user, String password) throws UserException;
    User login (String dni, String password) throws UserException;
}
