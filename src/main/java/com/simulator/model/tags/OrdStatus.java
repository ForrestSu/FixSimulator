package com.simulator.model.tags;

public enum OrdStatus {

	// TODO finish statuses
	NONE_YET('.', "None yet"), // this status is only added for display in
								// blotter (for newly arrived orders)
	PENDING_NEW('A', "Pending New"), NEW('0', "New"), PARTIALLY_FILLED('1', "Partially Filled"), FILLED('2',
			"Filled"), DONE_FOR_DAY('3', "Done for Day"), CANCELED('4', "Canceled");

	private final char fixStatus;
	private final String humanStatus;

	OrdStatus(char status, String humanStatus) {
		this.fixStatus = status;
		this.humanStatus = humanStatus;
	}

	@Override
	public String toString() {
		return fixStatus + " (" + humanStatus + ")";
	}

	public char toChar() {
		return fixStatus;
	}

}
