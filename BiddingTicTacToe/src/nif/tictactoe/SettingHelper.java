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

	private final String _filename = "TicTacToe_Properties.config";

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
		} catch (Exception ex) {
			Context.getContext().handleException(ex);
			return 0;
		}
	}

	private void saveStatistic(String propName, int value) {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(getPropertyFile()));
			props.setProperty(propName, String.valueOf(value));
			File f = getPropertyFile();
			OutputStream out = new FileOutputStream(f);
			props.store(out, null);
		} catch (Exception ex) {
			Context.getContext().handleException(ex);
		}
	}

	private File getPropertyFile() {
		File f = new File(_filename);

		// Create the file if not existing.
		if (!f.exists()) {
			try {
				f.createNewFile();
			} catch (IOException ex) {
				Context.getContext().handleException(ex);
			}
		}
		return f;
	}
	
	// Public
	public void savePlayerName(String name) {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(getPropertyFile()));
			props.setProperty(_propertyNamePlayer, name);
			File f = getPropertyFile();
			OutputStream out = new FileOutputStream(f);
			props.store(out, null);
		} catch (Exception ex) {
			Context.getContext().handleException(ex);
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
		} catch (Exception ex) {
			Context.getContext().handleException(ex);
			return _defaultName;
		}
	}

	public void addPlayerHardWin() {
		String pName = loadPlayerName();
		int counter =  loadStatistic(pName.concat(_hardWin));
		counter++;
		saveStatistic(pName.concat(_hardWin), counter);
	}
	
	public void addPlayerEasyWin() {
		String pName = loadPlayerName();
		int counter =  loadStatistic(pName.concat(_easyWin));
		counter++;
		saveStatistic(pName.concat(_easyWin), counter);
	}
	
	public void addPlayerHardLose() {
		String pName = loadPlayerName();
		int counter =  loadStatistic(pName.concat(_hardLose));
		counter++;
		saveStatistic(pName.concat(_hardLose), counter);
	}
	
	public void addPlayerEasyLose() {
		String pName = loadPlayerName();
		int counter =  loadStatistic(pName.concat(_easyLose));
		counter++;
		saveStatistic(pName.concat(_easyLose), counter);
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
