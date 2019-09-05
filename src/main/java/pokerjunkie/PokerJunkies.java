package pokerjunkie;

import chippy.ChippyController;
import java.io.IOException;

import pokerjunkie.game.*;
import pokerjunkie.intro.LoginController;
import pokerjunkie.intro.MainMenuController;
import pokerjunkie.intro.SplashController;
import user.LocalUserDAO;
import user.UserDAO;


// Main class that launches the game and manages screens.
public class PokerJunkies {

    private static Controller controller;
    
    private static UserDAO userDAO;
    
    private static ChippyController chippyController;

    //
    public static void main(String[] args) throws IOException {
        
        /*
        GameView test = new GameView(new GameController());
        
        test.setDealer(-1);
        test.setLittleBlind(0);
        test.setBigBlind(1);
        
        test.setUserName("B");
        test.setUserBalance(257);
        test.setUserCard(0, new Card(Card.Suit.SPADES, Card.Rank.ACE));
        test.setUserCard(1, new Card(12));
        
        Card card = new Card(5);
        
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 2; j++) {
                test.setPlayerCard(i, j, card, true);
            }
        }
        
        test.setPlayerBalance(0, 109);
        test.setPlayerBalance(1, 33);
        test.setPlayerBalance(2, 46);
        
        test.setTableCard(0, new Card(11));
        test.setTableCard(1, new Card(43));
        
        test.setVisible(true);
        */
        
        try {
            launchSplash();
        } catch(InterruptedException e) {
            System.err.println(e.getMessage());
            System.exit(1);
        }
        
    }
    
    
    // Initializes Chippy and loads local user profiles (TODO).
    public static void launchSplash() throws InterruptedException {
        
        SplashController splashController = new SplashController();
        controller = splashController;
        controller.onStart();
        
        splashController.setLoadingMessage("Initializing Chippy . . .");
        chippyController = new ChippyController();
        chippyController.onStart();
        splashController.setLoadingMessage("Loading saved users . . .");
        userDAO = new LocalUserDAO("");
        Thread.sleep(1000);
        
        splashController.onLoadingFinished();
    }
    
    
    //
    public static void launchLogin() {
        Controller loginController = new LoginController();
        controller.onEnd();
        controller = loginController;
        controller.onStart();
    }
    
    
    //
    public static void launchMainMenu() {
        Controller mainMenuController = new MainMenuController();
        controller.onEnd();
        controller = mainMenuController;
        controller.onStart();
    }

    
    //
    public static void startGame(GameConfigurator gameConfig) {
        Controller gameController = new GameController(gameConfig);
        controller.onEnd();
        controller = gameController;
        controller.onStart();
    }

    public static void startTutorial(GameConfigurator gameConfig) {
        Controller gameController = new GameController(gameConfig);
        controller.onEnd();
        controller = gameController;
        controller.onStart();
    }
    
    //
    public static UserDAO getUserDAO() {
        return userDAO;
    }
    
    
    //
    public static ChippyController getChippy() {
        return chippyController;
    }
}
