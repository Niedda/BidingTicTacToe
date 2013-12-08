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
		if(ImmitatorScheduler.getInstance().hasStrategies()) {
			_strategy = ImmitatorScheduler.getInstance().getImmitatorBidStrategy();
		} else {
			int rnd = new Random().nextInt(2);
			
			if(rnd == 0) {
				_strategy = new ModerateBettingStrategy();	
			} else {
				_strategy = new AggressiveBettingStrategy();
			}
		}
		
	}	
}
