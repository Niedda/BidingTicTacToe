package nif.tictactoe;

import java.net.URL;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: Main startup class of the application.
 */
public class MainEntryPoint extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	/**
	 * Main start method for the JavaFx-application. *
	 */
	@Override
	public void start(Stage mainStage) throws Exception {
		Parent root = FXMLLoader.load(getClass().getResource("views/MainView.fxml"));
		mainStage.setTitle("Biding Tic Tac Toe");
		mainStage.getIcons().add(new Image("file:images/tictactoeIcon.png"));
		mainStage.setScene(new Scene(root));
		mainStage.show();
		_mainStage = mainStage;	
	}

	/**
	 * Helper method for displaying a modal dialog.
	 * 
	 * @param fxmlFile
	 *            the URL path to the FXML File to load.
	 */
	public static void showModalDialog(URL fxmlFile) {
		try {
			Stage dialog = new Stage();
			setDialogStage(dialog);
			dialog.initModality(Modality.WINDOW_MODAL);
			dialog.initStyle(StageStyle.UTILITY);
			dialog.initOwner(_mainStage);
			FXMLLoader loader = new FXMLLoader(fxmlFile);
			Parent parent = (Parent) loader.load();			
			dialog.setScene(new Scene(parent));
			dialog.show();
		} catch (Exception e) {
			// TODO: handle
		}
	}

	/**
	 * Helper method for displaying a non modal dialog.
	 * 
	 * @param fxmlFile
	 *            the URL path to the FXML File to load.
	 */
	public static void showSimpleDialog(URL fxmlFile) {
		try {
			Stage dialog = new Stage();
			setDialogStage(dialog);
			dialog.initStyle(StageStyle.UTILITY);
			dialog.initOwner(_mainStage);
			Parent loader = FXMLLoader.load(fxmlFile);
			dialog.setScene(new Scene(loader));
			dialog.show();
		} catch (Exception e) {
			// TODO: handle
		}
	}

	public static void closeDialog() {
		if (_dialogStage != null) {
			_dialogStage.close();
		}
	}

	private static void setDialogStage(Stage stage) {
		if (_dialogStage != null) {
			_dialogStage.close();	
		}
		_dialogStage = stage;	
	}

	private static Stage _dialogStage;

	private static Stage _mainStage;
}
