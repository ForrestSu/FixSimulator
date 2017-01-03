package com.simulator.controller.sender;

import com.simulator.model.messages.NewOrderSingle;
import com.simulator.model.state.Order;
import com.simulator.model.state.OrderBean;
import com.simulator.model.state.OrderBook;

/**
 * Processes the incoming 35=D messages (NewOrderSingle messages) and adds them
 * into the order book
 * 
 * @author sunquan
 *
 */
public class ProcessNewOrder {

	public ProcessNewOrder(NewOrderSingle inNos) {
		// TODO validate order
		Order order = new OrderBean();
		order.setSenderCompID(inNos.getSenderCompID());
		order.setTargetCompID(inNos.getTargetCompID());
		order.setClOrdID(inNos.getClOrdID());
		order.setLeavesQty(inNos.getOrderQty());
		order.setOrdType(inNos.getOrdType());
		order.setOrigClOrdID(inNos.getClOrdID());
		Double price = inNos.getPrice();
		if (price != null)
			order.setPrice(price);
		order.setQty(inNos.getOrderQty());
		order.setSide(inNos.getSide());
		order.setSymbol(inNos.getSymbol());
		// order.setTimeInForce(inNos.getT); TODO
		// TODO transactTime
		OrderBook.getInstance().addOrder(order);
	}
}
