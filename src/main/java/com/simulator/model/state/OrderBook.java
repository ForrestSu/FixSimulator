package com.simulator.model.state;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Singleton keeping the orders in the system
 * 
 * @author sunquan
 *
 */
public class OrderBook implements OrderObservable {

	private static final OrderBook instance = new OrderBook();
	
	private final Map<String, Order> ordersByID;
//	private final Map<String, List<Order>> historyByOrder;
	private final List<OrderObserver> observers;

	private OrderBook() {
		ordersByID = new ConcurrentHashMap<>();
		observers = new CopyOnWriteArrayList<>();
//		historyByOrder = new ConcurrentHashMap<>();
	}

	public static OrderBook getInstance() {
		return instance;
	}

	public Order getOrderBy(String orderID) {
		return ordersByID.get(orderID);
	}

	public void addOrder(Order order) {
		ordersByID.put(order.getOrderID(), order);
		notifyObservers(order);
	}
	
	public Collection<Order> getAllOrders() {
		return ordersByID.values();
	}

	@Override
	public void registerOrderObserver(OrderObserver observer) {
		observers.add(observer);
	}

	@Override
	public void unRegisterOrderObserver(OrderObserver observer) {
		// TODO Auto-generated method stub
	}

	private void notifyObservers(Order order) {
		observers.forEach(obs -> obs.onNewOrder(order));
	}

}
