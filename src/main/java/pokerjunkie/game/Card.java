package pokerjunkie.game;


public class Card {
    
    // Suit and Rank values are order critical, do not change.
    public enum Suit {HEARTS, DIAMONDS, SPADES, CLUBS};
    public enum Rank {TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, TEN, JACK, QUEEN, KING, ACE};
    
    private Suit suit;
    private Rank rank;
    
    
    //
    public Card(Suit suit, Rank rank) {
        this.suit = suit;
        this.rank = rank;
    }
    
    
    // Returns a new card based on the index.
    public Card(int index) {
        rank = Rank.values()[index % Rank.values().length];
        suit = Suit.values()[index / Rank.values().length];
    }
    
    
    //
    public Suit getSuit() {
        return suit;
    }
    
    
    //
    public Rank getRank() {
        return rank;
    }
    
    
    //
    public int getIndex() {
        return (suit.ordinal() * Rank.values().length) + rank.ordinal();
    }
    
    
    //
    public String getAbbreviation() {
        String a = "";
        
        switch(rank) {
            case ACE:
                a += "A";
                break;
            case TWO:
                a += "2";
                break;
            case THREE:
                a += "3";
                break;
            case FOUR:
                a += "4";
                break;
            case FIVE:
                a += "5";
                break;
            case SIX:
                a += "6";
                break;
            case SEVEN:
                a += "7";
                break;
            case EIGHT:
                a += "8";
                break;
            case NINE:
                a += "9";
                break;
            case TEN:
                a += "T";
            case JACK:
                a += "J";
                break;
            case QUEEN:
                a += "Q";
                break;
            case KING:
                a += "K";
                break;
        }
        
        switch(suit) {
            case HEARTS:
                a += "H";
                break;
            case DIAMONDS:
                a += "D";
                break;
            case SPADES:
                a += "S";
                break;
            case CLUBS:
                a += "C";
                break;
        }
        
        return a;
    }
    
    
    //
    public static int numSuits() {
        return Suit.values().length;
    }
    
    
    //
    public static int numRanks() {
        return Rank.values().length;
    }
}
