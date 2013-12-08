package nif.tictactoe.model;

import nif.tictactoe.Context;

public class AggressiveBettingStrategy implements IStrategy {

	private int _move;
	
	@Override
	public int getNextBet(int fieldsToWinPlayer, int fieldsToWinAi) {
		Context context = Context.getContext();
		int aiCredits = context.getAiCredits();
		int playerCredits = context.getPlayerCredits();
		_move++;

		if (_move % 2 == 1) {
			return 1;
		} else if (fieldsToWinAi == 1) {
			return aiCredits;
		}

		if (4 > aiCredits) {
			return aiCredits;
		}

		if (4 > playerCredits) {
			return playerCredits + 1;
		}

		return 4;
	}
}
