package pokerjunkie.intro;

import pokerjunkie.Controller;
import pokerjunkie.PokerJunkies;
import pokerjunkie.game.GameConfigurator;
import user.User;


//
public class MainMenuController implements Controller {
    
    //
    private User user;
    
    //
    private MainMenuView view;
    
    
    //
    public MainMenuController() {
        view = new MainMenuView(this);
        user = PokerJunkies.getUserDAO().getActiveUser();
    }
    
    
    //
    public void handleStartGame() {
        int numPlayers = view.getNumPlayers();
        int minimumBet = view.getMinimumBet();
        
        System.out.println(minimumBet);
        
        GameConfigurator gameConfig = new GameConfigurator(numPlayers, minimumBet, minimumBet);
        
        
        PokerJunkies.startGame(gameConfig);
    }

    public void handleStartTutorial() {

        GameConfigurator gameConfig = new GameConfigurator(true);

        PokerJunkies.startTutorial(gameConfig);
    }

    
    //
    @Override
    public void onEnd() {
        view.close();
        view = null;
    }

    
    //
    @Override
    public void onStart() {
        view.setUserDetails(user);
        view.setVisible(true);
        PokerJunkies.getChippy().greetUser();
    }
}
