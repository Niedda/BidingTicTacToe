package nif.tictactoe.model;

import java.util.Random;

import nif.tictactoe.BrainBase;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: The brain for playing hard games versus the computer.
 */
public class HardBrain extends BrainBase {

	public HardBrain() {
		int random = new Random().nextInt(ImmitatorScheduler.getInstance().hasStrategies() ? 4 : 3);

		switch (random) {
		case 0:
			_strategy = new PokerBettingStrategy();
			break;
		case 1:
			_strategy = new ModerateBettingStrategy();
			break;
		case 2:
			_strategy = new AggressiveBettingStrategy();
			break;
		case 3:
			_strategy = ImmitatorScheduler.getInstance().getImmitatorBidStrategy();
			break;
		}
	}
}
