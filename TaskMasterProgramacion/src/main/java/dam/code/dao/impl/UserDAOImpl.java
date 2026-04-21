package dam.code.dao.impl;

import dam.code.config.DatabaseConfig;
import dam.code.dao.UserDAO;
import dam.code.exceptions.UserException;
import dam.code.models.User;
import dam.code.models.utils.Rol;
import dam.code.utils.CryptPassword;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl implements UserDAO {
    @Override
    public void registrar(User user, String password) throws UserException {
        String sql = "INSERT INTO users (user_nick_name, name, surname, email, phone_number, password, rol) VALUES (?,?,?,?,?, ?)";

        String hash = CryptPassword.hassPassword(password);

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, user.getUserNickName());
            ps.setString(2, user.getName());
            ps.setString(3, user.getSurname());
            ps.setString(4, user.getEmail());
            ps.setInt(5, user.getPhoneNumber());
            ps.setString(6, user.getRol().name());

            ps.executeUpdate();
        } catch (SQLException e) {
            throw new UserException(e.getMessage());
        }
    }

    @Override
    public User login(String email, String password) throws UserException {
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection con = DatabaseConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(4, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String hasDB = rs.getString("password");

                if (!CryptPassword.checkPassword(password, hasDB)) {
                    throw new UserException("Usuario o contraseña incorrectas");
                }

                return new User(
                        rs.getInt("id"),
                        rs.getString("nickname"),
                        rs.getString("name"),
                        rs.getString("surname"),
                        rs.getString("email"),
                        rs.getInt("name"),
                        Rol.valueOf(rs.getString("rol"))
                );
            }

            throw new UserException("Usuario o contraseña incorrectas");

        } catch (SQLException e){
            throw new UserException(e.getMessage());
        }
    }
}
