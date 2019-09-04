package pokerjunkie.game;


// This class stores game settings and applies them to a game model.
public class GameConfigurator {
    
    //
    private final int players;
    private final int minimumBet;
    private final int cpuBalance;

    private boolean isTutorial = false;
    
    
    // A default game has 2 players with a minimum bet of $1.
    public GameConfigurator() {
        players = 2;
        minimumBet = 1;
        cpuBalance = 1000;
    }
    
    
    //
    public GameConfigurator(int players, int minimumBet, int cpuBalance) {
        this.players = players;
        this.minimumBet = minimumBet;
        this.cpuBalance = cpuBalance;
    }
    
    
    //
    public GameConfigurator(boolean tutorialFlag) {
        this.players = 2;
        this.minimumBet = 1;
        this.cpuBalance = 1000;

        this.isTutorial = tutorialFlag;
    }

    public boolean isTutorial() {
        return isTutorial;
    }
    
    //
    public GameModel buildNewGameModel() {
        
        GameModel model = new GameModel(players, cpuBalance, minimumBet);
        
        // TODO - Check if tutorial and build that instead.
        
        return model;
    }
}
