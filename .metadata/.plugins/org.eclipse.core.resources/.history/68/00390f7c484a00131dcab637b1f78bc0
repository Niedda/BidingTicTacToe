import java.util.Random;

/**
 * @author: Fabrizio Niedda
 * @version: 1.0
 * @description: The brain for playing easy games versus the computer. 
 */
public class EasyBrain extends SuperBrain {

	@Override
	public int getNextBid() {
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
		return new GameField(0, 0);
	}
}
