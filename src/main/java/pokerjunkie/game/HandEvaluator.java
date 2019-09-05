package pokerjunkie.game;

import java.util.ArrayList;
import java.util.List;

public class HandEvaluator {


    public static enum HAND_RANK {
        HIGH_CARD,
        PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH,
        ROYAL_FLUSH
    }

    private GameModel model;

    public HandEvaluator(GameModel model) {
        this.model = model;
    }


    public HAND_RANK sortAndRankPlayerHand(int player, List<Card> shared) {
        List<Card> hand = model.getPlayerHand(player);
        List<Card> tempHand = new ArrayList<Card>();
        tempHand.addAll(hand);
        tempHand.addAll(shared);
        //Collections.sort(tempHand);


        HAND_RANK handRank = HAND_RANK.HIGH_CARD;

        //Check how many cards have matching ranks and determine possible hands
        int firstMatchingIndex = -1;
        for(int i = 1; i < hand.size(); i++) {
            Card previousCard = hand.get(i-1);
            Card currentCard = hand.get(i);

            if(currentCard.getRank() == previousCard.getRank()) {
                if(handRank == HAND_RANK.HIGH_CARD) {
                    handRank = HAND_RANK.PAIR;
                    firstMatchingIndex = i - 1;

                } else if(handRank == HAND_RANK.PAIR) {
                    if(firstMatchingIndex == i - 2) {
                        handRank = HAND_RANK.THREE_OF_A_KIND;
                    } else if(firstMatchingIndex == i - 3) {
                        handRank = handRank.TWO_PAIR;
                    }

                } else if(handRank == HAND_RANK.THREE_OF_A_KIND) {
                    if(firstMatchingIndex == i - 3) {
                        handRank = HAND_RANK.FOUR_OF_A_KIND;
                    } else if(firstMatchingIndex == i - 4) {
                        handRank = handRank.FULL_HOUSE;
                    }

                } else if(handRank == HAND_RANK.TWO_PAIR) {
                    handRank = handRank.FULL_HOUSE;
                }
            }
        }

        //If there are cards with matching ranks, move them to the front of the hand
        //Else, keep evaluating
        if(firstMatchingIndex > -1) {
            List<Card> tempCards = new ArrayList();
            if(handRank == HAND_RANK.PAIR) {
                tempCards.add(hand.get(firstMatchingIndex));
                tempCards.add(hand.get(firstMatchingIndex + 1));
                hand.remove(firstMatchingIndex);
                hand.remove(firstMatchingIndex);
                hand.add(0, tempCards.get(0));
                hand.add(1, tempCards.get(1));
            } else if(handRank == HAND_RANK.TWO_PAIR) {
                if(firstMatchingIndex == 1) {
                    tempCards.add(hand.get(0));
                    hand.remove(0);
                    hand.add(tempCards.get(0));
                } else if(hand.get(2).getRank() != hand.get(3).getRank()) {
                    tempCards.add(hand.get(2));
                    hand.remove(2);
                    hand.add(tempCards.get(0));
                }
            } else if(handRank == HAND_RANK.THREE_OF_A_KIND) {
                tempCards.add(hand.get(firstMatchingIndex));
                tempCards.add(hand.get(firstMatchingIndex + 1));
                tempCards.add(hand.get(firstMatchingIndex + 2));
                hand.remove(firstMatchingIndex);
                hand.remove(firstMatchingIndex);
                hand.remove(firstMatchingIndex);
                hand.add(0, tempCards.get(0));
                hand.add(1, tempCards.get(1));
                hand.add(2, tempCards.get(2));
            }
            else if(handRank == HAND_RANK.FOUR_OF_A_KIND) {
                if(firstMatchingIndex == 1) {
                    tempCards.add(hand.get(0));
                    hand.remove(0);
                    hand.add(tempCards.get(0));
                }
            } else if(handRank == HAND_RANK.FULL_HOUSE) {
                //if the pair of two is before the three then put the three first
                if(hand.get(0).getRank() != hand.get(2).getRank()) {
                    //Just takes the first card and adds it to the end twice
                    hand.add(hand.get(0));
                    hand.remove(0);
                    hand.add(hand.get(0));
                    hand.remove(0);
                }
            }
        } else {
            //Check to see if all of the suits match
            boolean isFlush = true;
            Card.Suit firstSuit = hand.get(0).getSuit();
            for(int i = 1; i < hand.size() && isFlush; i++) {
                if(hand.get(i).getSuit() != firstSuit) {
                    isFlush = false;
                }
            }

            //Check to see if a straight exists
            boolean isStraight = true;
            Card.Rank previousRank = hand.get(0).getRank();
            for(int i = 1; i < hand.size() && isStraight; i++) {
                Card.Rank currentRank = hand.get(i).getRank();
                if(currentRank.ordinal() != previousRank.ordinal() - 1) {
                    isStraight = false;
                }
            }
            //Check for the special case of a straight where ace is low
            if(hand.get(0).getRank() == Card.Rank.ACE && !isStraight) {
                isStraight = true;
                for(int i = 1; i < hand.size(); i++) {
                    if(hand.get(i).getRank().ordinal() != hand.size() + 1 - i) {
                        isStraight = false;
                    }
                }
            }

            if(isStraight && isFlush) {
                if(hand.get(0).getRank() == Card.Rank.ACE
                        && hand.get(1).getRank() == Card.Rank.KING) {
                    handRank = HAND_RANK.ROYAL_FLUSH;
                } else {
                    handRank = HAND_RANK.STRAIGHT_FLUSH;
                }
            } else if(isStraight) {
                handRank = HAND_RANK.STRAIGHT;
            } else if(isFlush) {
                handRank = HAND_RANK.FLUSH;
            }
        }

        return handRank;
    }


    public static String getHandRankName(HAND_RANK handRank) {

        switch(handRank) {
            case PAIR :
                return "PAIR";
            case TWO_PAIR :
                return "TWO PAIR";
            case THREE_OF_A_KIND :
                return "THREE OF A KIND";
            case STRAIGHT :
                return "STRAIGHT";
            case FLUSH :
                return "FLUSH";
            case FULL_HOUSE :
                return "FULL HOUSE";
            case FOUR_OF_A_KIND :
                return "FOUR OF A KIND";
            case STRAIGHT_FLUSH :
                return "STRAIGHT FLUSH";
            case ROYAL_FLUSH :
                return "ROYAL FLUSH";
            default :
                return "HIGH CARD";
        }
    }

    //Scores hands based on hand rank, then by the next highest cards etc...
    /* Scoring:
     * Lowest card is worth from 0 to 12 pts (*1)
     * Next lowest card is worth 13 to (13^2 - 1) pts (*13)
     * etc . . .
     * Hand Rank is worth 13^6 * RANK pts
     */

    public int scoreHand(int player, List<Card> sharedCards) {
        HAND_RANK handRank = sortAndRankPlayerHand(player, sharedCards);

        int score = 0;
        //13 possible card ranks, so using powers of 13 is convenient for scoring
        //number of hand ranks is < 13 so we can still use 13 as a base
        score += handRank.ordinal() * Math.pow(13, 6);

        List<Card> hand = model.getPlayerHand(player);

        List<Card> tempHand = new ArrayList<Card>();
        tempHand.addAll(hand);
        tempHand.addAll(sharedCards);

        int trailingCardsToBeScored = 0;
        switch(handRank) {
            case HIGH_CARD :
                trailingCardsToBeScored = 5;
                break;
            case PAIR :
                score += tempHand.get(0).getRank().ordinal() * Math.pow(13, 5);
                trailingCardsToBeScored = 3;
                break;
            case TWO_PAIR :
                score += tempHand.get(0).getRank().ordinal() * Math.pow(13, 5);
                score += tempHand.get(2).getRank().ordinal() * Math.pow(13, 4);
                trailingCardsToBeScored = 1;
                break;
            case THREE_OF_A_KIND :
                score += tempHand.get(0).getRank().ordinal() * Math.pow(13, 5);
                trailingCardsToBeScored = 2;
                break;
            case STRAIGHT :
                score += tempHand.get(0).getRank().ordinal() * Math.pow(13, 5);
                break;
            case FLUSH :
                score += tempHand.get(0).getRank().ordinal() * Math.pow(13, 5);
                break;
            case FULL_HOUSE :
                score += tempHand.get(0).getRank().ordinal() * Math.pow(13, 5);
                score += tempHand.get(4).getRank().ordinal() * Math.pow(13, 4);
                break;
            case FOUR_OF_A_KIND :
                score += tempHand.get(0).getRank().ordinal() * Math.pow(13, 5);
                trailingCardsToBeScored = 1;
                break;
            case STRAIGHT_FLUSH :
                score += tempHand.get(0).getRank().ordinal() * Math.pow(13, 5);
                break;
            case ROYAL_FLUSH :
                score += tempHand.get(0).getRank().ordinal() * Math.pow(13, 5);
                break;
        }

        for(int i = tempHand.size() - trailingCardsToBeScored; i < tempHand.size(); i++) {
            score += tempHand.get(i).getRank().ordinal() * Math.pow(13, tempHand.size() - i);
        }
        return score;
    }
}
