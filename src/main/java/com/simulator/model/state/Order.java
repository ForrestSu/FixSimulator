package com.simulator.model.state;

import com.simulator.model.tags.ExecType;
import com.simulator.model.tags.OrdType;
import com.simulator.model.tags.Side;

/**
 * Mutable structure that holds the state of an order.
 * 
 * @author sunquan
 *
 */
public interface Order extends ReadOrder {
	void setMsgType(String msgType);
	
	void setSenderCompID(String senderCompID);

	void setTargetCompID(String targetCompID);
	
	void setOrderID(String ID);

	void setClOrdID(String clientID);

	void setOrigClOrdID(String origClientID);

	void setSymbol(String symbol);

	void setOrdStatus(ExecType status);

	void setTimeInForce(char tif);

	void setSide(Side side);

	void setOrdType(OrdType type);

	void setOrderQty(double qty);

	void setAvgPx(double price);

	void setPrice(Double price);

	void setCumQty(double cumQty);

	void setLeavesQty(double leavesQty);
	
	void setText(String text);
}
