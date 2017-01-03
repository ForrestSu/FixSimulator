package com.simulator.model.tags;

/**
 * Represents the tag 54 in FIX
 * 
 * @author sunquan
 *
 */
public enum Side {

	BUY('1', "Buy"), SELL('2', "Sell"); // TODO add others

	private final char fixSide;
	private final String humanSide;

	Side(char fixSide, String humanSide) {
		this.fixSide = fixSide;
		this.humanSide = humanSide;
	}

	@Override
	public String toString() {
		return fixSide + " (" + humanSide + ")";
	}
	
	public char toChar() {
		return fixSide;
	}
	
	public static Side valueOf(char side) {
		for (Side s: Side.values()) {
			if (side == s.fixSide) return s;
		}
		return null;
	}
}
