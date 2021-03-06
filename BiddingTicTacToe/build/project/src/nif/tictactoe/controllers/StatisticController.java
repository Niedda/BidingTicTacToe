package nif.tictactoe.controllers;

import java.net.URL;
import java.util.ResourceBundle;

import nif.tictactoe.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

/*
 * Controller for StatisticView.fxml.
 */
public class StatisticController implements Initializable {
	@FXML
	private Label _easyWin;
	@FXML
	private Label _easyLose;
	@FXML
	private Label _hardWin;
	@FXML
	private Label _hardLose;
	@FXML
	private Label _title;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		_easyWin.setText(String.valueOf(SettingHelper.getInstance().getPlayerEasyWin()));
		_hardWin.setText(String.valueOf(SettingHelper.getInstance().getPlayerHardWin()));
		_easyLose.setText(String.valueOf(SettingHelper.getInstance().getPlayerEasyLose()));
		_hardLose.setText(String.valueOf(SettingHelper.getInstance().getPlayerHardLose()));
		_title.setText(String.format("Statistik f�r %s", SettingHelper.getInstance().loadPlayerName()));
	}	
}
