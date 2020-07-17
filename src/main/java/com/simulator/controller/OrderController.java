package com.simulator.controller;

import com.simulator.model.state.Order;

/**
 * Controller part of the MVC model. Provides actions to be applied on an
 * {@link Order}
 * 
 * @author sunquan
 */
public interface OrderController {

	void execute(Order order, double lastPx, double lastQty);

	void reject(Order order, String reason);
	
	void cancelReject(Order order, String reason);

	void cancel(Order order);

	void doneForDay(Order order);

	void expire(Order order);

	void replace(Order order);

	void acknowledge(Order order);

	void pendingNew(Order order);

	void pendingCancel(Order order);

	void pendingReplace(Order order);

}
