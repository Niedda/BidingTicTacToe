import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.UUID;

import nif.tictactoe.SettingHelper;
import nif.tictactoe.model.ImmitatorBidStrategy;
import nif.tictactoe.model.ImmitatorScheduler;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ImmitatorTests {

	private ImmitatorBidStrategy _bidStrategy;

	@Before
	public void setUp() throws Exception {
		UUID id = UUID.randomUUID();
		int[] testStrategy = { 1, 2, 3, 4, 5, 6 };
		_bidStrategy = new ImmitatorBidStrategy(testStrategy, 0, 0, id);		
	}

	@After
	public void tearDown() throws Exception {
		ArrayList<ImmitatorBidStrategy> strategies = SettingHelper.getInstance().loadAvailableBidStrategies();
		
		for(ImmitatorBidStrategy strat : strategies) {
			SettingHelper.getInstance().deleteBidStrategy(strat.getSaveNumber());
		}
	}

	@Test
	public void testSaveImmitatorBidStrategy() {
		ImmitatorScheduler.getInstance().saveNewBidStrategyIfPossible(_bidStrategy);
		ImmitatorBidStrategy strategy = ImmitatorScheduler.getInstance().getImmitatorBidStrategy();
		
		assertNotNull(strategy);
		assertArrayEquals(_bidStrategy.getBides(), strategy.getBides());
	}
	
	@Test
	public void checkForObsoleteStrategy() {
		_bidStrategy.addLose();
		_bidStrategy.addWin();
		ImmitatorScheduler.getInstance().saveNewBidStrategyIfPossible(_bidStrategy);
		boolean exist = SettingHelper.getInstance().existBidStrategy(_bidStrategy.getSaveNumber());
		assertTrue(exist);
	}
}
