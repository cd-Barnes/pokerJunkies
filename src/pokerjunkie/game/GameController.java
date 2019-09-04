package pokerjunkie.game;

import pokerjunkie.Controller;
import pokerjunkie.PokerJunkies;

import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;


public class GameController implements Controller {
    
    //
    private GameView view;
    private GameModel model;
    
    //
    private final int dealCardDelay = 300;

    
    //
    public GameController(GameConfigurator gameConfig) {
        view = new GameView(this);
        model = gameConfig.buildNewGameModel();
    }
    
    
    //
    public void initView() {
        view.setNumComputerPlayers(model.getNumPlayers() - 1);
        
        view.setUserName(model.getPlayerName(GameModel.USER_PLAYER));
        view.setUserBalance(model.getPlayerBalance(GameModel.USER_PLAYER));
        view.setPotBalance(0);
        
        for(int i = 1; i < model.getNumPlayers(); i++) {
            view.setPlayerName(i - 1, model.getPlayerName(i));
            view.setPlayerBalance(i - 1, model.getPlayerBalance(i));
        }
        
        // TODO - Tell view which deck images to use.
    }
    
    
    /*
    //
    public void onGameStart() {
        dealRound();
    }
    
    
    public void callWinner() {

        HandEvaluator evaluator = new HandEvaluator(model);

        List<String> names = model.getPlayerNames();

        int userScore = evaluator.scoreHand(0, model.getTableCards());

        int AIScore = evaluator.scoreHand(1, model.getTableCards());

        if (userScore > AIScore) {
            //view.congratulate(names.get(0));
            model.adjustPlayerBalance(0, model.getPot());
            model.setPot(0);
        } else {
            //view.congratulate(names.get(1));
            model.adjustPlayerBalance(1, model.getPot());
            model.setPot(0);
        }
    }

    //setflopcards
    public void getFlopCards(int round) {
        if (round == 1) {
            List<Card> flop = model.dealFlop(3);
            //view.setFlop(flop, round);
        } else {
            List<Card> flop = model.dealFlop(1);
            //view.setFlop(flop, round);
        }

    }

    public int getRound() {
        return this.model.getRound();
    }

    public void advanceRound() {
        int currentRound = this.model.getRound();
        this.model.setRound(currentRound += 1);
    }

    public void resetRound() {
        this.model.setRound(0);
    }

    //if round != 0 deal call
    public void dealRound() {
        System.out.println("dealing cards");
        view.setDealer(model.getPlayerTurn());
        model.dealCards(2);

        List<String> names = model.getPlayerNames();

        for (int i = 0; i < names.size(); i++) {
            if (names.get(i).equals("Chippy")) {
                //is computer player
                //view.setComputerCards(model.getPlayerHand((i)));
            }
            else {
                //view.setUserCards(model.getPlayerHand(i));
            }
        }

        // TODO - Big blind and little blind.
    }

    
    public int getUpdatedBalance(int player) {
        return model.getBalance(player);
    }
    
    
    //maybe for now just take the bet and run an AI turn
    public int takeNextBet(int bet) {
        // TODO - If it is the user's turn, wait tell view to notify them.
        
        // TODO - If it is someone else's turn, notify the view. Then, tell model to make the CPU bet.
        
        // TODO - Once bets are done, do winner stuff and deal next round.

        Random rand = new Random();

        int randomNum = 0;

        //user
        if (model.getBalance(0) - bet > 0) {
            model.addToPot(bet);

            //substract from balance
            model.adjustPlayerBalance(0, bet * -1);
        }

        if (model.getBalance(1) - bet > 0) {
            //AI turn
            randomNum = rand.nextInt(200) + 1;

            model.addToPot(randomNum);

            model.adjustPlayerBalance(1, randomNum * -1);
        }



        return model.getPot();
    }
    */
    
    
    //
    public void handleUserBet() {
        if(model.getPlayerTurn() == GameModel.USER_PLAYER) {

            int bet = view.getUserBet();
            
            if(bet == model.getPlayerBalance(GameModel.USER_PLAYER)) {
                PokerJunkies.getChippy().sayPhrase("Now THAT'S a large bet!");
            } else {
                PokerJunkies.getChippy().sayRandomPhrase();
            }
            
            model.addBet(bet, GameModel.USER_PLAYER);
            view.setPotBalance(bet);
            
            view.setUserBalance(model.getPlayerBalance(GameModel.USER_PLAYER));
            view.setBettingAmounts(0, 0, 0);
            
            nextPlayerTurn();
            
        }
    }
    
    
    //
    public void handleUserFold() {
        if(model.getPlayerTurn() == GameModel.USER_PLAYER) {
            PokerJunkies.getChippy().makeChippyDissapointed();
            PokerJunkies.getChippy().sayPhrase("Shoulda bet large...");
            
            model.setPlayerFolded(GameModel.USER_PLAYER);
            
            nextPlayerTurn();
        }
    }
    
    
    //
    public void handleStartRound() {
        model.startNewRound();
        model.dealCards();
        view.clearChips();
        view.clearTable();
        updateChips();
        updateBettingAmounts();
        
        view.setActivePlayer(model.getPlayerTurn() - 1);
        
        Thread dealCardsAnimationThread = new Thread(new DealCardsController());
        dealCardsAnimationThread.start();
        // TODO - Disable user controls except for quit when it's not user's turn.
        
        nextPlayerTurn();
        // TODO - Deal cards, do cpu player bets until player turn.
    }
    
    
    //
    private void nextPlayerTurn() {
        model.incrementPlayerTurn();
        view.setActivePlayer(model.getPlayerTurn() - 1);
        
        if(model.getLastBettingPlayer() != model.getPlayerTurn()) {
            nextPhase();
        }
        
        while(model.getPlayerTurn() != GameModel.USER_PLAYER) {
            model.incrementPlayerTurn();
        }
    }
    
    
    //
    private void nextPhase() {
        model.incrementGamePhase();
        
        if(!model.getGamePhase().equals(GameModel.GamePhase.DONE)) {
            Thread cardTurnThread = new Thread(new CardTurnController());
            cardTurnThread.start();
        }
    }
    
    
    //
    private void updateChips() {
        view.clearChips();
        view.setDealer(model.getDealer() - 1);
        view.setLittleBlind(model.getLittleBlind() - 1);
        
        if(model.getNumPlayers() > 2) {
            view.setLittleBlind(model.getLittleBlind() - 1);
        }
    }
    
    
    //
    private void updateBettingAmounts() {
        int minimumBet = 0;
        if(model.getBigBlind() == GameModel.USER_PLAYER) {
            minimumBet = 2 * model.getMinimumBet();
        } else if (model.getLittleBlind() == GameModel.USER_PLAYER) {
            minimumBet = model.getMinimumBet();
        }
        
        view.setBettingAmounts(minimumBet, model.getPlayerBalance(GameModel.USER_PLAYER), minimumBet);
    }
    
    
    //
    public void returnToMainMenu() {
        PokerJunkies.launchMainMenu();
    }
    
    
    //
    @Override
    public void onStart() {
        initView();
        view.setVisible(true);
    }

    
    //
    @Override
    public void onEnd() {
        // TODO
        view.close();
        view = null;
    }
    
    
    //
    private class CardTurnController implements Runnable {

        @Override
        public void run() {
            
            try {
                Thread.sleep(dealCardDelay * 2);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            int card1 = 0;
            int card2 = 0;
            
            System.out.println(model.getGamePhase().toString());
            
            switch(model.getGamePhase()) {
                case FLOP:
                    card1 = 0;
                    card2 = 1;
                    break;
                case TURN:
                    card1 = 2;
                    card2 = 3;
                    break;
                case RIVER:
                    card1 = 4;
                    card2 = -1;
            }
            
            view.setTableCard(card1, model.getTableCards().get(card1));
            
            try {
                Thread.sleep(dealCardDelay);
            } catch (InterruptedException ex) {
                Logger.getLogger(GameController.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            if(card2 != -1) {
                view.setTableCard(card1, model.getTableCards().get(card1));
            }
        }
        
    }
    
    
    //
    private class DealCardsController implements Runnable {

        @Override
        public void run() {
            int playerTurn = model.getPlayerTurn();
            
            try {
                Thread.sleep(dealCardDelay * 2);
            } catch (InterruptedException ex) {
                System.err.println(ex.getMessage());
            }
            
            for(int i = 0; i < model.getNumPlayers(); i++) {
                int p = (playerTurn + i) % model.getNumPlayers();
                
                for(int c = 0; c < 2; c++) {
                    
                    List<Card> cards = model.getPlayerHand(p);
                    
                    if(p == GameModel.USER_PLAYER) {
                        view.setUserCard(c, cards.get(c));
                    } else {
                        view.setPlayerCard(p - 1, c, cards.get(c), true);
                    }
                    
                    try {
                        Thread.sleep(dealCardDelay);
                    } catch (InterruptedException ex) {
                        System.err.println(ex.getMessage());
                    }
                }
            }
            
        }
    }
    
}
