package nif.tictactoe;

import java.util.ArrayList;

import javax.activity.InvalidActivityException;

import nif.tictactoe.model.*;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: The basic abstract class for all computer-players.
 */
public abstract class BrainBase {

	private int _fieldsToWinAi;
	private int _fieldsToWinPlayer;
	protected IStrategy _strategy;

	private void setFieldsToWinAi(int fields) {
		_fieldsToWinAi = fields;
	}

	private void setFieldsToWinPl(int fields) {
		_fieldsToWinPlayer = fields;
	}

	protected final int getFieldsToWinAi() {
		return _fieldsToWinAi;
	}

	protected final int getFieldsToWinPlayer() {
		return _fieldsToWinPlayer;
	}

	/**
	 * Update the brain and calculate the new values.
	 */
	private final void UpdateBrain() {
		getMovesToWin();
	}

	// Public
	/**
	 * Get the list of {@link GameField} which are owned by the computer.
	 * 
	 * @return {@link GameField}
	 */
	protected final ArrayList<GameField> getAiFields() {
		GameField[][] pg = Context.getContext().getPlayground();
		ArrayList<GameField> aiFields = new ArrayList<GameField>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (pg[i][j].getValue() != null && pg[i][j].getValue().equals("X")) {
					aiFields.add(pg[i][j]);
				}
			}
		}
		return aiFields;
	}

	/**
	 * Get the list of {@link GameField} which are owned by the player.
	 * 
	 * @return {@link GameField}
	 */
	protected final ArrayList<GameField> getPlayerFields() {
		GameField[][] pg = Context.getContext().getPlayground();
		ArrayList<GameField> playerFields = new ArrayList<GameField>();

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (pg[i][j].getValue() != null && pg[i][j].getValue().equals("O")) {
					playerFields.add(pg[i][j]);
				}
			}
		}
		return playerFields;
	}

	/**
	 * Get the moves to win for the computer and the player. The updated values
	 * are stored in the getters <b>getFieldsToWinAi</b></br>
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
	 * Calculate the next move for the computer.
	 * 
	 * @throws InvalidActivityException
	 *             if there is no field left
	 */
	public GameField getNextMove() throws InvalidActivityException {
		ArrayList<GameField> aiFields = getAiFields();
		ArrayList<GameField> plFields = getPlayerFields();
		GameField[][] gameFields = Context.getContext().getPlayground();
		ArrayList<GameLine> freeLines = GameLine.getFreeLines(plFields);
		ArrayList<GameLine> freeLinesPlayer = GameLine.getFreeLines(aiFields);

		if (getFieldsToWinPlayer() == 1 && getFieldsToWinAi() != 1) {
			GameLine bestLine = freeLinesPlayer.get(0);
			int oldMoves = 4;
			for (GameLine line : freeLinesPlayer) {
				int moves = line.getMovesNeeded(plFields);

				if (moves < oldMoves) {
					bestLine = line;
					oldMoves = moves;
				}
			}

			for (GameField field : bestLine.getGameFields()) {
				if (field.getValue().equals("")) {
					return field;
				}
			}
		}

		if (gameFields[1][1].getValue().equals("")) {
			// Take the middle field if available
			return gameFields[1][1];
		}

		if (freeLines.size() == 0) {
			// There's something wrong.
			throw new UnsupportedOperationException("There are no fields available for the next move.");
		}

		GameLine bestLine = freeLines.get(0);
		int oldMoves = 4;
		for (GameLine line : freeLines) {
			int moves = line.getMovesNeeded(aiFields);

			if (moves < oldMoves) {
				bestLine = line;
				oldMoves = moves;
			}
		}

		if (bestLine.getField2().getValue().equals("")) {
			return bestLine.getField2();
		} else if (bestLine.getField1().getValue().equals("")) {
			return bestLine.getField1();
		} else {
			return bestLine.getField3();
		}
	}

	/**
	 * Calculate the next bid for the computer.
	 */
	public int getNextBid() {
		UpdateBrain();
		return getNextValidBid(_strategy.getNextBet(getFieldsToWinPlayer(), getFieldsToWinAi()));
	}

	/**
	 * Validate the bid against some simple rules.
	 */
	private int getNextValidBid(int calculatedBid) {
		int aiCredits = Context.getContext().getAiCredits();
		int playerCredits = Context.getContext().getPlayerCredits();

		if (getFieldsToWinAi() == 1) {
			return aiCredits;
		}

		if (calculatedBid > aiCredits) {
			return aiCredits;
		}

		if (calculatedBid > playerCredits || getFieldsToWinPlayer() == 1) {
			if (calculatedBid > aiCredits) {
				return aiCredits;
			}
			return aiCredits > playerCredits ? playerCredits + 1 : aiCredits;
		}

		if (calculatedBid == 0 && aiCredits > 0) {
			return 1;
		}

		return calculatedBid;
	}

	/**
	 * Save the win to the strategy if needed.<br/>
	 * Only used in {@link ImmitatorBettingStrategy}
	 */
	public void saveAiWin() {
		if (_strategy.getClass() == ImmitatorBettingStrategy.class) {
			ImmitatorBettingStrategy strat = (ImmitatorBettingStrategy) _strategy;
			strat.addWin();
			SettingHelper.getInstance().saveBidStrategy(strat);
		}
	}

	/**
	 * Save the lose to the strategy if needed.<br/>
	 * Only used in {@link ImmitatorBettingStrategy}
	 */
	public void saveAiLose() {
		if (_strategy.getClass() == ImmitatorBettingStrategy.class) {
			ImmitatorBettingStrategy strat = (ImmitatorBettingStrategy) _strategy;
			strat.addLose();
			SettingHelper.getInstance().saveBidStrategy(strat);
		}
	}
}
