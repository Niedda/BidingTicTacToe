
public class HardBrain extends SuperBrain {

	@Override
	public int getNextBid() {
		int score = 0;
		
		// Calculate the score value
		if(getFieldsToWinPlayer() ==  1) {
			score += 9;
		} else if(getFieldsToWinPlayer() == 2) {
			score += 2;
		}
		
		if(getFieldsToWinAi() == 1) {
			score += 9;
		} else if(getFieldsToWinAi() == 2) {
			score += 2;
		}
		
		if(Context.getContext().getAiCredits() < Context.getContext().getPlayerCredits()) {
			score += 2;
		} else if(Context.getContext().getAiCredits() > Context.getContext().getPlayerCredits()) {
			score += 4;
		}
		
		// Set the bet depending on the score value		
		if(score <= 4) {
			
		} else if(score <= 8) {
			
		} else {
			
		}
			
		
		
		return 3;
	}

	@Override
	public GameField getNextMove() {
		// TODO implement
		return new GameField(1,1);
	}

}
