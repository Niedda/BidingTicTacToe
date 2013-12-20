package nif.tictactoe.model;

import java.util.Random;

public class PokerBettingStrategy implements IStrategy {

	@Override
	public int getNextBet(int fieldsToWinPlayer, int fieldsToWinAi) {
		if(fieldsToWinPlayer != 1) {
			int rnd = new Random().nextInt(2);
			
			if(rnd == 0) {
				return 1;
			}
			if(rnd == 1){
				return 2;
			}
		}
		return _backupStrategy.getNextBet(fieldsToWinPlayer, fieldsToWinAi);		
	}
	
	private IStrategy _backupStrategy = new ModerateBettingStrategy();
}
