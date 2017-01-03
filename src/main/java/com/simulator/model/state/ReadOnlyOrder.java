package com.simulator.model.state;

import com.simulator.model.tags.OrdStatus;
import com.simulator.model.tags.OrdType;
import com.simulator.model.tags.Side;

public interface ReadOnlyOrder {

	String getSenderCompID();

	String getTargetCompID();
	
	String getOrderID();

	String getClOrdID();

	String getOrigClOrdID();

	String getSymbol();

	OrdStatus getOrdStatus();

	char getTimeInForce();

	Side getSide();

	OrdType getOrdType();

	double getQty();

	double getAvgPx();

	Double getPrice();
	
	double getCumQty();
	
	double getLeavesQty();
}
