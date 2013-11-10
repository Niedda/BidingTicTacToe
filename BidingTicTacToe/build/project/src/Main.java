import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.SwingUtilities;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Main extends Application {

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
	
	private ArrayList<Button> _ticTacToeGrid;

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
	
	private void setAiMove(GameField field) {
		//TODO: ugly -.-
		switch (field.getXPosition())
		{
		case 0:
			switch (field.getYPosition())
			{	
			case 0:
				_field00.setText("X");
				return;
			case 1:
				_field01.setText("X");
				return;
			case 2:
				_field02.setText("X");
				return;
			}
		case 1:
			switch (field.getYPosition())
			{	
			case 0:
				_field00.setText("X");
				return;
			case 1:
				_field01.setText("X");
				return;
			case 2:
				_field02.setText("X");
				return;
			}
		case 2:
			switch (field.getYPosition())
			{	
			case 0:
				_field00.setText("X");
				return;
			case 1:
				_field01.setText("X");
				return;
			case 2:
				_field02.setText("X");
				return;
			}
		}
	}
	
	public ArrayList<Button> getTicTacToeGrid() {
		if(_ticTacToeGrid == null) {
			_ticTacToeGrid = new ArrayList<Button>() {{
				add(_field00);
				add(_field01);
				add(_field02);
				add(_field10);
				add(_field11);
				add(_field12);
				add(_field20);
				add(_field21);
				add(_field22);
			}};
		}
    	return _ticTacToeGrid;
	}
	
	public MenuItem getEasyGameMenuItem() {
		return _newEasyGameMenuItem;
	}
	
	public MenuItem getHardGameMenuItem() {
		return _newHardGameMenuItem;
	}
		
	public Button getBetButton() {
		return _betButton;
	}
	
	public Slider getBetSlider() {
		return _betSlider;
	}
	
	public Label getCreditLabelAi() {
		return _aiCredit;
	}
	
	public Label getCreditLabelPlayer() {
		return _plCredit;
	}
	
	public Label getInfoLabel() {
		return _infoLabel;
	}
	
	public static void main(String[] args) {
		Application.launch(Main.class, args);
	}

	@Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("MainView.fxml"));
        stage.setTitle("Biding Tic Tac Toe");
        stage.setScene(new Scene(root));
        stage.show();
    }    
    
    public void onBetClick(ActionEvent e) {
    	updatePlayground();
    	Context.getContext().setAiBid(Context.getContext().getBrain().getNextBid());
    	Context.getContext().setPlayerBid((int)getBetSlider().getValue());
    	showInfoMsg(Context.getContext().getBetResultMessage());
		getCreditLabelAi().setText(String.valueOf(Context.getContext().getAiCredits()));
		getCreditLabelPlayer().setText(String.valueOf(Context.getContext().getPlayerCredits()));
			
		if(Context.getContext().isAiWinner()) {
    		GameField field = Context.getContext().getBrain().getNextMove();
    		setAiMove(field);
			switchToBetMode();
	    	showWinMsg();
		} else if(Context.getContext().isPlayerWinner()) {
    		switchToMoveMode();
		}	
    }
    
    public void onGridButtonClick(ActionEvent e) {
    	Button btn = (Button) e.getSource();
    	btn.setText("O");
    	updatePlayground();
    	switchToBetMode();    	
    	showWinMsg();
    }    
          
    /*@
     * Resets the playground, switch to the bet mode and set AI to easy.
     */
    public void onNewEasyGameClick() {
    	Context.getContext().setBrain(new EasyBrain());
    	Context.getContext().resetContext();
    	resetPlayground();
    	switchToBetMode();
    	getInfoLabel().setVisible(false);
    }
    
    /*@
     * Resets the playground, switch to the bet mode and set AI to hard.
     */
    public void onNewHardGameClick() {
    	Context.getContext().setBrain(new HardBrain());
    	Context.getContext().resetContext();
    	resetPlayground();
    	switchToBetMode();
    	getInfoLabel().setVisible(true);
    }
    
    public void onSettingsClick() {
    
    }
    
    public void onAboutClick() {

    	
    }
    
    public void onStatisticClick() {
    	
    }
    
    /*@
     * Switch to the Bet-Mode the Button-Grid gets disabled and the context-playground updated.
     */
    public void switchToBetMode() {
    	for(Button btn: getTicTacToeGrid()) {
    		btn.setDisable(true);
    	}    	
    	getBetButton().setDisable(false);
    	getBetSlider().setDisable(false);
    	getBetSlider().setMax(Double.parseDouble(getCreditLabelPlayer().getText()));
    	updatePlayground();
    }
    
    /*@
     * Switch to the Move-Mode the Button-Grid gets enabled and the Slider and Bet-Button disabled.
     */
    public void switchToMoveMode() {
    	for(Button btn: getTicTacToeGrid()) {
    		if(btn.getText().equals("")) {
    			btn.setDisable(false);
    		}
    	}    	
    	getBetButton().setDisable(true);
    	getBetSlider().setDisable(true); 	
    }
    
    public void switchToGameEndMode() {
    	for(Button btn: getTicTacToeGrid()) {
    		btn.setDisable(true);
    	}
    	getBetButton().setDisable(true);
    	getBetSlider().setDisable(true);
    }
    
    public void resetPlayground() {
    	for(Button button: getTicTacToeGrid()) {
    		button.setText("");
    	}
    	getCreditLabelAi().setText("8");
    	getCreditLabelPlayer().setText("8");
    }
    
    public void onFinishClick() throws Exception {
    	Platform.exit();    	
    }
    
    public void showInfoMsg(String message) {
    	_infoLabel.setText(message);
    	_infoLabel.setVisible(true);
    	
    	Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater(new Runnable() {
                    @Override
                    public void run() {
                    	_infoLabel.setText("");
                    	_infoLabel.setVisible(false);
                    }
               });
            }
        }, 1500);    	  	
    }
    
    public void showWinMsg() {
	    	Timer timer = new Timer();
	        timer.schedule(new TimerTask() {
	            @Override
	            public void run() {
	            	Platform.runLater(new Runnable() {
	                    @Override
	                    public void run() {
	                    	updatePlayground();
	                    	if(Context.getContext().isAiGameWinner()) {
	                    		_infoLabel.setText("Der Computer hat das Spiel gewonnen!");
	                    		_infoLabel.setVisible(true);
		                      switchToGameEndMode();		                      
	                    	} else if (Context.getContext().isPlayerGameWinner()) {
	                    		_infoLabel.setText("Du hast das Spiel gewonnen!");
			                    _infoLabel.setVisible(true);
			                    switchToGameEndMode();
	                    	}
	                    }
	               });
	            }
	        }, 1600);    	
    }
}
