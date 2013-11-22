import static org.junit.Assert.*;

import java.util.ArrayList;

import nif.tictactoe.Context;
import nif.tictactoe.model.GameField;
import nif.tictactoe.model.GameLine;
import nif.tictactoe.model.HardBrain;

import org.junit.Before;
import org.junit.Test;

public class GameLineTests {

	@Before
	public void tearUp() {
		_testPlayground[0][0] = new GameField(0, 0, "");
		_testPlayground[0][1] = new GameField(0, 1, "");
		_testPlayground[0][2] = new GameField(0, 2, "");
		_testPlayground[1][0] = new GameField(1, 0, "");
		_testPlayground[2][0] = new GameField(2, 0, "");
		_testPlayground[1][1] = new GameField(1, 1, "");
		_testPlayground[2][2] = new GameField(2, 2, "");
		_testPlayground[1][2] = new GameField(1, 2, "");
		_testPlayground[2][1] = new GameField(2, 1, "");
		Context.getContext().setBrain(new HardBrain());
		Context.getContext().setPlayground(_testPlayground);
	}

	@Test
	public void testVerticalGameLinesAvailable() {
		ArrayList<GameField> fields = new ArrayList<GameField>();
		fields.add(new GameField(0, 0, "X"));
		fields.add(new GameField(1, 0, "X"));
		fields.add(new GameField(2, 0, "X"));

		ArrayList<GameLine> freeLines = GameLine.getFreeLines(fields);

		assertEquals(2, freeLines.size());

		for (GameLine line : freeLines) {
			assertFalse(line.contains(fields.get(0)));
			assertFalse(line.contains(fields.get(1)));
			assertFalse(line.contains(fields.get(2)));
		}
	}

	@Test
	public void testHorizontalGameLinesAvailable() {
		ArrayList<GameField> fields = new ArrayList<GameField>();
		fields.add(new GameField(0, 0, "X"));
		fields.add(new GameField(0, 1, "X"));
		fields.add(new GameField(0, 2, "X"));

		ArrayList<GameLine> freeLines = GameLine.getFreeLines(fields);

		assertEquals(2, freeLines.size());

		for (GameLine line : freeLines) {
			assertFalse(line.contains(fields.get(0)));
			assertFalse(line.contains(fields.get(1)));
			assertFalse(line.contains(fields.get(2)));
		}
	}

	@Test
	public void testDiagonalGameLinesAvailable() {
		ArrayList<GameField> fields = new ArrayList<GameField>();
		fields.add(new GameField(1, 0, "X"));
		fields.add(new GameField(0, 1, "X"));
		fields.add(new GameField(1, 2, "X"));
		fields.add(new GameField(2, 1, "X"));

		ArrayList<GameLine> freeLines = GameLine.getFreeLines(fields);

		assertEquals(2, freeLines.size());

		for (GameLine line : freeLines) {
			assertFalse(line.contains(fields.get(0)));
			assertFalse(line.contains(fields.get(1)));
			assertFalse(line.contains(fields.get(2)));
			assertFalse(line.contains(fields.get(3)));
		}
	}

	@Test
	public void testFieldsNeeded() {
		ArrayList<GameLine> lines = GameLine.getPossibleGameLines();

		for (GameLine line : lines) {
			ArrayList<GameField> fields = new ArrayList<GameField>();

			int needed = line.getMovesNeeded(fields);

			assertEquals(3, needed);

			fields.add(line.getField1());
			needed = line.getMovesNeeded(fields);

			assertEquals(2, needed);

			fields.add(line.getField2());
			needed = line.getMovesNeeded(fields);

			assertEquals(1, needed);

			fields.add(line.getField3());
			needed = line.getMovesNeeded(fields);

			assertEquals(0, needed);
		}
	}

	private GameField[][] _testPlayground = new GameField[3][3];
}
