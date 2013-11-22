package nif.tictactoe.model;

import java.util.ArrayList;
import java.util.Random;

import javax.activity.InvalidActivityException;

import nif.tictactoe.BrainBase;
import nif.tictactoe.Context;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: The brain for playing hard games versus the computer.
 */
public class HardBrain extends BrainBase {

	public HardBrain() {
		_strategy = new Random().nextInt(2);
	}

	@Override
	public int getNextBid() {
		if (_strategy == 0) {
			return getAggressiveBet();
		} else if (_strategy == 1) {
			return getMixedStrategyBet();
		} else {
			return getMixedStrategyBet();
		}
	}

	@Override
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

	private int getMixedStrategyBet() {
		int score = 1;
		int fieldsToWinPlayer = getFieldsToWinPlayer();
		int fieldsToWinAi = getFieldsToWinAi();
		Context context = Context.getContext();

		if (context.getAiCredits() == 0) {
			return 0;
		}

		// Calculate the score value
		if (fieldsToWinPlayer == 1) {
			score += 2;
		} else if (fieldsToWinPlayer == 2) {
			score += 1;
		}

		int aiCredits = context.getAiCredits();
		int playerCredits = context.getPlayerCredits();

		if (fieldsToWinAi == 1) {
			score += 2;
		} else if (fieldsToWinAi == 2) {
			score += 1;
		}

		if (aiCredits < playerCredits) {
			score += 1;
		} else if (aiCredits > playerCredits) {
			score += 1;
		}

		Random rnd = new Random();
		int maxBet = (int) Math.round(aiCredits / 5d * score);
		int minBet = 1;
		minBet += (int) Math.round(aiCredits / 5d * (score - 1));
		maxBet = rnd.nextInt(maxBet);

		if (minBet > maxBet) {
			while (minBet < maxBet) {
				if (maxBet == 0) {
					maxBet += 2;
				}
				maxBet = rnd.nextInt(maxBet);
			}
		}

		if (maxBet > aiCredits) {
			return aiCredits;
		}

		if (maxBet > playerCredits) {
			return playerCredits + 1;
		}

		return maxBet;
	}

	private int getAggressiveBet() {
		Context context = Context.getContext();
		int aiCredits = context.getAiCredits();
		int playerCredits = context.getPlayerCredits();
		_move++;

		if (_move % 2 == 1) {
			return 1;
		} else if (getFieldsToWinAi() == 1) {
			return aiCredits;
		}

		if (4 > aiCredits) {
			return aiCredits;
		}

		if (4 > playerCredits) {
			return playerCredits + 1;
		}

		return 4;
	}

	private int _move;

	private int _strategy;
}
