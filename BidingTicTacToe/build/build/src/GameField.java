public class GameField {
	
	private String _value;
	
	private int _xPosition;

	private int _yPosition;
	
	public GameField(int xPosition, int yPosition) {
		_xPosition = xPosition;
		_yPosition = yPosition;		
	}
	
	public GameField(int xPosition, int yPosition, String value ) {
		_value = value;
		_xPosition = xPosition;
		_yPosition = yPosition;		
	}
		
	public int getYPosition() {
		return _yPosition;
	}
	
	public int getXPosition() {
		return _xPosition;
	}
	
	public String getValue() {
		return _value;
	}	
}
