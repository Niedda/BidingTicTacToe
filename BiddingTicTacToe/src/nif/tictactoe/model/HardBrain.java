package nif.tictactoe.model;

import java.util.ArrayList;
import java.util.Random;

import nif.tictactoe.*;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: The brain for playing hard games versus the computer. 
 */
public class HardBrain extends SuperBrain {

	@Override
	public int getNextBid() {
		int score = 0;

		int fieldsToWinPlayer = getFieldsToWinPlayer();
		int fieldsToWinAi = getFieldsToWinAi();
		
		// Calculate the score value
		if(fieldsToWinPlayer ==  1) {
			score += 9;
		} else if(fieldsToWinPlayer == 2) {
			score += 2;
		}
		
		if(fieldsToWinAi == 1) {
			return Context.getContext().getAiCredits();
		} else if(fieldsToWinAi == 2) {
			score += 2;
		}
		
		if(Context.getContext().getAiCredits() < Context.getContext().getPlayerCredits()) {
			score += 2;
		} else if(Context.getContext().getAiCredits() > Context.getContext().getPlayerCredits()) {
			score += 4;
		}
		Random rnd = new Random();
		
		// Set the bet depending on the score value		
		if(score <= 4) {
			// Make a low bet
			int maxBet = (int) Math.round(Context.getContext().getAiCredits() / 3d);
			if(maxBet > Context.getContext().getPlayerCredits() + 1) {
				maxBet = Context.getContext().getPlayerCredits() + 1; 				
			}
			int bet = rnd.nextInt(maxBet) + 1;
			if(bet > Context.getContext().getAiCredits()) {
				return Context.getContext().getAiCredits();
			}			
			return bet;
		} else if(score <= 8) {
			// Make a medium bet
			int maxBet = (int) Math.round((Context.getContext().getAiCredits() / 3d) * 2d);
			int minBet = (int) Math.round(Context.getContext().getAiCredits() / 3d);
			if(maxBet > Context.getContext().getPlayerCredits() + 1) {
				maxBet = Context.getContext().getPlayerCredits() + 1; 				
			}			
			int bet = rnd.nextInt(maxBet) + 1;
			
			while(bet < minBet) {
				bet = rnd.nextInt(maxBet) + 1;
			}
			if(bet > Context.getContext().getAiCredits()) {
				return Context.getContext().getAiCredits();
			}
			
			return bet;
		} else {
			// Make a high bet
			int maxBet = Context.getContext().getAiCredits();
			int minBet = (int) Math.round((Context.getContext().getAiCredits() / 3d) * 2d);
			if(maxBet > Context.getContext().getPlayerCredits() + 1) {
				maxBet = Context.getContext().getPlayerCredits() + 1; 				
			}			
			int bet = rnd.nextInt(maxBet) + 1;
			
			while(bet < minBet) {
				bet = rnd.nextInt(maxBet) + 1;
			}
			if(bet > Context.getContext().getAiCredits()) {
				return Context.getContext().getAiCredits();
			}
			
			return bet;
		}
	}

	@Override
	public GameField getNextMove() {
		ArrayList<GameField> aiFields = getAiFields();
		ArrayList<GameField> plFields = getPlayerFields();
		GameField[][] gameFields = Context.getContext().getPlayground();
		ArrayList<GameLine> freeLines = GameLine.getFreeLines(plFields);
		
		if(gameFields[1][1].getValue().equals("")) {
			//Take the middle field if available
			return gameFields[1][1];
		} 
		
		if(freeLines.size() == 0) {
			//There's something wrong.
			return null;
		}
		
		GameLine bestLine = freeLines.get(0);
		int oldMoves = 4;
		for(GameLine line: freeLines) {
			int moves = line.getMovesNeeded(aiFields);
			
			if(moves < oldMoves) {
				bestLine = line;
				oldMoves = moves;
			}
		}
		
		if(bestLine.getField2().getValue().equals("")) {
			return bestLine.getField2();				
		} else if(bestLine.getField1().getValue().equals("")) {
			return bestLine.getField1();			
		} else {
			return bestLine.getField3();
		}
	}

}