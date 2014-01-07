package nif.tictactoe.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import nif.tictactoe.FxmlHelper;
import nif.tictactoe.SettingHelper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;

/*
 * Controller for SettingsView.fxml.
 */
public class SettingsController implements Initializable {
	@FXML
	private TextField _playerName;
	@FXML
	private Label _errorField;
	@FXML
	private Slider _splashDuration;
	
	@FXML
	private void onSaveClick() {
		if (checkUsernameEmpty()) {
			SettingHelper.getInstance().savePlayerName(_playerName.getText());
			double splashDuration = _splashDuration.getValue() == 0 ? 0.001d :_splashDuration.getValue(); 
			SettingHelper.getInstance().saveSplashDuration(((int)(splashDuration * 1000)));
			FxmlHelper.getInstance().closeDialog();
		}
	}

	@FXML
	private void onTextChanged() {
		if (!checkUsernameEmpty()) {
			_errorField.setText("");
		}
	}

	private boolean checkUsernameEmpty() {
		if (_playerName.getText().equals("")) {
			_errorField.setText("Bite einen gültigen Name eingeben.");
			return false;
		}
		return true;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		_playerName.setText(SettingHelper.getInstance().loadPlayerName());
		_splashDuration.setValue(SettingHelper.getInstance().loadSplashDuration() / 1000d);
	}
}
