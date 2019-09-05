package User;

import java.io.Serializable;

public class Settings implements Serializable {

	public int getGameTimeLimit() {
            return gameTimeLimit;
	}

	public void setGameTimeLimit(int gameTimeLimit) {
            this.gameTimeLimit = gameTimeLimit;
	}

	private int gameTimeLimit;

	public int getHandTimeLimit() {
            return handTimeLimit;
	}

	public void setHandTimeLimit(int handTimeLimit) {
            this.handTimeLimit = handTimeLimit;
	}

	private int handTimeLimit;

	public int getBiggestBet() {
            return biggestBet;
	}

	public void setBiggestBet(int biggestBet) {
            this.biggestBet = biggestBet;
	}

	private int biggestBet;

	public int getSmallBlind() {
            return smallBlind;
	}

	public void setSmallBlind(int smallBlind) {
            this.smallBlind = smallBlind;
	}

	private int smallBlind;

	public int getBigBlind() {
            return bigBlind;
	}

	public void setBigBlind(int bigBlind) {
            this.bigBlind = bigBlind;
	}

	private int bigBlind;

	public int getStoryModeDifficulty() {
            return storyModeDifficulty;
	}

	public void setStoryModeDifficulty(int storyModeDifficulty) {
            this.storyModeDifficulty = storyModeDifficulty;
	}

	private int storyModeDifficulty;
	
	Settings()
	{
            gameTimeLimit = 2;
            handTimeLimit = 2;
            biggestBet = 100000;
            smallBlind = 0;
            bigBlind = 0;
            storyModeDifficulty = 2;
	}

	
}
