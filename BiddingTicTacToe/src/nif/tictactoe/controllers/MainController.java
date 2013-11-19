package nif.tictactoe.controllers;

import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.ResourceBundle;

import javax.activity.InvalidActivityException;

import nif.tictactoe.*;
import nif.tictactoe.model.*;

import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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
public class MainController implements Initializable {

	@FXML
	private Button _betButton;
	@FXML
	private Slider _betSlider;
	@FXML
	private Button _field00;
	@FXML
	private Button _field01;
	@FXML
	private Button _field02;
	@FXML
	private Button _field10;
	@FXML
	private Button _field20;
	@FXML
	private Button _field11;
	@FXML
	private Button _field12;
	@FXML
	private Button _field22;
	@FXML
	private Button _field21;
	@FXML
	private Label _aiCredit;
	@FXML
	private Label _plCredit;
	@FXML
	private Label _infoLabel;
	@FXML
	private MenuItem _newEasyGameMenuItem;
	@FXML
	private MenuItem _newHardGameMenuItem;

	private Button[] _ticTacToeGrid;

	private FadeTransition fadeIn = new FadeTransition(Duration.millis(700));

	private gameState currentState = gameState.disabledPlayground;

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

	/**
	 * Initialize the fade effect for displaying the info-label.
	 */
	private void initializeFade() {
		fadeIn.setNode(_infoLabel);
		fadeIn.setFromValue(0.6);
		fadeIn.setToValue(0.3);
		fadeIn.setCycleCount(1);
		fadeIn.setAutoReverse(false);
	}

	/**
	 * Update the context with the current playground state.
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
	 * Update the context with the bets and calculate the new balance.
	 */
	private void updateContext() {
		Context.getContext().setAiBid(Context.getContext().getBrain().getNextBid());
		Context.getContext().setPlayerBid((int) getBetSlider().getValue());
		Context.getContext().setBalance();
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
		} catch (Exception ex) {
			Context.getContext().handleException(ex);
		}
	}

	/**
	 * Switch to the bet mode and disable the grid.
	 */
	private void switchToBetMode() {
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
		getBetSlider().setMax(
				Double.parseDouble(getCreditLabelPlayer().getText()));
		currentState = gameState.betState;
	}

	/**
	 * Switch to the move mode and enable the grid.
	 */
	private void switchToMoveMode() {
		for (Button btn : getTicTacToeGrid()) {
			if (btn.getText().equals("")) {
				btn.setDisable(false);
			}
		}
		getBetButton().setDisable(true);
		getBetSlider().setDisable(true);
		currentState = gameState.moveStatePlayer;
	}

	/**
	 * Switch to the game-end-mode. The GUI-play-elements are getting disabled.
	 */
	private void switchToDisabledMode() {
		for (Button btn : getTicTacToeGrid()) {
			btn.setDisable(true);
		}
		getBetButton().setDisable(true);
		getBetSlider().setDisable(true);
	}

	/**
	 * Resets the playground to the default values.
	 */
	private void resetPlayground() {
		for (Button button : getTicTacToeGrid()) {
			button.setText("");
		}
		getCreditLabelAi().setText("8");
		getCreditLabelPlayer().setText("8");
		Context.getContext().resetContext();
		switchToBetMode();
		getInfoLabel().setVisible(false);
		currentState = gameState.betState;
	}

	/**
	 * Show an Info Message as splash screen which disappears after 0.6 seconds.
	 */
	private void showInfoMsg(String message) {
		_infoLabel.setText(message);
		_infoLabel.setVisible(true);

		// Prevent user input while evaluating.
		switchToDisabledMode();

		if (currentState != gameState.disabledPlayground) {
			fadeIn.onFinishedProperty().set(onFadeFinishedEvent());
			fadeIn.playFromStart();
		} else {
			_infoLabel.setOpacity(0.6);
		}
	}

	/**
	 * Handles the fade finished event.
	 */
	private EventHandler<ActionEvent> onFadeFinishedEvent() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				_infoLabel.setText("");
				_infoLabel.setVisible(false);
				updateGameState();
			}
		};
	}

	/**
	 * Updates the current state according to the game situation.
	 */
	private void showMessage() {
		switch (currentState) {
		case betState:
			if (Context.getContext().isDraw()) {
				showInfoMsg("Unentschieden.");
				currentState = gameState.drawState;
				return;
			} else if (Context.getContext().isAiWinner()) {
				showInfoMsg("Computer hat das Spiel gewonnen.");
				try {
					setAiMove(Context.getContext().getBrain().getNextMove());
				} catch (InvalidActivityException exception) {
					Context.getContext().handleException(exception);
				}
				currentState = gameState.moveStateAi;
			} else if (Context.getContext().isPlayerWinner()) {
				showInfoMsg("Player hat das Spiel gewonnen.");
			}
			break;
		default:
			return;
		}
	}

	/**
	 * Checks if the game has a winner or switch to the next state.
	 */
	private void updateGameState() {
		updatePlayground();

		// Check for a winner.
		if (Context.getContext().isPlayerGameWinner()) {
			switchToDisabledMode();
			currentState = gameState.disabledPlayground;
			showInfoMsg("Spieler hat das Spiel gewonnen.");

			if (Context.getContext().getBrain().getClass() == EasyBrain.class) {
				SettingHelper.getInstance().addPlayerEasyWin();
			} else {
				SettingHelper.getInstance().addPlayerHardWin();
			}
			return;
		} else if (Context.getContext().isAiGameWinner()) {
			switchToDisabledMode();
			currentState = gameState.disabledPlayground;
			showInfoMsg("Computer hat das Spiel gewonnen.");

			if (Context.getContext().getBrain().getClass() == EasyBrain.class) {
				SettingHelper.getInstance().addPlayerEasyLose();
			} else {
				SettingHelper.getInstance().addPlayerHardLose();
			}
			return;
		} else if (Context.getContext().isDrawGame()) {
			switchToDisabledMode();
			currentState = gameState.disabledPlayground;
			showInfoMsg("Unentschieden.");
			return;
		}

		// If no one has win the game yet switch the mode
		switch (currentState) {
		case betState:
			switchToMoveMode();
			break;
		case moveStatePlayer:
		case moveStateAi:
		case drawState:
			switchToBetMode();
			break;
		case disabledPlayground:
		default:
			return;
		}
	}

	/**
	 * Possible game states.
	 */
	private enum gameState {
		disabledPlayground, moveStatePlayer, betState, moveStateAi, drawState,
	}

	/**
	 * Handles the Click-Event fired by the bet-button.
	 */
	@FXML
	private void onBetClick(ActionEvent e) {
		updatePlayground();
		updateContext();

		// Update the GUI with the new values.
		getCreditLabelAi().setText(String.valueOf(Context.getContext().getAiCredits()));
		getCreditLabelPlayer().setText(String.valueOf(Context.getContext().getPlayerCredits()));

		// Adjust the game state.
		showMessage();
	}

	/**
	 * Handles the Click-Event fired by the grid-button if a move by the player
	 * was made.
	 */
	@FXML
	private void onGridButtonClick(ActionEvent e) {
		Button btn = (Button) e.getSource();
		btn.setText("O");
	}

	/**
	 * Handles the Click-Event fired by the new easy game menu button.
	 */
	@FXML
	private void onNewEasyGameClick() {
		initializeFade();
		Context.getContext().setBrain(new EasyBrain());
		Context.getContext().resetContext();
		resetPlayground();
	}

	/**
	 * Handles the Click-Event fired by the new hard game menu button.
	 */
	@FXML
	private void onNewHardGameClick() {
		initializeFade();
		Context.getContext().setBrain(new HardBrain());
		Context.getContext().resetContext();
		resetPlayground();
	}

	/**
	 * Handles the Click-Event fired by the settings menu button.
	 * 
	 * @throws IOException
	 */
	@FXML
	private void onSettingsClick() {
		FxmlHelper.getInstance().showModalDialog("SettingsView.fxml", "Einstellungen");
	}

	/**
	 * Handles the Click-Event fired by the about menu button.
	 * 
	 * @throws IOException
	 */
	@FXML
	private void onAboutClick() {
		FxmlHelper.getInstance().showModalDialog("AboutView.fxml", "�ber das Spiel");
	}

	/**
	 * Handles the Click-Event fired by the statics menu button.
	 */
	@FXML
	private void onStatisticClick() {
		FxmlHelper.getInstance().showModalDialog("StatisticView.fxml", "Statistik");
	}

	/**
	 * Event Handler if the Menu Button exit is clicked.
	 */
	@FXML
	private void onFinishClick() {
		Platform.exit();
	}

	@FXML
	private void onExceptionClick() {
		try {
			String nan = "nan";
			Integer.parseInt(nan);
		} catch (NumberFormatException ex) {
			Context.getContext().handleException(ex);
		}
	}
		
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {	
		for (Button btn : getTicTacToeGrid()) {
			btn.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					updatePlayground();
					updateGameState();
				}
			});
		}
	}
}
