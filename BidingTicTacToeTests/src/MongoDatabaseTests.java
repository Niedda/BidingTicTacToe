import static org.junit.Assert.*;

import org.junit.Test;


public class MongoDatabaseTests {

	@Test
	public void testLoading() {
		MongoDatabase.getDb();		
	}
	
	@Test
	public void testLoseInserting() {
		long counter = MongoDatabase.getDb().getEasyGamesLose();
		long winCounter = MongoDatabase.getDb().getEasyGamesWin();
		MongoDatabase.getDb().addLoseEasy();
		MongoDatabase.getDb().addLoseEasy();
		long afterInsert = MongoDatabase.getDb().getEasyGamesLose();
		long afterInsertWin = MongoDatabase.getDb().getEasyGamesWin();
		assertEquals(counter + 2, afterInsert);
		assertEquals(winCounter, afterInsertWin);
		
		counter = MongoDatabase.getDb().getHardGamesLose();
		winCounter = MongoDatabase.getDb().getHardGamesWin();
		MongoDatabase.getDb().addLoseHard();
		MongoDatabase.getDb().addLoseHard();
		afterInsert = MongoDatabase.getDb().getHardGamesLose();
		afterInsertWin = MongoDatabase.getDb().getHardGamesWin();
		assertEquals(counter + 2, afterInsert);
		assertEquals(winCounter, afterInsertWin);
	}
	
	@Test
	public void testWinInserting() {
		long counter = MongoDatabase.getDb().getEasyGamesWin();
		long winCounter = MongoDatabase.getDb().getEasyGamesLose();
		MongoDatabase.getDb().addWinEasy();
		MongoDatabase.getDb().addWinEasy();
		long afterInsert = MongoDatabase.getDb().getEasyGamesWin();
		long afterInsertWin = MongoDatabase.getDb().getEasyGamesLose();
		assertEquals(counter + 2, afterInsert);
		assertEquals(winCounter, afterInsertWin);
		
		counter = MongoDatabase.getDb().getHardGamesWin();
		winCounter = MongoDatabase.getDb().getHardGamesLose();
		MongoDatabase.getDb().addWinHard();
		MongoDatabase.getDb().addWinHard();
		afterInsert = MongoDatabase.getDb().getHardGamesWin();
		afterInsertWin = MongoDatabase.getDb().getHardGamesLose();
		assertEquals(counter + 2, afterInsert);
		assertEquals(winCounter, afterInsertWin);
	}

}
