package nif.tictactoe.model;

import java.util.Random;

import nif.tictactoe.Context;
import nif.tictactoe.SuperBrain;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: The brain for playing easy games versus the computer. 
 */
public class EasyBrain extends SuperBrain {

	@Override
	public int getNextBid() {
		//TODO: Implement basic skills.
		if(Context.getContext().getAiCredits() == 0) {
			return 0;
		}
		Random i = new Random();
		int j = i.nextInt(Context.getContext().getAiCredits()) + 1;
		return j;
	}

	@Override
	public GameField getNextMove() {
		//TODO: Implement basic skills.
		return new GameField(0, 0);
	}
}