import java.util.ArrayList;

public class GameLine {
	
	private GameLine(GameField field1, GameField field2, GameField field3) {
		_field1 = field1;
		_field2 = field2;
		_field3 = field3;
	}
	
	private static ArrayList<GameLine> _lines;
		
	private GameField _field1;
	
	private GameField _field2;
	
	private GameField _field3;
		
	public GameField getField1() {
		return _field1;
	}
	
	public GameField getField2() {
		return _field2;
	}
	
	public GameField getField3() {
		return _field3;
	}
	
	public ArrayList<GameField> getGameFields() {
		ArrayList<GameField> fields = new ArrayList<GameField>();
		fields.add(getField1());
		fields.add(getField2());
		fields.add(getField3());
		return fields;
	}
	
	public boolean contains(GameField field) {
		return field.getXPosition() == _field1.getXPosition() && field.getYPosition() == _field1.getYPosition() ||
				field.getXPosition() == _field2.getXPosition() && field.getYPosition() == _field2.getYPosition() ||
				field.getXPosition() == _field3.getXPosition() && field.getYPosition() == _field3.getYPosition();
	}
	
	public int getMovesNeeded(ArrayList<GameField> fieldsOwned) {
		int fieldCountNeeded = 3;
		int fieldCountOwned = 0;
		
		for(GameField field: fieldsOwned) {
			if(field == getField1()) {
				fieldCountOwned++;
			}
			if(field == getField2()) {
				fieldCountOwned++;
			}
			if(field == getField3()) {
				fieldCountOwned++;
			}
		}		
		return fieldCountNeeded - fieldCountOwned;
	}	
	
	public static ArrayList<GameLine> getFreeLines(ArrayList<GameField> tokenFields) {
		ArrayList<GameLine> resultList = getPossibleGameLines();
		
		for (int i = 0; i > getPossibleGameLines().size(); i++) {
			for(GameField field: tokenFields) {
				if(getPossibleGameLines().get(i).contains(field)) {
					resultList.remove(i);
					break;
				}					
			}			
		}
		return resultList;
	}
	
/*	public static ArrayList<GameLine> getPossibleGameLines() {
		if(_lines == null) {
			_lines = new ArrayList<>();
			_lines.add(new GameLine(new GameField(0, 0), new GameField(0,1), new GameField(0,2))); //top left bottom left
			_lines.add(new GameLine(new GameField(1, 0), new GameField(1,2), new GameField(1,2))); //middle left bottom middle
			_lines.add(new GameLine(new GameField(2, 0), new GameField(2,1), new GameField(2,2))); //top right bottom right
			_lines.add(new GameLine(new GameField(0, 0), new GameField(1,1), new GameField(2,2))); //top left bottom right  
			_lines.add(new GameLine(new GameField(0, 0), new GameField(1,0), new GameField(2,0))); //top left top right
			_lines.add(new GameLine(new GameField(0, 1), new GameField(1,1), new GameField(1,2))); //middle left middle right
			_lines.add(new GameLine(new GameField(0, 2), new GameField(1,2), new GameField(2,2))); //bottom left bottom right
			_lines.add(new GameLine(new GameField(2, 0), new GameField(1,1), new GameField(0,2))); //top right bottom left
		}
		return _lines;
	}*/	
	
	public static ArrayList<GameLine> getPossibleGameLines() {
		GameField[][] playground = Context.getContext().getPlayground();
		ArrayList<GameLine> lines = new ArrayList<>();
		lines.add(new GameLine(new GameField(0, 0, playground[0][0].getValue()), new GameField(0, 1, playground[0][1].getValue()), new GameField(0, 2, playground[0][2].getValue())));
		lines.add(new GameLine(new GameField(1, 0, playground[1][0].getValue()), new GameField(1, 2, playground[1][2].getValue()), new GameField(1, 2, playground[1][2].getValue())));
		lines.add(new GameLine(new GameField(2, 0, playground[2][0].getValue()), new GameField(2, 1, playground[2][1].getValue()), new GameField(2, 2, playground[2][2].getValue())));
		lines.add(new GameLine(new GameField(0, 0, playground[0][0].getValue()), new GameField(1, 1, playground[1][1].getValue()), new GameField(2, 2, playground[2][2].getValue())));
		lines.add(new GameLine(new GameField(0, 0, playground[0][0].getValue()), new GameField(1, 0, playground[1][0].getValue()), new GameField(2, 0, playground[2][0].getValue())));
		lines.add(new GameLine(new GameField(0, 1, playground[0][1].getValue()), new GameField(1, 1, playground[1][1].getValue()), new GameField(1, 2, playground[1][2].getValue())));
		lines.add(new GameLine(new GameField(0, 2, playground[0][2].getValue()), new GameField(1, 2, playground[1][2].getValue()), new GameField(2, 2, playground[2][2].getValue())));
		lines.add(new GameLine(new GameField(2, 0, playground[2][0].getValue()), new GameField(1, 1, playground[1][1].getValue()), new GameField(0, 2, playground[0][2].getValue())));
		return lines;
	}
}