package nif.tictactoe.model;

import java.util.Random;

import nif.tictactoe.Context;

public class ModerateBettingStrategy implements IStrategy {

	@Override
	public int getNextBet(int fieldsToWinPlayer, int fieldsToWinAi) {
		int score = 1;
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
		int maxBet = (int) Math.round((aiCredits / 5d) * score);
		int minBet = 1;
		minBet += (int) Math.round(aiCredits / 5d * (score - 1));
		int bet = rnd.nextInt(maxBet + 1);

		if (minBet > bet && maxBet >= minBet) {
			if(maxBet == minBet) {
				return maxBet;
			}
			while (minBet >= bet) {
				bet = rnd.nextInt(maxBet + 1);
			}
		}

		return bet;
	}

}
