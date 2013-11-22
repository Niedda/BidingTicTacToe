package nif.tictactoe.model;

import java.util.ArrayList;
import java.util.Random;

import nif.tictactoe.BrainBase;
import nif.tictactoe.Context;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: The brain for playing easy games versus the computer. 
 */
public class EasyBrain extends BrainBase {

	@Override
	public int getNextBid() {
		int score = 0;
		int fieldsToWinPlayer = getFieldsToWinPlayer();
		int fieldsToWinAi = getFieldsToWinAi();
		Context context = Context.getContext();

		if (context.getAiCredits() == 0) {
			return 0;
		}

		// Calculate the score value
		if (fieldsToWinPlayer == 1) {
			score += 9;
		} else if (fieldsToWinPlayer == 2) {
			score += 2;
		}

		int aiCredits = context.getAiCredits();
		int playerCredits = context.getPlayerCredits();

		if (fieldsToWinAi == 1) {
			return aiCredits;
		} else if (fieldsToWinAi == 2) {
			score += 3;
		}

		if (aiCredits < playerCredits) {
			score += 3;
		} else if (aiCredits > playerCredits) {
			score += 3;
		}
		Random rnd = new Random();

		// Set the bet depending on the score value
		if (score <= 4) {
			// Make a low bet
			int maxBet = (int) Math.round(aiCredits / 3d);
			if (maxBet > playerCredits + 1) {
				maxBet = playerCredits + 1;
			}
			int bet = rnd.nextInt(maxBet) + 1;
			if (bet > aiCredits) {
				return aiCredits;
			}
			return bet;
		}

		if (score <= 8) {
			// Make a medium bet
			int maxBet = (int) Math.round((aiCredits / 3d) * 2d);
			int minBet = (int) Math.round(aiCredits / 3d);
			if (maxBet > playerCredits + 1) {
				maxBet = playerCredits + 1;
			}
			int bet = rnd.nextInt(maxBet) + 1;

			while (bet < minBet) {
				if (minBet > maxBet) {
					minBet = maxBet;
				}
				bet = rnd.nextInt(maxBet) + 1;
			}
			if (bet > aiCredits) {
				return aiCredits;
			}

			return bet;
		}

		// Make a high bet
		int maxBet = aiCredits;
		int minBet = (int) Math.round((aiCredits / 3d) * 2d);
		if (maxBet > playerCredits + 1) {
			maxBet = playerCredits + 1;
		}
		int bet = rnd.nextInt(maxBet) + 1;

		while (bet < minBet) {
			if (minBet > maxBet) {
				minBet = maxBet;
			}
			bet = rnd.nextInt(maxBet) + 1;
		}
		if (bet > aiCredits) {
			return aiCredits;
		}

		return bet;
	}

	@Override
	public GameField getNextMove() {
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
}
