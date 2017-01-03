package com.simulator.model.state;

/**
 * Listens to order events (new orders coming in, changes requested on the
 * orders, cancelations, etc)
 * 
 * @author sunquan
 *
 */
public interface OrderObserver {

	/**
	 * Notifies when a new order got added into the order book
	 * 
	 * @param theNewOrder
	 */
	void onNewOrder(Order theNewOrder);

}
