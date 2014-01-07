package nif.tictactoe.model;

import nif.tictactoe.BrainBase;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: The brain for playing easy games versus the computer. 
 */
public class EasyBrain extends BrainBase {

	public EasyBrain() {
		_strategy = new ModerateBettingStrategy();
	}	
}
