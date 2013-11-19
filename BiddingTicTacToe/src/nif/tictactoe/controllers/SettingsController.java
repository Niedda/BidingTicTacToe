package nif.tictactoe.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import nif.tictactoe.FxmlHelper;
import nif.tictactoe.SettingHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable {
	@FXML
	private TextField PlayerName;
	@FXML
	private Label ErrorField;

	@FXML
	private void onSaveClick() {
		if (checkUsernameEmpty()) {
			SettingHelper.getInstance().savePlayerName(PlayerName.getText());
			FxmlHelper.getInstance().closeDialog();
		}
	}

	@FXML
	private void onTextChanged() {
		if (!checkUsernameEmpty()) {
			ErrorField.setText("");
		}
	}

	private boolean checkUsernameEmpty() {
		if (PlayerName.getText().equals("")) {
			ErrorField.setText("Bite einen gültigen Name eingeben.");
			return false;
		}

		return true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		String playerName;
		playerName = SettingHelper.getInstance().loadPlayerName();
		PlayerName.setText(playerName);
	}
}
