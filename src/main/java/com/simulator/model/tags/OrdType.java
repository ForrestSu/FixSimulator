package com.simulator.model.tags;

/**
 * Represents the tag 40 in FIX
 * 
 * @author sunquan
 *
 */
public enum OrdType {

	MARKET('1', "Market"), LIMIT('2', "Limit"), STOP('3', "Stop"); // TODO add other types

	private final char fixOrdType;
	private final String humanOrdType;

	OrdType(char fixOrdType, String humanOrdType) {
		this.fixOrdType = fixOrdType;
		this.humanOrdType = humanOrdType;
	}
	
	@Override
	public String toString() {
		return fixOrdType + " (" + humanOrdType + ")";
	}
	
	public static OrdType valueOf(char type) {
		for (OrdType t : OrdType.values()) {
			if (t.fixOrdType == type) return t;
		}
		return null;
	}
}
