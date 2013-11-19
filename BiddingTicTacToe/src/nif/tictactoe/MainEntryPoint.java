package nif.tictactoe;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: Main startup class of the application.
 */
public class MainEntryPoint extends Application {

	public static void main(String[] args) {
			launch(args);
	}

	/**
	 * Main start method for the JavaFx-application.
	 * 
	 * @throws IOException
	 *             *
	 */
	@Override
	public void start(Stage mainStage) {
		try {
			Parent root = FXMLLoader.load(getClass().getResource("views/MainView.fxml"));
			mainStage.setTitle("Bidding Tic Tac Toe");
			mainStage.getIcons().add(new Image("file:images/tictactoeIcon.png"));
			mainStage.setScene(new Scene(root));
			mainStage.show();
			FxmlHelper.getInstance().setMainStage(mainStage);
		} catch (Exception ex) {
			System.out.println(ex.toString());
		}
	}
}
