package nif.tictactoe.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import nif.tictactoe.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable {
		
	//UI-Fields
	@FXML
	private TextField PlayerName;
	@FXML 
	private Label ErrorField;
	
	//Event Handlers
	@FXML
	private void onSaveClick() {
		if(PlayerName.getText().equals("")) {
			//Invalid Name set to the default
			ErrorField.setText("Bite einen g�ltigen Name eingeben.");
			return;
		}
		SettingHelper.getInstance().savePlayerName(PlayerName.getText());
		MainEntryPoint.closeDialog();
	}	

	@FXML
	private void onTextChanged() {
		if(PlayerName.getText().equals("")) {
			//Invalid Name set to the default
			ErrorField.setText("Bite einen g�ltigen Name eingeben.");
		} else {
			ErrorField.setText("");
		}
	}

	//Publics	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		PlayerName.setText(SettingHelper.getInstance().loadPlayerName());	
	}
}