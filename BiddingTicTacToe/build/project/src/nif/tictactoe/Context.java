package nif.tictactoe;

import java.util.ArrayList;

import nif.tictactoe.model.*;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: The main class which controls the game flow. Implemented whit the Singleton-Pattern.
 */
public class Context {

	private Context() {
		//NOP
	}
		
	private BrainBase _brain;
	private GameField[][] _playground;
	private int _aiBid;
	private int _plBid;
	private int _aiCredits;
	private int _plCredits;
	private Exception _lastError;
	private static Context _context;
	
	public int getAiCredits() {
		return _aiCredits;
	}

	public int getPlayerBid() {
		return _plBid;
	}
		
	public int getPlayerCredits() {
		return _plCredits;
	}	
		
	public BrainBase getBrain() {
		return _brain;
	}
	
	public int getAiBid() {
		return _aiBid;
	}
	
	public GameField[][] getPlayground() {
		return _playground;
	}	

	public Exception getLastError() {
		return _lastError;
	}
		
	public void setAiCredits(int credits) {
		_aiCredits = credits;
	}

	public void setPlayerCredits(int credits) {
		_plCredits = credits;
	}
	
	public void setAiBid(int bid) {
		_aiBid = bid;
	}
	
	public void setPlayerBid(int bid) {
		_plBid = bid;
	}
	
	public void setBalance() {
		if(isPlayerWinner()) {
			setPlayerCredits(getPlayerCredits() - getPlayerBid());
			setAiCredits(getAiCredits() + getPlayerBid());
		} else if(isAiWinner()) {
			setAiCredits(getAiCredits() - getAiBid());
			setPlayerCredits(getPlayerCredits() + getAiBid());
		}
	}
		
	public void setBrain(BrainBase brain) {
		_brain = brain;
	}

	public void setPlayground(GameField[][] playground) {
		_playground = playground;		
	}
		
	/* 
	 * Handle an unexpected exception an invoke the exception dialog.
	 */
	public void handleException(Exception ex) {
		_lastError = ex;
		FxmlHelper.getInstance().showModalDialog("ErrorView.fxml", "Fehler");
	}	
	
	public boolean isPlayerGameWinner() {
		ArrayList<GameLine> lines = GameLine.getPossibleGameLines();
		ArrayList<GameField> plFields = Context.getContext().getBrain().getPlayerFields();
		
		for(GameLine line: lines) {
			int counter = 0;
			for(GameField field: plFields) {
				if(line.contains(field)) {
					counter++;
				}
			}
			if(counter == 3) {
				return true;
			}			
		}	
		return false;
	}
	
	public boolean isAiGameWinner() {
		ArrayList<GameLine> lines = GameLine.getPossibleGameLines();
		ArrayList<GameField> aiFields = getBrain().getAiFields();
						
		for(GameLine line: lines) {
			int counter = 0;
			for(GameField field: aiFields) {
				if(line.contains(field)) {
					counter++;
				}
			}
			if(counter == 3) {
				return true;
			}			
		}	
		return false;
	}
	
	public boolean isDraw() {
		return _aiBid == _plBid;
	}
	
	public boolean isPlayerWinner() {
		return _aiBid < _plBid;		
	}
	
	public boolean isAiWinner() {
		return _aiBid > _plBid;
	}
	
	public boolean isDrawGame() {
		return GameLine.getFreeLines(new ArrayList<GameField>()).size() == 0;		
	}
	
	public void resetContext() {
		setAiCredits(8);
		setPlayerCredits(8);	
		_aiBid = 0;
		_plBid = 0;
	}
			
	/**
	 * The only existing instance of this class.
	 * @return {@link Context}
	 */
	public static Context getContext() {
		if(_context == null) {
			_context = new Context();
		}
		return _context;
	}
}
