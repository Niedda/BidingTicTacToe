import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class SettingHelper {

	private SettingHelper() {
		// NOP
	}

	// Statics Private
	private static SettingHelper _instance;

	private static final String _propertyNamePlayer = "pname";

	private final static String _filename = "TicTacToe_Properties.xml";

	private final static String _defaultName = "Player1";

	// Publics
	public void savePlayerName(String name) {
		try {
			Properties props = new Properties();
			props.setProperty(_propertyNamePlayer, name);
			File f = new File(_filename);
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
			File f = new File(_filename);

			// Create the file if not existing.
			if (!f.exists()) {
				f.createNewFile();
			}

			is = new FileInputStream(f);
			props.load(is);
			String name =  (String)props.getProperty(_propertyNamePlayer, _defaultName);
			return name;
		} catch (Exception e) {
			// TODO: add exception handling		
			return "";
		}
	}

	// Statics Public
	public static SettingHelper getInstance() {
		if (_instance == null) {
			_instance = new SettingHelper();
		}
		return _instance;
	}
}
