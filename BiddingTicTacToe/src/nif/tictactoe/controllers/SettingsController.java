package nif.tictactoe.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import nif.tictactoe.*;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class SettingsController implements Initializable
{
  @FXML
  private TextField PlayerName;
  @FXML
  private Label ErrorField;

  // Order functions on importance and don't comment if it's public or private or an event handler, you can see that on the next line anyways ("Don't state the obvious", it just bloats the code)
  @Override
  public void initialize(URL location, ResourceBundle resources) {
    String playerName;
    playerName = SettingHelper.getInstance().loadPlayerName();
    PlayerName.setText(playerName);
  }
  
  @FXML
  private void onSaveClick() {
    if (!checkUsernameEmpty())
    {
      SettingHelper.getInstance().savePlayerName(PlayerName.getText());

      MainEntryPoint.closeDialog();
    }
  }

  @FXML
  private void onTextChanged() {
    if (!checkUsernameEmpty())
    {
      ErrorField.setText("");
    }
  }
  
  private boolean checkUsernameEmpty()
  {
    if (PlayerName.getText().equals(""))
    {
      ErrorField.setText("Bite einen gültigen Name eingeben.");
      
      return false; 
    }
    
    return true;
  }
}
