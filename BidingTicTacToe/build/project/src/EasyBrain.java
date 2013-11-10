import java.util.ArrayList;
import java.util.Random;

public class EasyBrain extends SuperBrain {

	@Override
	public int getNextBid() {
		// TODO implement
		if(Context.getContext().getAiCredits() == 0) {
			return 0;
		}
		Random i = new Random();
		int j = i.nextInt(Context.getContext().getAiCredits()) + 1;
		return j;
	}

	@Override
	public GameField getNextMove() {
		UpdateBrain();
		ArrayList<GameField> aiFields = getAiFields();
		GameField[][] gameFields = Context.getContext().getPlayground();
		ArrayList<GameLine> freeLines = getFreeGameLines();
				
		for(int i = 0; 3 > i; i++) {
			for(int j = 0; 3 > j; j++) {
				if(gameFields[i][j].getValue().equals("")) {
					return gameFields[i][j];
				}
			}	
		}
		return new GameField(0, 0);
	}
}
