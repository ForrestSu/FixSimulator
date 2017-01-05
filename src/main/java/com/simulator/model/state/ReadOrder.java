package com.simulator.model.state;

import com.simulator.model.tags.ExecType;
import com.simulator.model.tags.OrdType;
import com.simulator.model.tags.Side;

public interface ReadOrder {

	String getMsgType();
	
	String getSenderCompID();

	String getTargetCompID();
	
	String getOrderID();

	String getClOrdID();

	String getOrigClOrdID();

	String getSymbol();

	ExecType getOrdStatus();

	char getTimeInForce();

	Side getSide();

	OrdType getOrdType();

	double getOrderQty();

	double getAvgPx();

	Double getPrice();
	
	double getCumQty();
	
	double getLeavesQty();
	
	String getText();
}
