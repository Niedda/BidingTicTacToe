package nif.tictactoe.model;

import java.util.ArrayList;
import java.util.Collections;
import nif.tictactoe.SettingHelper;

public class ImmitatorScheduler {

	private final int _cacheSize = 3;
		
	private ImmitatorScheduler() {
		//NOP
	}
	
	public boolean hasStrategies() {
		return SettingHelper.getInstance().loadAvailableBidStrategies().size() != 0;		
	}
	
	private boolean hasEmptySpace() {
		return SettingHelper.getInstance().loadAvailableBidStrategies().size() < _cacheSize;
	}	
	
	public void saveNewBidStrategyIfPossible(ImmitatorBidStrategy newBidStrat) {
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
		
		ImmitatorBidStrategy bidStrat = getObsoleteStrategy();
		
		if(bidStrat == null) {
			return;
		}
		
		helper.deleteBidStrategy(bidStrat.getSaveNumber());
		helper.saveBidStrategy(newBidStrat);		
	}
	
	public ImmitatorBidStrategy getImmitatorBidStrategy() {
		if(!hasStrategies()) {
			return null;
		}
		
		SettingHelper helper = SettingHelper.getInstance();
		ArrayList<ImmitatorBidStrategy> strategies = helper.loadAvailableBidStrategies();
		Collections.sort(strategies);
		return strategies.get(0);		
	}
	
	private ImmitatorBidStrategy getObsoleteStrategy() {
		if(!hasStrategies()) {
			return null;
		}
		
		SettingHelper helper = SettingHelper.getInstance();
		ArrayList<ImmitatorBidStrategy> strategies = helper.loadAvailableBidStrategies();
		
		for(ImmitatorBidStrategy bidStrat : strategies) {
				if(bidStrat.getWinLoseProportion() < 1 && bidStrat.gamesPlayed() > 6) {
					return bidStrat;
				}						
		}
		
		return null;
	}
	
	//Singleton
	public static ImmitatorScheduler getInstance() {
		if(_instance == null) {
			_instance = new ImmitatorScheduler();
		}
		
		return _instance;
	}
	
	private  static ImmitatorScheduler _instance;		
}