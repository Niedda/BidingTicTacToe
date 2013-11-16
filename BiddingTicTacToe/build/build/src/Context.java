import java.util.ArrayList;

public class Context {

	private Context() {
		//NOP
	}
	
	private static Context _context;
	
	private SuperBrain _brain;
				
	private GameField[][] _playground;
	
	private int _aiBid;
	
	private int _plBid;
	
	private int _aiCredits;
	
	private int _plCredits;
	
	public void setAiCredits(int credits) {
		_aiCredits = credits;
	}
	
	public int getAiCredits() {
		return _aiCredits;
	}
	
	public void setPlayerCredits(int credits) {
		_plCredits = credits;
	}
	
	public int getPlayerCredits() {
		return _plCredits;
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
		} else {
			setAiCredits(getAiCredits() - getAiBid());
			setPlayerCredits(getPlayerCredits() + getAiBid());
		}
	}
	
	public static Context getContext() {
		if(_context == null) {
			_context = new Context();
		}
		return _context;
	}
		
	public void setBrain(SuperBrain brain) {
		_brain = brain;
	}
	
	public SuperBrain getBrain() {
		return _brain;
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
		ArrayList<GameField> aiFields = Context.getContext().getBrain().getAiFields();
						
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
	
	public void resetContext() {
		setAiCredits(8);
		setPlayerCredits(8);		
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

	public int getPlayerBid() {
		return _plBid;
	}
	
	public int getAiBid() {
		return _aiBid;
	}
	
	public void setPlayground(GameField[][] playground) {
		_playground = playground;
	}
	
	public GameField[][] getPlayground() {
		return _playground;
	}
	
	public String getBetResultMessage() {
    	if(Context.getContext().isDraw()) {
   		 return "Unentschieden!";   		 
    	}
    	setBalance();
		if(isPlayerWinner()) {
			return String.format("Computer bietet %s - Du gewinnst!", getAiBid());
		} else {
			return String.format("Computer bietet %s und gewinnt!", getAiBid());
		} 
	}
}