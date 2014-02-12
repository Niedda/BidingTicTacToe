package nif.tictactoe;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FxmlHelper {

	private static Stage _dialogStage;
	private static Stage _mainStage;
	private static FxmlHelper _instance;

	private void setDialogStage(Stage stage) {
		if (_dialogStage != null) {
			_dialogStage.close();
		}
		_dialogStage = stage;
	}

	private Stage getDialogStage() {
		return _dialogStage;
	}

	private URL getFXMLUrl(String name) {
		return getClass().getResource("views/".concat(name));
	}

	private void initDialog(String fxmlFile, String title) {
		try {
			URL fxmlUrl = getFXMLUrl(fxmlFile);
			Stage dialog = new Stage();
			setDialogStage(dialog);
			dialog.setTitle(title);
			dialog.initStyle(StageStyle.UTILITY);
			dialog.initOwner(_mainStage);
			Parent loader = FXMLLoader.load(fxmlUrl);
			dialog.setScene(new Scene(loader));
		} catch (Exception ex) {
			if (!fxmlFile.equals("ErrorView.fxml")) {
				Context.getContext().handleException(ex);
			}
		}
	}

	public static FxmlHelper getInstance() {
		if (_instance == null) {
			_instance = new FxmlHelper();
		}
		return _instance;
	}

	public void setMainStage(Stage stage) {
		_mainStage = stage;
	}

	/**
	 * Helper method for displaying a modal dialog.
	 */
	public void showModalDialog(String fxmlFile, String title) {
		initDialog(fxmlFile, title);
		getDialogStage().initModality(Modality.WINDOW_MODAL);
		getDialogStage().show();
	}

	/**
	 * Helper method for displaying a non modal dialog.
	 */
	public void showSimpleDialog(String fxmlFile, String title) {
		initDialog(fxmlFile, title);
		getDialogStage().show();
	}

	/**
	 * Reset the game after an unexpected error.
	 */
	public void resetGameAfterError() {
		try {
			_mainStage.close();
			Parent root = FXMLLoader.load(MainEntryPoint.class.getResource("views/MainView.fxml"));
			_mainStage.setTitle("Bidding Tic Tac Toe");
			_mainStage.getIcons().add(new Image("file:images/tictactoeIcon.png"));
			_mainStage.setScene(new Scene(root));
			_mainStage.show();
		} catch (Exception ex) {
			Platform.exit();
		}
	}

	/*
	 * Open a file stored in the view folder.
	 */
	public void openFile(String name) {
		try {
			File f = new File("Spielregeln.pdf");
			InputStream inputStream = MainEntryPoint.class.getResourceAsStream("views/" + name);
			OutputStream out = new FileOutputStream(f);
			byte buf[] = new byte[1024];
			int len;
			while ((len = inputStream.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			inputStream.close();
			Desktop.getDesktop().open(f);
		} catch (Exception e) {
			Context.getContext().handleException(e);
		}
	}

	/**
	 * Close the currently open dialog.
	 */
	public void closeDialog() {
		if (_dialogStage != null) {
			_dialogStage.close();
		}
	}
}
