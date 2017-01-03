package com.simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.simulator.model.state.Order;
import com.simulator.model.state.OrderBean;
import com.simulator.model.state.OrderBook;
import com.simulator.model.state.OrderObserver;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

/**
 * Class wrapping a {@code TableView} displaying the current status of all
 * orders in the system
 * 
 * @author sunquan
 *
 */
public class OrderBlotter implements OrderObserver {

	private final TableView<OrderBean> tableView;
	private final ObservableList<OrderBean> observableOrderList;

	OrderBlotter() {
		// get all orders and transform them into order beans
		Collection<Order> orders = OrderBook.getInstance().getAllOrders();
		List<OrderBean> orderBeans = orders.stream().map(order -> new OrderBean(order)).collect(Collectors.toList());
		// add all properties from the table to be updated

		Callback<OrderBean, Observable[]> extractor = order -> {
			List<Observable> list = new ArrayList<>();
			list.add(order.getAvgPxProperty());
			list.add(order.getClOrdIDProperty());
			list.add(order.getCumQtyProperty());
			list.add(order.getLeavesProperty());
			list.add(order.getOrdStatusProperty());
			list.add(order.getOrigClOrdIDProperty());
			// for Market orders, no need to observe Price
			if (order.getPrice() != null)
				list.add(order.getPriceProperty());
			return list.toArray(new Observable[list.size()]);
		};

		observableOrderList = FXCollections.observableList(orderBeans, extractor);
		observableOrderList.addListener(new InvalidationListener() {

			@Override
			public void invalidated(Observable observable) {
				System.out.println("Invalidated " + observable);
			}
		});
		tableView = new TableView<>(observableOrderList);
		getTableView().setEditable(false);
		// With the table defined, we define now the data model: we'll be using
		// a ObservableList
		createTableColumns();
		
		OrderBook.getInstance().registerOrderObserver(this); // listen to order events
	}

	@SuppressWarnings("unchecked")
	private void createTableColumns() {
		TableColumn<OrderBean, Double> avgPx = new TableColumn<>("avgPx");
		avgPx.setCellValueFactory(cellData -> cellData.getValue().getAvgPxProperty().asObject());
		TableColumn<OrderBean, String> clOrdID = new TableColumn<>("clOrdID");
		clOrdID.setCellValueFactory(cellData -> cellData.getValue().getClOrdIDProperty());
		TableColumn<OrderBean, Double> cumQty = new TableColumn<>("cumQty");
		cumQty.setCellValueFactory(cellData -> cellData.getValue().getCumQtyProperty().asObject());
		TableColumn<OrderBean, Double> leavesQty = new TableColumn<>("leavesQty");
		leavesQty.setCellValueFactory(cellData -> cellData.getValue().getLeavesProperty().asObject());
		TableColumn<OrderBean, String> orderID = new TableColumn<>("orderID");
		orderID.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
		TableColumn<OrderBean, String> ordStatus = new TableColumn<>("ordStatus");
		ordStatus.setCellValueFactory(cellData -> cellData.getValue().getOrdStatusProperty());
		TableColumn<OrderBean, String> ordType = new TableColumn<>("ordType");
		ordType.setCellValueFactory(cellData -> cellData.getValue().getOrdTypeProperty());
		TableColumn<OrderBean, String> origClOrdID = new TableColumn<>("origClOrdID");
		origClOrdID.setCellValueFactory(cellData -> cellData.getValue().getOrigClOrdIDProperty());
		TableColumn<OrderBean, String> price = new TableColumn<>("price");
		price.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
		TableColumn<OrderBean, String> side = new TableColumn<>("side");
		side.setCellValueFactory(cellData -> cellData.getValue().getSideProperty());
		TableColumn<OrderBean, String> symbol = new TableColumn<>("symbol");
		symbol.setCellValueFactory(cellData -> cellData.getValue().getSymbolProperty());
		TableColumn<OrderBean, String> senderCompID = new TableColumn<>("senderCompID");
		senderCompID.setCellValueFactory(cellData -> cellData.getValue().getSenderCompIDProperty());
		TableColumn<OrderBean, String> targetCompID = new TableColumn<>("targetCompID");
		targetCompID.setCellValueFactory(cellData -> cellData.getValue().getTargetCompIDProperty());
		// TODO fill all values

		getTableView().getColumns().setAll(orderID, symbol, ordType, price, side, ordStatus, leavesQty, cumQty, avgPx,
				origClOrdID, clOrdID, senderCompID, targetCompID);
	}

	public TableView<OrderBean> getTableView() {
		return tableView;
	}

	@Override
	public void onNewOrder(Order order) {
		observableOrderList.add(new OrderBean(order));
	}

}
