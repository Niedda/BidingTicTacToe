package nif.tictactoe.model;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: Represents a {@link GameField} and offers some basic methods to deal with them.
 */
public class GameField {
	private String _value;
	private int _xPosition;
	private int _yPosition;
	
	public int getYPosition() {
		return _yPosition;
	}

	public int getXPosition() {
		return _xPosition;
	}

	public String getValue() {
		return _value;
	}
	
	/**
	 * Public constructor for creating a game-field. 
	 * @param xPosition the x-position on the grid
	 * @param yPosition the y-position on the grid
	 */
	public GameField(int xPosition, int yPosition) {
		_xPosition = xPosition;
		_yPosition = yPosition;
	}

	/**
	 * Public constructor for creating a game-field with a value. 
	 * @param xPosition the x-position on the grid
	 * @param yPosition the y-position on the grid
	 * @param value the current value of the field
	 */
	public GameField(int xPosition, int yPosition, String value) {
		_value = value;
		_xPosition = xPosition;
		_yPosition = yPosition;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(GameField.class)) {
			GameField comp = (GameField) obj;
			return (comp.getXPosition() == getXPosition() && comp.getYPosition() == getYPosition());
		}
		return false;
	}
}
