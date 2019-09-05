package user;

import java.util.List;


public interface UserDAO {
    
    List<String> getAllUserNames();
    
    User getUserByName(String name);
    
    boolean userExists(String name);
    
    User getActiveUser();
    
    void saveUser(User user);
    
    boolean attemptLogin(String username, String password);
}
