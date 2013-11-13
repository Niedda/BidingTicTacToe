package nif.tictactoe.controllers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Timer;
import java.util.TimerTask;

import nif.tictactoe.*;
import nif.tictactoe.model.*;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.util.Duration;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: Main User Interface Controller which keeps track of the
 *               graphical elements.
 */
public class MainController {

	// Graphical User Interface elements
	public Button _betButton;
	public Slider _betSlider;
	public Button _field00;
	public Button _field01;
	public Button _field02;
	public Button _field10;
	public Button _field20;
	public Button _field11;
	public Button _field12;
	public Button _field22;
	public Button _field21;
	public Label _aiCredit;
	public Label _plCredit;
	public Label _infoLabel;
	public MenuItem _newEasyGameMenuItem;
	public MenuItem _newHardGameMenuItem;

	// Privates
	private Button[] _ticTacToeGrid;

	private Timer _timer;

	private FadeTransition fadeIn = new FadeTransition(Duration.millis(1500));

	// Private Getters
	private Timer getTimer() {
		if (_timer == null) {
			_timer = new Timer();
		}
		return _timer;
	}

	private Button getBetButton() {
		return _betButton;
	}

	private Slider getBetSlider() {
		return _betSlider;
	}

	private Label getCreditLabelAi() {
		return _aiCredit;
	}

	private Label getCreditLabelPlayer() {
		return _plCredit;
	}

	private Label getInfoLabel() {
		return _infoLabel;
	}

	private Button[] getTicTacToeGrid() {
		if (_ticTacToeGrid == null) {
			_ticTacToeGrid = new Button[] { _field00, _field01, _field02,
					_field10, _field11, _field12, _field20, _field21, _field22, };
		}
		return _ticTacToeGrid;
	}

	// Private Methods
	/**
	 * Initialize the fade effect for displaying the info-label.
	 */
	private void initializeFade() {
		fadeIn.setNode(_infoLabel);
		fadeIn.setFromValue(0.0);
		fadeIn.setToValue(0.7);
		fadeIn.setCycleCount(1);
		fadeIn.setAutoReverse(false);
	}

	/**
	 * Switch to the game-end-mode. The GUI-play-elements are getting disabled.
	 */
	private void switchToGameEndMode() {
		for (Button btn : getTicTacToeGrid()) {
			btn.setDisable(true);
		}
		getBetButton().setDisable(true);
		getBetSlider().setDisable(true);		
	}

	/**
	 * Switch to the game-end-mode. The graphical user interface elements are
	 * getting disabled.
	 */
	private void updatePlayground() {
		GameField[][] playground = new GameField[3][3];
		playground[0][0] = new GameField(0, 0, _field00.getText());
		playground[0][1] = new GameField(0, 1, _field01.getText());
		playground[0][2] = new GameField(0, 2, _field02.getText());
		playground[1][0] = new GameField(1, 0, _field10.getText());
		playground[2][0] = new GameField(2, 0, _field20.getText());
		playground[1][1] = new GameField(1, 1, _field11.getText());
		playground[2][2] = new GameField(2, 2, _field22.getText());
		playground[2][1] = new GameField(2, 1, _field21.getText());
		playground[1][2] = new GameField(1, 2, _field12.getText());
		Context.getContext().setPlayground(playground);
	}

	/**
	 * Maps the computer move to the graphical user interface.
	 */
	private void setAiMove(GameField field) {
		try {
			String refField = String.format("_field%s%s", field.getXPosition(), field.getYPosition());
			Field f = this.getClass().getDeclaredField(refField);
			f.getGenericType();
			f.setAccessible(true);
			Button b = (Button) f.get(this);
			b.setText("X");
		} catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
			// TODO log exception
		}
	}

	// Publics
	/**
	 * Switch to the bet mode and disable the grid.
	 */
	public void switchToBetMode() {
		for (Button btn : getTicTacToeGrid()) {
			btn.setDisable(true);
		}
		getBetButton().setDisable(false);
		getBetSlider().setDisable(false);
		if (getCreditLabelPlayer().getText().equals("0")) {
			getBetSlider().setDisable(true);
		} else {
			getBetSlider().setDisable(false);
			getBetSlider().setMin(1);
		}
		getBetSlider().setMax(Double.parseDouble(getCreditLabelPlayer().getText()));
	}

	/**
	 * Switch to the move mode and enable the grid.
	 */
	public void switchToMoveMode() {
		for (Button btn : getTicTacToeGrid()) {
			if (btn.getText().equals("")) {
				btn.setDisable(false);
			}
		}
		getBetButton().setDisable(true);
		getBetSlider().setDisable(true);
	}

	/**
	 * Resets the playground to the default values.
	 */
	public void resetPlayground() {
		for (Button button : getTicTacToeGrid()) {
			button.setText("");
		}
		getCreditLabelAi().setText("8");
		getCreditLabelPlayer().setText("8");
	}

	/**
	 * Show an Info Message as splash screen which disappears after 1.5 seconds.
	 */
	public void showInfoMsg(String message) {
		_infoLabel.setText(message);
		_infoLabel.setVisible(true);
		
		fadeIn.onFinishedProperty().set(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg) {				
				_infoLabel.setText("");
				_infoLabel.setVisible(false);
				if(Context.getContext().isAiWinner()) {
					showWinMsg();				
				} else if(Context.getContext().isPlayerWinner()) {
					switchToMoveMode();
				}
			}			
		});		
		fadeIn.playFromStart();
	}

	/**
	 * Checks if the game has a winner and displays the winner message.
	 */
	public void showWinMsg() {
		updatePlayground();
		Thread t = new Thread(){
			@Override
			public void run() {
				updatePlayground();
				if (Context.getContext().isAiGameWinner()) {
					_infoLabel.setText("Der Computer hat das Spiel gewonnen!");
					_infoLabel.setVisible(true);
					fadeIn.playFromStart();
					switchToGameEndMode();
					if (Context.getContext().getBrain().getClass() == EasyBrain.class) {
						MongoDatabase.getDb().addLoseEasy();
					} else {
						MongoDatabase.getDb().addLoseHard();
					}
				} else if (Context.getContext().isPlayerGameWinner()) {
					_infoLabel.setText("Du hast das Spiel gewonnen!");
					_infoLabel.setVisible(true);
					fadeIn.playFromStart();
					switchToGameEndMode();
					if (Context.getContext().getBrain().getClass() == EasyBrain.class) {
						MongoDatabase.getDb().addWinEasy();
					} else {
						MongoDatabase.getDb().addWinHard();
					}
				} else if (Context.getContext().isDrawGame()) {
					_infoLabel.setText("Das Spiel endete Unentschieden!");
					_infoLabel.setVisible(true);
					fadeIn.playFromStart();
					switchToGameEndMode();
				} else {
					switchToBetMode();
				}
			}
		};				
		t.run();
	}

	// Event Handlers
	/**
	 * Handles the Click-Event fired by the bet-button.
	 */
	public void onBetClick(ActionEvent e) {
		try {
			updatePlayground();
			Context.getContext().setAiBid(Context.getContext().getBrain().getNextBid());
			Context.getContext().setPlayerBid((int) getBetSlider().getValue());
			getCreditLabelAi().setText(String.valueOf(Context.getContext().getAiCredits()));
			getCreditLabelPlayer().setText(String.valueOf(Context.getContext().getPlayerCredits()));
			showInfoMsg(Context.getContext().getBetResultMessage());
			
			if (Context.getContext().isAiWinner()) {
				GameField field = Context.getContext().getBrain().getNextMove();
				setAiMove(field);
			} else if (Context.getContext().isPlayerWinner()) {
				switchToMoveMode();
			}
		} catch (Exception ex) {
			System.out.print(ex.toString());
		}
	}

	/**
	 * Handles the Click-Event fired by the grid-button if a move by the player
	 * was made.
	 */
	public void onGridButtonClick(ActionEvent e) {
		Button btn = (Button) e.getSource();
		btn.setText("O");
		switchToBetMode();
		showWinMsg();
	}

	/**
	 * Handles the Click-Event fired by the new easy game menu button.
	 */
	public void onNewEasyGameClick() {
		initializeFade();
		Context.getContext().setBrain(new EasyBrain());
		Context.getContext().resetContext();
		resetPlayground();
		switchToBetMode();
		getInfoLabel().setVisible(false);
	}

	/**
	 * Handles the Click-Event fired by the new hard game menu button.
	 */
	public void onNewHardGameClick() {
		initializeFade();
		Context.getContext().setBrain(new HardBrain());
		Context.getContext().resetContext();
		resetPlayground();
		switchToBetMode();
		getInfoLabel().setVisible(false);
	}

	/**
	 * Handles the Click-Event fired by the settings menu button.
	 * 
	 * @throws Exception
	 */
	public void onSettingsClick() throws Exception {
		MainEntryPoint.showSimpleDialog(getClass().getResource("../views/SettingsView.fxml"));
	}

	/**
	 * Handles the Click-Event fired by the about menu button.
	 * 
	 * @throws IOException
	 */
	public void onAboutClick() throws IOException {
		MainEntryPoint.showModalDialog(getClass().getResource("../views/AboutView.fxml"));
	}

	/**
	 * Handles the Click-Event fired by the statics menu button.
	 */
	public void onStatisticClick() {
		MainEntryPoint.showModalDialog(getClass().getResource(
				"../views/StatisticView.fxml"));
	}

	/**
	 * Event Handler if the Menu Button exit is clicked.
	 */
	public void onFinishClick() throws Exception {
		Platform.exit();
	}
}