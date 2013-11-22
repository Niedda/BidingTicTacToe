import static org.junit.Assert.*;
import nif.tictactoe.model.GameField;

import org.junit.Test;


public class GameFieldTests {

	@Test
	public void testEquality() {
		GameField comp1 = new GameField(1,1,"");
		GameField comp2 = new GameField(1,1,"");
		
		assertTrue(comp1.equals(comp2));
		
		GameField notEqual = new GameField(2,2,"");
		
		assertFalse(comp1.equals(notEqual));
	}
	
	@Test
	public void testEqualityValueIndependent() {
		//Game Field refers to a place and should only compare the position not the value of the field.
		GameField comp1 = new GameField(1,1,"O");
		GameField comp2 = new GameField(1,1,"X");
		
		assertTrue(comp1.equals(comp2));
		
		GameField notEqual = new GameField(2,2,"O");
		
		assertFalse(comp1.equals(notEqual));
	}
}
