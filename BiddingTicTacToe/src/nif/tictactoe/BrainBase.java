package nif.tictactoe;

import java.util.ArrayList;

import nif.tictactoe.model.*;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: The basic abstract class for all computer-players.
 */
public abstract class SuperBrain {

	// Privates
	private int _fieldsToWinAi;

	private int _fieldsToWinPlayer;

	// Setters
	private void setFieldsToWinAi(int fields) {
		_fieldsToWinAi = fields;
	}

	private void setFieldsToWinPl(int fields) {
		_fieldsToWinPlayer = fields;
	}

	// Getters
	protected final int getFieldsToWinAi() {
		return _fieldsToWinAi;
	}

	protected final int getFieldsToWinPlayer() {
		return _fieldsToWinPlayer;
	}

	// Public
	/**
	 * Get the list of {@link GameField} which are owned by the computer.
	 * @return {@link GameField}
	 */
	protected final ArrayList<GameField> getAiFields() {
		GameField[][] pg = Context.getContext().getPlayground();
		ArrayList<GameField> aiFields = new ArrayList<GameField>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (pg[i][j].getValue() != null
						&& pg[i][j].getValue().equals("X")) {
					aiFields.add(pg[i][j]);
				}
			}
		}
		return aiFields;
	}

	/**
	 * Get the list of {@link GameField} which are owned by the player.
	 * @return {@link GameField}
	 */
	protected final ArrayList<GameField> getPlayerFields() {
		GameField[][] pg = Context.getContext().getPlayground();
		ArrayList<GameField> playerFields = new ArrayList<GameField>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (pg[i][j].getValue() != null
						&& pg[i][j].getValue().equals("O")) {
					playerFields.add(pg[i][j]);
				}
			}
		}
		return playerFields;
	}

	/**
	 * Get the moves to win for the computer and the player.
	 * The updated values can get by the methods 
	 * <b>getFieldsToWinAi</b></br>
	 * <b>getFieldsToWinPlayer</b>
	 */
	protected final void getMovesToWin() {
		GameField[][] pg = Context.getContext().getPlayground();
		ArrayList<GameField> aiFields = new ArrayList<GameField>();
		ArrayList<GameField> playerFields = new ArrayList<GameField>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (pg[i][j].getValue().equals("X")) {
					aiFields.add(pg[i][j]);
				} else if (pg[i][j].getValue().equals("O")) {
					playerFields.add(pg[i][j]);
				}
			}
		}

		ArrayList<GameLine> freeLinesAi = GameLine.getFreeLines(playerFields);
		ArrayList<GameLine> freeLinesPl = GameLine.getFreeLines(aiFields);
		int minMovesNeededAi = 3;
		int minMovesNeededPl = 3;

		for (GameLine line : freeLinesAi) {
			int result = line.getMovesNeeded(aiFields);

			if (result < minMovesNeededAi) {
				minMovesNeededAi = result;
			}
		}
		for (GameLine line : freeLinesPl) {
			int result = line.getMovesNeeded(playerFields);

			if (result < minMovesNeededPl) {
				minMovesNeededPl = result;
			}
		}
		setFieldsToWinAi(minMovesNeededAi);
		setFieldsToWinPl(minMovesNeededPl);
	}

	/**
	 * Calculate the next move from the computer.
	 */
	public abstract GameField getNextMove();

	/**
	 * Calculate the next bid from the computer.
	 * @return integer
	 */
	public abstract int getNextBid();

	/**
	 * Update the brain and calculate the new values.
	 */
	public final void UpdateBrain() {
		getMovesToWin();
	}
}
