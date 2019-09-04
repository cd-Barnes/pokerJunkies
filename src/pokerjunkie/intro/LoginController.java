package pokerjunkie.intro;

import java.util.List;
import pokerjunkie.Controller;
import pokerjunkie.PokerJunkies;
import user.NewUser;
import user.User;


//
public class LoginController implements Controller {
    
    //
    private final String loginFailedMessage = "Incorrect password.";
    private final String chippyLoginFailedMessage = 
            "You wouldn't be trying to sneak into\n" + 
            "%s's account, would you?";
    
    //
    private final String invalidUsernameMessage = "Invalid username";
    private final String usernameTakenMessage = "Username taken";
    private final String passwordMismatchMessage = "Passwords do not match";
    
    //
    private LoginView view;
    
    //
    private NewUserView newUserView;
    
    
    //
    public LoginController() {
        view = new LoginView(this);
        
        List<String> users = PokerJunkies.getUserDAO().getAllUserNames();
        view.populateUserList(users);
    }
    
    
    //
    public void handleTryLogin(String username, String password) {        
        if(PokerJunkies.getUserDAO().attemptLogin(username, password)) {
            PokerJunkies.launchMainMenu();
        } else {
            view.showMessageDialog(loginFailedMessage);
            PokerJunkies.getChippy().makeChippyDissapointed();
            String chippyPhrase = String.format(chippyLoginFailedMessage, username);
            PokerJunkies.getChippy().sayPhrase(chippyPhrase);
        }
    }
    
    
    //
    public void handleStartNewUserView() {
        view.setNewUserButtonEnabled(false);
        
        newUserView = new NewUserView(this);
        newUserView.setVisible(true);
    }
    
    
    //
    public void handleCloseNewUserView() {
        if(newUserView != null) {
            newUserView.close();
            newUserView = null;
        }
        
        view.setNewUserButtonEnabled(true);
    }
    
    
    //
    public void handleRegisterNewUser(NewUser newUser) {
        if(handleRegistrationValidation(newUser)) {
            PokerJunkies.getUserDAO().saveUser(newUser.generateUser());
            handleCloseNewUserView();
            refreshUserList();
            view.setPlayerSelectionToLast();
        }
    }
    
    
    //
    public void handleUserSelected(String username) {
        User user = PokerJunkies.getUserDAO().getUserByName(username);
        
        if(user != null) {
            view.setUserDetails(user);
        } else {
            view.hideUserDetails();
        }
    }
    
    
    //
    private boolean handleRegistrationValidation(NewUser newUser) {
        if(!newUser.isNameValid()) {
            newUserView.showMessageDialog(invalidUsernameMessage);
            return false;
        } else if(!newUser.passwordsMatch()) {
            newUserView.showMessageDialog(passwordMismatchMessage);
            return false;
        } else if(PokerJunkies.getUserDAO().userExists(newUser.getName())) {
            newUserView.showMessageDialog(usernameTakenMessage);
            return false;
        } else {
            return true;
        }
    }
    
    
    //
    private void refreshUserList() {
        List<String> usernames = PokerJunkies.getUserDAO().getAllUserNames();
        view.populateUserList(usernames);
    }
    
    
    @Override
    public void onStart() {
        refreshUserList();
        view.setVisible(true);
    }
    

    @Override
    public void onEnd() {
        view.close();
        view = null;
    }
}
