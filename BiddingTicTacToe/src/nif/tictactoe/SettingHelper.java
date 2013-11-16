package nif.tictactoe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class SettingHelper {

	private SettingHelper() {
		// NOP
	}

	// Private
	private final String _propertyNamePlayer = "pname";

	private final String _filename = "TicTacToe_Properties.xml";

	private final String _defaultName = "Player1";

	private final String _hardWin = "hWin";

	private final String _easyWin = "eWin";

	private final String _hardLose = "hLose";

	private final String _easyLose = "eLose";

	private int loadStatistic(String propName) {
		try {
			Properties props = new Properties();
			InputStream is = null;
			File f = getPropertyFile();
			is = new FileInputStream(f);
			props.load(is);
			return Integer.parseInt(props.getProperty(propName, "0"));
		} catch (Exception e) {
			// TODO: add exception handling
			return 0;
		}
	}

	private void saveStatistic(String propName, int value) {
		try {
			Properties props = new Properties();
			props.setProperty(propName, String.valueOf(value));
			File f = getPropertyFile();
			OutputStream out = new FileOutputStream(f);
			props.store(out, null);
		} catch (Exception e) {
			// TODO: add exception handling
		}
	}

	private File getPropertyFile() {
		File f = new File(_filename);

		// Create the file if not existing.
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO log exception
			}
		}
		return f;
	}
	
	// Public
	public void savePlayerName(String name) {
		try {
			Properties props = new Properties();
			props.setProperty(_propertyNamePlayer, name);
			File f = getPropertyFile();
			OutputStream out = new FileOutputStream(f);
			props.store(out, null);
		} catch (Exception e) {
			// TODO: add exception handling
		}
	}

	public String loadPlayerName() {
		try {
			Properties props = new Properties();
			InputStream is = null;
			File f = getPropertyFile();

			is = new FileInputStream(f);
			props.load(is);
			String name = (String) props.getProperty(_propertyNamePlayer, _defaultName);
			return name;
		} catch (Exception e) {
			// TODO: add exception handling
			return "";
		}
	}

	public void addPlayerHardWin() {
		int counter =  loadStatistic(loadPlayerName().concat(_hardWin));
		counter++;
		saveStatistic(_propertyNamePlayer + _hardWin, counter);
	}
	
	public void addPlayerEasyWin() {
		int counter =  loadStatistic(loadPlayerName().concat(_easyWin));
		counter++;
		saveStatistic(_propertyNamePlayer + _easyWin, counter);
	}
	
	public void addPlayerHardLose() {
		int counter =  loadStatistic(loadPlayerName().concat(_hardLose));
		counter++;
		saveStatistic(_propertyNamePlayer + _hardLose, counter);
	}
	
	public void addPlayerEasyLose() {
		int counter =  loadStatistic(loadPlayerName().concat(_easyLose));
		counter++;
		saveStatistic(_propertyNamePlayer + _easyLose, counter);
	}

	public int getPlayerHardWin() {
		return loadStatistic(loadPlayerName().concat(_hardWin));
	}
	
	public int getPlayerEasyWin() {
		return loadStatistic(loadPlayerName().concat(_easyWin));
	}
	
	public int getPlayerHardLose() {
		return loadStatistic(loadPlayerName().concat(_hardLose));
	}
	
	public int getPlayerEasyLose() {
		return loadStatistic(loadPlayerName().concat(_easyLose));
	}
	
	// Static
	private static SettingHelper _instance;
	
	public static SettingHelper getInstance() {
		if (_instance == null) {
			_instance = new SettingHelper();
		}
		return _instance;
	}
}
