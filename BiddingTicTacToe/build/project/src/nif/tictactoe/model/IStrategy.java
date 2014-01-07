package nif.tictactoe.model;

public interface IStrategy {

	/*
	 * Get the next bet from the strategy.
	 */
	int getNextBet(int fieldsToWinPlayer, int fieldsToWinAi);
	
}
