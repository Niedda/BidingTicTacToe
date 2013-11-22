import java.io.File;
import java.util.UUID;

import junit.framework.TestCase;
import nif.tictactoe.SettingHelper;

import org.junit.AfterClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link SettingHelper}.
 * 
 * @author Fabrizio Niedda
 */
@RunWith(JUnit4.class)
public class SettingTests extends TestCase {

	@Test
	public void testPlayerName() {
		String name = SettingHelper.getInstance().loadPlayerName();
		assertTrue("Testing for valid name", !name.equals(""));
	}

	@Test
	public void testPlayerNameChange() {
		String newName = "TestPlayer";
		SettingHelper.getInstance().savePlayerName(newName);
		String loadedName = SettingHelper.getInstance().loadPlayerName();
		assertTrue("Testing for valid name", newName.equals(loadedName));
	}

	@Test
	public void testWinCounter() {
		SettingHelper.getInstance().savePlayerName(rndName);

		SettingHelper.getInstance().addPlayerHardWin();
		int winHardAfter = SettingHelper.getInstance().getPlayerHardWin();
		assertEquals(1, winHardAfter);
		
		SettingHelper.getInstance().addPlayerEasyWin();
		int winEasyAfter = SettingHelper.getInstance().getPlayerEasyWin();
		assertEquals(1, winEasyAfter);
	}

	@Test
	public void testLoseCounter() {	
		SettingHelper.getInstance().savePlayerName(rndName);

		SettingHelper.getInstance().addPlayerHardLose();
		int loseHardAfter = SettingHelper.getInstance().getPlayerHardLose();
		assertEquals(1, loseHardAfter);
		
		SettingHelper.getInstance().addPlayerEasyLose();
		int loseEasyAfter = SettingHelper.getInstance().getPlayerEasyLose();
		assertEquals(1, loseEasyAfter);
	}
	
	@AfterClass
	public static void testCleanup() {
		File f = new File("TicTacToe_Properties.config");
		
		if(f.exists()) {
			f.delete();
		}
	}
	
	private String rndName = UUID.randomUUID().toString();
}
