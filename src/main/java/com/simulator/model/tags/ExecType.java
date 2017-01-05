package com.simulator.model.tags;

/**
 * Represents the TAG 150 in FIX
 *  The Same as TAG 39 OrdStatus 
 * @author sunquan
 *
 */
public enum ExecType {
	
	
	NONE_YET('.',"NewOrder"),
	NEW('0', "NEW"), 
	PARTIALLY_FILLED('1', "Partially Filled"),
	FILLED('2', "Filled"), 
	DONE_FOR_DAY('3',"DONE FOR DAY"), 
	CANCELED('4', "CANCELED"),
	REPLACE('5', "REPLACE"), 
	PENDING_CANCEL('6',"PENDING CANCEL"), 
	REJECTED('8', "REJECTED"),
	CANCEL_REJECTED('9', "Cancel Rejected"),
	PENDING_NEW('A',"PENDING NEW"), 
	PENDING_REPLACE('E', "PENDING REPLACE"), 
	TRADE('F', "TRADE");

	private final char fixTag;
	private final String humanValue;

	ExecType(char fixTag, String humanValue) {
		this.fixTag = fixTag;
		this.humanValue = humanValue;
	}

	@Override
	public String toString() {
		return fixTag + " (" + humanValue + ")";
	}

	public char toChar() {
		return fixTag;
	}

}
