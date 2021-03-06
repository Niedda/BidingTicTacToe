package nif.tictactoe.model;

import java.util.ArrayList;

import nif.tictactoe.Context;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: Represents a {@link GameLine} and offers some basic functions
 *               to deal with them. A {@link GameLine} always contains 3
 *               {@link GameField}.
 */
public class GameLine {

	private GameLine(GameField field1, GameField field2, GameField field3) {
		_field1 = field1;
		_field2 = field2;
		_field3 = field3;
	}

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

	/**
	 * Get the {@link GameField} of the line.
	 * 
	 * @return {@link GameField}
	 */
	public ArrayList<GameField> getGameFields() {
		ArrayList<GameField> fields = new ArrayList<GameField>();
		fields.add(getField1());
		fields.add(getField2());
		fields.add(getField3());
		return fields;
	}

	/**
	 * Evaluates if the line contains the {@link GameField}.
	 * 
	 * @param field
	 *            The {@link GameField} to check for.
	 * @return boolean
	 */
	public boolean contains(GameField field) {
		return field.getXPosition() == _field1.getXPosition() 
				&& field.getYPosition() == _field1.getYPosition()
				|| field.getXPosition() == _field2.getXPosition()
				&& field.getYPosition() == _field2.getYPosition()
				|| field.getXPosition() == _field3.getXPosition()
				&& field.getYPosition() == _field3.getYPosition();
	}

	/**
	 * Get the minimum moves needed to win the game.
	 * 
	 * @param fieldsOwned
	 *            The fields owned.
	 */
	public int getMovesNeeded(ArrayList<GameField> fieldsOwned) {
		int fieldCountNeeded = 3;
		int fieldCountOwned = 0;

		for (GameField field : fieldsOwned) {
			if (field.equals(getField1())) {
				fieldCountOwned++;
			}
			if (field.equals(getField2())) {
				fieldCountOwned++;
			}
			if (field.equals(getField3())) {
				fieldCountOwned++;
			}
		}
		return fieldCountNeeded - fieldCountOwned;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj.getClass().equals(GameLine.class)) {
			GameLine comp = (GameLine) obj;
			if (comp.getField1().getXPosition() == getField1().getXPosition()
					&& comp.getField1().getYPosition() == getField1().getYPosition()
					&& comp.getField2().getXPosition() == getField2().getXPosition()
					&& comp.getField2().getYPosition() == getField2().getYPosition()
					&& comp.getField3().getXPosition() == getField3().getXPosition()
					&& comp.getField3().getYPosition() == getField3().getYPosition()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Get the available {@link GameLine} still available.
	 * 
	 * @param tokenFields
	 *            The fields already token by the player.
	 * @return A list of {@link GameLine}.
	 */
	public static ArrayList<GameLine> getFreeLines(
			ArrayList<GameField> tokenFields) {
		if (tokenFields.size() == 0) {
			// If there's no field taken just return the whole list.
			return getPossibleGameLines();
		}
		ArrayList<GameLine> resultList = getPossibleGameLines();
		ArrayList<GameLine> lines = getPossibleGameLines();

		// Check the line versus the token fields.
		for (int i = 0; i < 8; i++) {
			for (GameField field : tokenFields) {
				// If the line contains a token field remove it from the result
				if (lines.get(i).contains(field)) {
					GameLine.removeLine(resultList, lines.get(i));
					break;
				}
			}
		}
		return resultList;
	}

	/**
	 * Get the list of all possible {@link GameLine}.
	 * 
	 * @return A list of {@link GameLine}.
	 */
	public static ArrayList<GameLine> getPossibleGameLines() {
		GameField[][] playground = Context.getContext().getPlayground();
		ArrayList<GameLine> lines = new ArrayList<>();
		
		//top left - bottom right
		lines.add(new GameLine(
				new GameField(0, 0, playground[0][0].getValue()),
				new GameField(1, 1, playground[1][1].getValue()),
				new GameField(2, 2, playground[2][2].getValue())));
		
		//top right - bottom left
		lines.add(new GameLine(
				new GameField(2, 0, playground[2][0].getValue()),
				new GameField(1, 1, playground[1][1].getValue()),
				new GameField(0, 2, playground[0][2].getValue())));

		//vertical from left to right
		lines.add(new GameLine(
				new GameField(0, 0, playground[0][0].getValue()),
				new GameField(0, 1, playground[0][1].getValue()),
				new GameField(0, 2, playground[0][2].getValue())));
		lines.add(new GameLine(
				new GameField(1, 0, playground[1][0].getValue()),
				new GameField(1, 1, playground[1][1].getValue()),
				new GameField(1, 2, playground[1][2].getValue())));
		lines.add(new GameLine(
				new GameField(2, 0, playground[2][0].getValue()),
				new GameField(2, 1, playground[2][1].getValue()),
				new GameField(2, 2, playground[2][2].getValue())));

		//horizontal from top to bottom
		lines.add(new GameLine(
				new GameField(0, 0, playground[0][0].getValue()),
				new GameField(1, 0, playground[1][0].getValue()),
				new GameField(2, 0, playground[2][0].getValue())));
		lines.add(new GameLine(
				new GameField(0, 1, playground[0][1].getValue()),
				new GameField(1, 1, playground[1][1].getValue()),
				new GameField(2, 1, playground[2][1].getValue())));
		lines.add(new GameLine(
				new GameField(0, 2, playground[0][2].getValue()),
				new GameField(1, 2, playground[1][2].getValue()),
				new GameField(2, 2, playground[2][2].getValue())));
		return lines;
	}

	public static ArrayList<GameLine> removeLine(ArrayList<GameLine> input, GameLine toRemove) {
		for (GameLine line : input) {
			if (line.equals(toRemove)) {
				input.remove(line);
				break;
			}
		}
		return input;
	}
}