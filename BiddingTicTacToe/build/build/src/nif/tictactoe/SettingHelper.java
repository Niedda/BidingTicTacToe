package nif.tictactoe;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import nif.tictactoe.model.IStrategy;
import nif.tictactoe.model.ImmitatorBettingStrategy;

/*
 * Offers easy access to the game setting.
 */
public class SettingHelper implements Serializable {

	// Private
	private static final long serialVersionUID = -7622863186940617107L;

	private SettingHelper() {
		// NOP
	}

	private final String _propertyNamePlayer = "pname";
	private final String _splashDurationName = "splashD";
	private final String _defaultSplashDuration = "600";
	private final String _filename = "TicTacToe_Properties.config";
	private final String _defaultName = "Player1";
	private final String _hardWin = "hWin";
	private final String _easyWin = "eWin";
	private final String _hardLose = "hLose";
	private final String _easyLose = "eLose";
	private final String _strategy = "strat";
	private static SettingHelper _instance;

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

	private String getCurrentFilePath() {
		return System.getProperty("user.dir");
	}

	/*
	 * Save the new player name to the property file.
	 */
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

	/*
	 * Load the player name from the property file.
	 */
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

	/*
	 * Saves the new duration for splash screens.
	 */
	public void saveSplashDuration(int durationMs) {
		try {
			Properties props = new Properties();
			props.load(new FileInputStream(getPropertyFile()));
			props.setProperty(_splashDurationName, String.valueOf(durationMs));
			File f = getPropertyFile();
			OutputStream out = new FileOutputStream(f);
			props.store(out, null);
		} catch (Exception ex) {
			Context.getContext().handleException(ex);
		}
	}

	/*
	 * Returns the currently set duration of the splash screens.
	 */
	public int loadSplashDuration() {
		try {
			Properties props = new Properties();
			InputStream is = null;
			File f = getPropertyFile();
			is = new FileInputStream(f);
			props.load(is);
			String duration = (String) props.getProperty(_splashDurationName, _defaultSplashDuration);
			int result = Integer.parseInt(duration);
			return result;			
		} catch (Exception ex) {
			Context.getContext().handleException(ex);
			return Integer.parseInt(_defaultSplashDuration);
		}
	}

	/*
	 * Add a win vs a hard pc to the player statistic.
	 */
	public void addPlayerHardWin() {
		String pName = loadPlayerName();
		int counter = loadStatistic(pName.concat(_hardWin));
		counter++;
		saveStatistic(pName.concat(_hardWin), counter);
	}

	/*
	 * Add a win vs a easy pc to the player statistic.
	 */
	public void addPlayerEasyWin() {
		String pName = loadPlayerName();
		int counter = loadStatistic(pName.concat(_easyWin));
		counter++;
		saveStatistic(pName.concat(_easyWin), counter);
	}

	/*
	 * Add a lose vs a hard pc to the player statistic.
	 */
	public void addPlayerHardLose() {
		String pName = loadPlayerName();
		int counter = loadStatistic(pName.concat(_hardLose));
		counter++;
		saveStatistic(pName.concat(_hardLose), counter);
	}

	/*
	 * Add a lose vs a easy pc to the player statistic.
	 */
	public void addPlayerEasyLose() {
		String pName = loadPlayerName();
		int counter = loadStatistic(pName.concat(_easyLose));
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

	/*
	 * Save the {@link ImmitatorBidStrategy}.
	 */
	public void saveBidStrategy(ImmitatorBettingStrategy strategy) {
		FileOutputStream fileOutputStream;

		try {
			fileOutputStream = new FileOutputStream(getCurrentFilePath().concat(_strategy.concat(strategy.getSaveNumber()).concat(".strategy")));
			ObjectOutputStream obj_out = new ObjectOutputStream(fileOutputStream);
			obj_out.writeObject(strategy);
			obj_out.flush();
			obj_out.close();
		} catch (Exception ex) {
			Context.getContext().handleException(ex);
		}
	}

	public boolean existBidStrategy(String saveNumber) {
		File f = new File(getCurrentFilePath().concat(_strategy.concat(String.valueOf(saveNumber).concat(".strategy"))));
		return f.exists();
	}

	/*
	 * Load the {@link ImmitatorBidStrategy}.
	 */
	public IStrategy loadBidStrategy(String saveNumber) {
		try {
			FileInputStream fileInputStream = new FileInputStream(getCurrentFilePath().concat(_strategy.concat(String.valueOf(saveNumber).concat(".strategy"))));
			Object obj = new ObjectInputStream(fileInputStream).readObject();
			fileInputStream.close();

			if (obj instanceof ImmitatorBettingStrategy) {
				return (ImmitatorBettingStrategy) obj;
			}
		} catch (Exception ex) {
			Context.getContext().handleException(ex);
		}
		return null;
	}

	/*
	 * Load all available {@link ImmitatorBidStrategy}.
	 */
	public ArrayList<ImmitatorBettingStrategy> loadAvailableBidStrategies() {
		File dir = new File(getCurrentFilePath());
		ArrayList<ImmitatorBettingStrategy> result = new ArrayList<ImmitatorBettingStrategy>();

		List<String> strategyFiles = new ArrayList<String>();

		for (File file : dir.listFiles()) {
			if (file.getName().endsWith((".strategy"))) {
				strategyFiles.add(file.getName());
			}
		}

		for (String fName : strategyFiles) {
			File f = new File(fName);

			try {
				FileInputStream fileInputStream = new FileInputStream(getCurrentFilePath().concat(f.getName()));
				Object obj = new ObjectInputStream(fileInputStream).readObject();
				fileInputStream.close();

				if (obj instanceof ImmitatorBettingStrategy) {
					result.add((ImmitatorBettingStrategy) obj);
				}

			} catch (Exception ex) {
				continue;
			}
		}

		return result;
	}

	/*
	 * Delete the {@link ImmitatorBidStrategy}.
	 */
	public void deleteBidStrategy(String saveNumber) {
		try {
			File f = new File(getCurrentFilePath().concat(_strategy.concat(String.valueOf(saveNumber).concat(".strategy"))));

			if (f.exists()) {
				f.delete();
			}
		} catch (Exception ex) {
			Context.getContext().handleException(ex);
		}
	}

	public static SettingHelper getInstance() {
		if (_instance == null) {
			_instance = new SettingHelper();
		}
		return _instance;
	}
}
