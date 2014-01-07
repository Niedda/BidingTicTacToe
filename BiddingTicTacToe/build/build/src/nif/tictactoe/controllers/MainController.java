package nif.tictactoe.controllers;

import java.lang.reflect.Field;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.UUID;

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
	private ArrayList<Integer> _cachedBids;
	private FadeTransition fadeIn;
	private gameState currentState = gameState.disabledPlayground;
	private gameState nextState;

	private enum gameState {
		disabledPlayground, moveStatePlayer, betState, moveStateAi, drawState, gameFinished,
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
			_ticTacToeGrid = new Button[] { _field00, _field01, _field02, _field10, _field11, _field12, _field20, _field21, _field22, };
		}
		return _ticTacToeGrid;
	}

	private boolean isHardGame() {
		return Context.getContext().getBrain().getClass() == HardBrain.class;
	}

	private void initializeFade() {
		fadeIn = new FadeTransition(Duration.millis(SettingHelper.getInstance().loadSplashDuration()));
		fadeIn.setNode(_infoLabel);
		fadeIn.setFromValue(0.6);
		fadeIn.setToValue(0.6);
		fadeIn.setCycleCount(1);
		fadeIn.setAutoReverse(false);
	}

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

	private void updateContext() {
		Context.getContext().setAiBid(Context.getContext().getBrain().getNextBid());
		Context.getContext().setPlayerBid((int) getBetSlider().getValue());
		Context.getContext().setBalance();
	}

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
		getBetSlider().setMax(Double.parseDouble(getCreditLabelPlayer().getText()));
		currentState = gameState.betState;
	}

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

	private void switchToDisabledMode() {
		for (Button btn : getTicTacToeGrid()) {
			btn.setDisable(true);
		}
		getBetButton().setDisable(true);
		getBetSlider().setDisable(true);
		currentState = gameState.disabledPlayground;
	}

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

	private void showInfoMsg(String message) {
		_infoLabel.setText(message);
		_infoLabel.setVisible(true);

		// Prevent user input while evaluating.
		switchToDisabledMode();

		fadeIn.onFinishedProperty().set(onFadeFinishedEvent());
		fadeIn.playFromStart();
	}

	private EventHandler<ActionEvent> onFadeFinishedEvent() {
		return new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				if (nextState != gameState.gameFinished) {
					_infoLabel.setText("");
					_infoLabel.setVisible(false);
					updateGameState();
				}
			}
		};
	}

	private void showMessage() {
		if (currentState == gameState.betState) {
			if (Context.getContext().isDraw()) {
				showInfoMsg("Unentschieden.");
				nextState = gameState.drawState;
				return;
			} else if (Context.getContext().isAiWinner()) {
				showInfoMsg(String.format("Computer bietet %s %nSpieler bietet %s %nComputer darf setzten.", Context.getContext().getAiBid(), Context.getContext().getPlayerBid()));
				nextState = gameState.moveStateAi;
			} else if (Context.getContext().isPlayerWinner()) {
				showInfoMsg(String.format("Computer bietet %s %nSpieler bietet %s %nSpieler darf setzten.", Context.getContext().getAiBid(), Context.getContext().getPlayerBid()));
				nextState = gameState.moveStatePlayer;
			}
		}
	}

	private void updateGameState() {
		updatePlayground();

		if (nextState == gameState.gameFinished) {
			return;
		}

		// Check for a winner.
		if (Context.getContext().isPlayerGameWinner()) {
			switchToDisabledMode();
			showInfoMsg("Spieler gewinnt!");

			if (Context.getContext().getBrain().getClass() == EasyBrain.class) {
				SettingHelper.getInstance().addPlayerEasyWin();
			} else {
				int[] _bids = new int[_cachedBids.size()];

				for (int i = 0; i < _cachedBids.size(); i++) {
					_bids[i] = _cachedBids.get(i).intValue();
				}

				ImmitatorScheduler.getInstance().saveNewBidStrategyIfPossible(new ImmitatorBettingStrategy(_bids, 0, 0, UUID.randomUUID()));
				Context.getContext().getBrain().saveAiLose();
				SettingHelper.getInstance().addPlayerHardWin();
			}
			nextState = gameState.gameFinished;
			return;
		} else if (Context.getContext().isAiGameWinner()) {
			switchToDisabledMode();
			showInfoMsg("Computer gewinnt!");

			if (Context.getContext().getBrain().getClass() == EasyBrain.class) {
				SettingHelper.getInstance().addPlayerEasyLose();
			} else {
				Context.getContext().getBrain().saveAiWin();
				SettingHelper.getInstance().addPlayerHardLose();
			}
			nextState = gameState.gameFinished;
			return;
		} else if (Context.getContext().isDrawGame()) {
			switchToDisabledMode();
			showInfoMsg("Unentschieden.");
			nextState = gameState.gameFinished;
			return;
		}

		// If no one has win the game yet switch the mode
		switch (nextState) {
		case moveStateAi:
			try {
				setAiMove(Context.getContext().getBrain().getNextMove());
			} catch (InvalidActivityException exception) {
				Context.getContext().handleException(exception);
			}
			nextState = gameState.betState;
			updateGameState();
			break;
		case moveStatePlayer:
			switchToMoveMode();
			break;
		case betState:
		case drawState:
			switchToBetMode();
			break;
		default:
			return;
		}
	}

	/**
	 * Handles the Click-Event fired by the bet-button.
	 */
	@FXML
	private void onBetClick(ActionEvent e) {
		updatePlayground();
		updateContext();

		if (isHardGame()) {
			_cachedBids.add(Context.getContext().getPlayerBid());
		}

		getCreditLabelAi().setText(String.valueOf(Context.getContext().getAiCredits()));
		getCreditLabelPlayer().setText(String.valueOf(Context.getContext().getPlayerCredits()));
		showMessage();
	}

	/**
	 * Handles the Click-Event fired by the grid-button if a move by the player
	 * was made.
	 */
	@FXML
	private void onGridButtonClick(ActionEvent e) {
		switchToDisabledMode();
		Button btn = (Button) e.getSource();
		btn.setText("O");
		nextState = gameState.betState;
		updateGameState();
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
		_cachedBids = new ArrayList<Integer>();
		initializeFade();
		Context.getContext().setBrain(new HardBrain());
		Context.getContext().resetContext();
		resetPlayground();
	}

	/**
	 * Handles the Click-Event fired by the settings menu button. *
	 */
	@FXML
	private void onSettingsClick() {
		FxmlHelper.getInstance().showModalDialog("SettingsView.fxml", "Einstellungen");
	}

	/**
	 * Handles the Click-Event fired by the about menu button.
	 */
	@FXML
	private void onAboutClick() {
		FxmlHelper.getInstance().showModalDialog("AboutView.fxml", "Über das Spiel");
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
					if (currentState == gameState.gameFinished) {
						updatePlayground();
						updateGameState();
					}
				}
			});
		}
	}
}
