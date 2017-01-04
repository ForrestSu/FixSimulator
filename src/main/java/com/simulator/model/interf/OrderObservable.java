package com.simulator.model.interf;

/**
 * @author sunquan
 *
 */
public interface OrderObservable {

	void registerOrderObserver(OrderObserver observer);
	void unRegisterOrderObserver(OrderObserver observer);
	
}