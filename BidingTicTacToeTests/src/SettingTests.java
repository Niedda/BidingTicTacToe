import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * Tests for {@link SettingHelper}.
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
}