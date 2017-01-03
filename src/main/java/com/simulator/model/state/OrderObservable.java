package com.simulator.model.state;

/**
 * @author sunquan
 *
 */
public interface OrderObservable {

	void registerOrderObserver(OrderObserver observer);

	void unRegisterOrderObserver(OrderObserver observer);

}
