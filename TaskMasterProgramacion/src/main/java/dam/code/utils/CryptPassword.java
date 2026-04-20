package dam.code.utils;

import org.mindrot.jbcrypt.BCrypt;

public class CryptPassword {
    public static String hassPassword(String password){
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    public static boolean checkPassword(String password, String hashedPassword){
        return BCrypt.checkpw(password, hashedPassword);
    }
}
