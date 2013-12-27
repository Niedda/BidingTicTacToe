package nif.tictactoe.model;

import java.io.Serializable;
import java.util.UUID;

public class ImmitatorBidStrategy implements Serializable, Comparable<ImmitatorBidStrategy>, IStrategy  {

	private static final long serialVersionUID = -6696317599920513348L;
	private int[] _bides;
	private transient int _itterator;
	private int _wins;
	private int _loses;
	private String _saveNumber;
	private transient IStrategy _fallbackStrategy;

	public ImmitatorBidStrategy(int[] bides, int wins, int loses, UUID saveNumber) {
		_bides = bides;
		_itterator = 0;
		_wins = wins;
		_loses = loses;
		_saveNumber = saveNumber.toString();
	}

	public String getSaveNumber() {
		return _saveNumber;
	}

	public int[] getBides() {
		return _bides;
	}

	public void setWins(int wins) {
		_wins = wins;
	}

	public int getWins() {
		return _wins;
	}

	public void setLoses(int loses) {
		_loses = loses;
	}

	public int getLoses() {
		return _loses;
	}

	public void addWin() {
		_wins++;
	}

	public void addLose() {
		_loses++;
	}

	public int gamesPlayed() {
		return _loses + _wins;
	}

	public double getWinLoseProportion() {
		if(_loses == 0) {
			return 1;
		}
		return _wins / _loses;
	}

	@Override
	public int compareTo(ImmitatorBidStrategy o) {			
		if (o.gamesPlayed() < this.gamesPlayed()) {
			return 1;
		}
		if (o.gamesPlayed() > this.gamesPlayed()) {
			return -1;
		}
		return 0;
	}

	@Override
	public int getNextBet(int fieldsToWinPlayer, int fieldsToWinAi) {
		if (_itterator < _bides.length) {
			int res = _bides[_itterator];
			_itterator++;
			return res;
		}

		_fallbackStrategy = new ModerateBettingStrategy();
		return _fallbackStrategy.getNextBet(fieldsToWinPlayer, fieldsToWinAi);
	}
}
