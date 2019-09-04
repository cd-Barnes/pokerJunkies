package pokerjunkie.game;

import user.User;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;
    private int balance;
    private List<Card> cards;

    
    //
    public Player(User user) {
        this.name = user.getName();
        this.balance = user.getBalance();
        cards = new ArrayList();
    }
    
    
    //
    public Player(String name, int balance) {
        this.name = name;
        this.balance = balance;
        cards = new ArrayList();
    }
    
    
    //
    public String getName() {
        return name;
    }
    
    
    //
    public int getBalance() {
        return balance;
    }


    //
    public void setBalance(int balance) {
        this.balance = balance;
    }
    
    
    //
    public List<Card> getCards() {
        return cards;
    }
    
    
    //
    public void clearCards() {
        cards = new ArrayList<>();
    }
    
    
    //
    public void addCard(Card card) {
        cards.add(card);
    }
}
