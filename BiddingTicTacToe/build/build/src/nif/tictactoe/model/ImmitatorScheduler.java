package nif.tictactoe.model;

import java.util.ArrayList;
import java.util.Collections;
import nif.tictactoe.SettingHelper;

public class ImmitatorScheduler {

	private final int _cacheSize = 3;
	private  static ImmitatorScheduler _instance;		
	
	private ImmitatorScheduler() {
		//NOP
	}
	
	public static ImmitatorScheduler getInstance() {
		if(_instance == null) {
			_instance = new ImmitatorScheduler();
		}		
		return _instance;
	}
	
	public boolean hasStrategies() {
		return SettingHelper.getInstance().loadAvailableBidStrategies().size() != 0;		
	}
	
	private boolean hasEmptySpace() {
		return SettingHelper.getInstance().loadAvailableBidStrategies().size() < _cacheSize;
	}	
	
	public void saveNewBidStrategyIfPossible(ImmitatorBettingStrategy newBidStrat) {
		SettingHelper helper = SettingHelper.getInstance();
		
		if(hasEmptySpace()) {
			helper.saveBidStrategy(newBidStrat);		
			return;
		}
		
		if(helper.existBidStrategy(newBidStrat.getSaveNumber())) {
			helper.deleteBidStrategy(newBidStrat.getSaveNumber());
			helper.saveBidStrategy(newBidStrat);
			return;
		}
		
		ImmitatorBettingStrategy bidStrat = getObsoleteStrategy();
		
		if(bidStrat == null) {
			return;
		}
		
		helper.deleteBidStrategy(bidStrat.getSaveNumber());
		helper.saveBidStrategy(newBidStrat);		
	}
	
	public ImmitatorBettingStrategy getImmitatorBidStrategy() {
		if(!hasStrategies()) {
			return null;
		}
		
		SettingHelper helper = SettingHelper.getInstance();
		ArrayList<ImmitatorBettingStrategy> strategies = helper.loadAvailableBidStrategies();
		Collections.sort(strategies);
		return strategies.get(0);		
	}
	
	private ImmitatorBettingStrategy getObsoleteStrategy() {
		if(!hasStrategies()) {
			return null;
		}
		
		SettingHelper helper = SettingHelper.getInstance();
		ArrayList<ImmitatorBettingStrategy> strategies = helper.loadAvailableBidStrategies();
		
		for(ImmitatorBettingStrategy bidStrat : strategies) {
				if(bidStrat.getWinLoseProportion() < 1 && bidStrat.gamesPlayed() > 6) {
					return bidStrat;
				}						
		}		
		return null;
	}
}