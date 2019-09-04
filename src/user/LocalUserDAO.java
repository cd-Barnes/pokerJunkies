package user;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


// Handles all local user saving and loading.
public class LocalUserDAO implements UserDAO {
    
    private final String userSavePath = "saves/";
    
    
    private Map<String, User> usersByName;
    
    private User activeUser;
    
    
    //
    public LocalUserDAO(String filepath) {
        usersByName = new HashMap();
        
        try {
            loadSaves();
        } catch(IOException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        
    }
    
    
    //
    @Override
    public List<String> getAllUserNames() {
        List<String> names = new ArrayList();
        for(String name : usersByName.keySet()) {
            names.add(name);
        }
        Collections.sort(names);
        return names;
    }

    
    //
    @Override
    public User getUserByName(String name) {
        return usersByName.get(name);
    }
    
    
    //
    @Override
    public boolean userExists(String name) {
        return usersByName.containsKey(name);
    }

    
    //
    @Override
    public void saveUser(User user) {
        usersByName.put(user.getName(), user);
        
        String filePath = userSavePath + user.getName() + ".pkj";
        
        try {
            
            File userSaveFile = new File(filePath);
            
            if(userSaveFile.exists()) {
                userSaveFile.delete();
            }
            
            FileOutputStream fileOut = new FileOutputStream(filePath);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(user);
            objectOut.close();
            
        } catch (Exception ex) {
            System.err.println("Failed to save user " + user.getName());
        }
    }
    
    
    //
    @Override
    public User getActiveUser() {
        return activeUser;
    }
    
    
    //
    @Override
    public boolean attemptLogin(String username, String password) {
        if(usersByName.containsKey(username)) {
            User loginUser = usersByName.get(username);
            
            if(loginUser.isPassword(password)) {
                activeUser = loginUser;
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }
    
    
    //
    private void loadSaves() throws IOException {
        File saveFolder = new File(userSavePath);
        
        if(saveFolder.exists()) {
            
            File[] saves = saveFolder.listFiles();
            
            for(int i = 0; i < saves.length; i++) {
                FileInputStream fileIn = new FileInputStream(saves[i].getPath());
                ObjectInputStream objectIn = new ObjectInputStream(fileIn);
                try {
                    User user = (User) objectIn.readObject();
                    objectIn.close();
                    usersByName.put(user.getName(), user);
                } catch (ClassNotFoundException ex) {
                    System.err.println("Failed to load user from " + saves[i].getPath());
                }
            }
            
        } else {
            saveFolder.mkdir();
        }
    }
}
