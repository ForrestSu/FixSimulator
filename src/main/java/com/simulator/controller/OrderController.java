package com.simulator.controller;

import com.simulator.model.state.Order;

/**
 * Controller part of the MVC model. Provides actions to be applied on an
 * {@link Order}
 * 
 * @author sunquan
 */
public interface OrderController {

	public void execute(Order order, double lastPx, double lastQty);

	public void reject(Order order, String reason);

	public void cancel(Order order, String reason);

	public void doneForDay(Order order);

	public void expire(Order order);

	public void replace(Order order);

	public void acknowledge(Order order);

	public void pendingNew(Order order);

	public void pendingCancel(Order order);

	public void pendingReplace(Order order);

}
