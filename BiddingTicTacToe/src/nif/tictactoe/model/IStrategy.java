package nif.tictactoe.model;

public interface IStrategy {

	int getNextBet(int fieldsToWinPlayer, int fieldsToWinAi);
	
}
