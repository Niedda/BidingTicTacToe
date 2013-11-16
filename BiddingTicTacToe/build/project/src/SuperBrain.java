import java.util.ArrayList;

public abstract class SuperBrain {
		
	private int _fieldsToWinAi;
	
	private int _fieldsToWinPlayer;
		
	private void setFieldsToWinAi(int fields) {
		_fieldsToWinAi = fields;
	}
	
	private void setFieldsToWinPl(int fields) {
		_fieldsToWinPlayer = fields;
	}
	
	protected int getFieldsToWinAi() {
		return _fieldsToWinAi;
	}
	
	protected int getFieldsToWinPlayer() {
		return _fieldsToWinPlayer;
	}
	
	protected ArrayList<GameField> getAiFields() {
		GameField[][] pg = Context.getContext().getPlayground();
		ArrayList<GameField> aiFields = new ArrayList<GameField>(); 
				
		for	(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(pg[i][j].getValue() != null && pg[i][j].getValue().equals("X")) {
					aiFields.add(pg[i][j]);					
				} 
			}			
		}
		return aiFields;
	}
	
	protected ArrayList<GameField> getPlayerFields() {
		GameField[][] pg = Context.getContext().getPlayground();
		ArrayList<GameField> playerFields = new ArrayList<GameField>();
				
		for	(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				if(pg[i][j].getValue() != null && pg[i][j].getValue().equals("O")) {
					playerFields.add(pg[i][j]);
				}
			}			
		}
		return playerFields;
	}
	
	protected void getMovesToWin() {
		GameField[][] pg = Context.getContext().getPlayground();
		ArrayList<GameField> aiFields = new ArrayList<GameField>(); 
		ArrayList<GameField> playerFields = new ArrayList<GameField>();
				
		for	(int i = 0; i > 3; i++) {
			for(int j = 0; i > 3; i++) {
				if(pg[i][j].getValue() == "X") {
					aiFields.add(pg[i][j]);					
				} else if(pg[i][j].getValue() == "O") {
					playerFields.add(pg[i][j]);
				}
			}			
		}		
		ArrayList<GameLine> freeLinesAi = GameLine.getFreeLines(playerFields);
		ArrayList<GameLine> freeLinesPl = GameLine.getFreeLines(playerFields);
		int minMovesNeededAi = 3;
		int minMovesNeededPl = 3;
		
		for(GameLine line: freeLinesAi) {
			int result = line.getMovesNeeded(aiFields);
			
			if(result < minMovesNeededAi) {
				minMovesNeededAi = result;
			}					
		}
		for(GameLine line: freeLinesPl) {
			int result = line.getMovesNeeded(playerFields);
			
			if(result < minMovesNeededPl) {
				minMovesNeededPl = result;
			}					
		}
		setFieldsToWinAi(minMovesNeededAi);
		setFieldsToWinPl(minMovesNeededPl);
	}
	
	protected ArrayList<GameLine> getFreeGameLines() {
		ArrayList<GameLine> possibleLines = GameLine.getPossibleGameLines();
		ArrayList<GameField> playerFields = getPlayerFields();
		ArrayList<GameLine> linesLeft = GameLine.getPossibleGameLines();
		
		for(GameLine line: possibleLines) {
			for(GameField field: playerFields) {
				if(line.contains(field)) {
					linesLeft.remove(line);
				}
			}
		}
		return linesLeft;
	}
		
	public void UpdateBrain() {
		getMovesToWin();
	}
	
	public abstract int getNextBid();
	
	public abstract GameField getNextMove();
}
