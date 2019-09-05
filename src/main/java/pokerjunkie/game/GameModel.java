package pokerjunkie.game;

import pokerjunkie.PokerJunkies;
import user.User;

import java.util.ArrayList;
import java.util.List;


// All data and methods related to the game state.
public class GameModel {
    
    public static enum GamePhase {DEAL, FLOP, TURN, RIVER, DONE};
    
    //
    public static int USER_PLAYER = 0;
    
    //
    private GamePhase gamePhase;
    
    //
    private List<Player> players;
    private int dealer;
    private int playerTurn;
    private int lastBettingPlayer;
    private List<Player> foldedPlayers;
    
    //
    private Deck deck;
    private List<Card> tableCards;

    //
    private static int potBalance;
    
    //
    private int minimumBet;

    // private boolean isTutorial = false;

    
    //
    public GameModel(int numPlayers, int cpuBalance, int minimumBet) {
        this.minimumBet = minimumBet;
        players = new ArrayList();
        tableCards = new ArrayList();
        foldedPlayers = new ArrayList();
        
        players.add(new Player(PokerJunkies.getUserDAO().getActiveUser()));
        
        for(int i = 1; i < numPlayers; i++) {
            String cpuName = "P_" + (i);
            players.add(new Player(cpuName, cpuBalance));
        }
        
        dealer = -1;
        startNewRound();
    }
    
    
    //
    public void startNewRound() {
        potBalance = 0;
        tableCards.clear();
        foldedPlayers.clear();
        
        for(int i = 0; i < players.size(); i++) {
            players.get(i).clearCards();
        }
        
        dealer++;
        dealer %= players.size();
        
        playerTurn = getLittleBlind();
        lastBettingPlayer = -1;
        
        gamePhase = GamePhase.DEAL;
    }
    
    
    //
    public int getDealer() {
        return dealer;
    }
    
    
    //
    public int getLittleBlind() {
        return (dealer + 1) % players.size();
    }
    
    
    //
    public int getBigBlind() {
        return (dealer + 2) % players.size();
    }
    
    
    /*
    //set up a tutorial game
    public GameModel(boolean tutorialFlag) {
        players = new ArrayList();

        // TODO - Get user info here to init user player, then make the rest bot users.

        List<String> playerNames = PokerJunkies.getUserDAO().getAllUserNames();

        //temporary while we can only do 1 player
        User user = PokerJunkies.getUserDAO().getUserByName(playerNames.get(0));

        // Regardless of the number of players, the user is always added first.
        players.add(new Player(user));
        players.add(new Player());

        playerTurn = 0;

        //dealer is always chippy?
        dealer = 0;

        deck = new Deck();

        pot = 0;

        isTutorial = tutorialFlag;
    }
    */
    
    
    //
    public void addBet(int bet, int playerIndex) {
        if(bet > 0) {
            potBalance += bet;
            lastBettingPlayer = playerIndex;
            
            Player player = players.get(playerIndex);
            
            player.setBalance(player.getBalance() - bet);
        }
    }
    
    
    //
    public void setPlayerFolded(int player) {
        foldedPlayers.add(players.get(player));
    }
    
    
    //
    public boolean isPlayerFolded(int player) {
        return foldedPlayers.contains(foldedPlayers.get(player));
    }
    
    
    //
    public void incrementPlayerTurn() {
        playerTurn++;
        playerTurn %= players.size();
    }
    
    
    //
    public void incrementGamePhase() {
        int phase = gamePhase.ordinal();
        phase++;
        gamePhase = GamePhase.values()[phase];
    }
    
    
    //
    public GamePhase getGamePhase() {
        return gamePhase;
    }

    
    //
    public int getPotBalance() {
        return potBalance;
    }
    
    
    //
    public int getLastBettingPlayer() {
        return lastBettingPlayer;
    }

    
    //
    public List<Card> getTableCards() {
        return tableCards;
    }
    
    
    //
    public void dealCards() {
        deck = new Deck();
        
        // Discard current cards (if any)
        for(Player player : players) {
            player.clearCards();
        }
        // Deal new cards.
        for(Player player : players) {
            player.addCard(deck.drawCard());
            player.addCard(deck.drawCard());
        }
        
        tableCards.clear();
        // Maybe move this if time
        for(int c = 0; c < 5; c++) {
            tableCards.add(deck.drawCard());
        }
    }
    
    
    //
    public String getPlayerName(int player) {
        return players.get(player).getName();
    }
    
    
    //
    public List<Card> getPlayerHand(int player) {
        return players.get(player).getCards();
    }
    

    //
    public int getPlayerBalance(int player) {
        return players.get(player).getBalance();
    }
    
    
    //
    public void setPlayerBalance(int player, int balance) {
        players.get(player).setBalance(balance);
    }
    
    
    //
    public int getPlayerTurn() {
        return playerTurn;
    }
    
    
    //
    public int getNumPlayers() {
        return players.size();
    }
    
    
    //
    public int getMinimumBet() {
        return minimumBet;
    }
}
