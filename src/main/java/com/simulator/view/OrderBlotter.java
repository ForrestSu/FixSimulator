package com.simulator.view;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import com.simulator.model.interf.OrderObserver;
import com.simulator.model.state.Order;
import com.simulator.model.state.OrderBean;
import com.simulator.model.state.OrderBook;

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
			//表格刷新，重新加载数据
			@Override
			public void invalidated(Observable observable) {
				System.out.println("Refresh TableView!");
			}
		});
		tableView = new TableView<>(observableOrderList);
		getTableView().setEditable(false);
		// With the table defined, we define now the data model: we'll be using
		// show Order title
		createTableColumns();
		
		OrderBook.getInstance().registerOrderObserver(this); // listen to order events
	}

	@SuppressWarnings("unchecked")
	private void createTableColumns() {
		TableColumn<OrderBean, Double> avgPx = new TableColumn<>("均价");
		avgPx.setCellValueFactory(cellData -> cellData.getValue().getAvgPxProperty().asObject());
		TableColumn<OrderBean, String> clOrdID = new TableColumn<>("客户订单编号");
		clOrdID.setCellValueFactory(cellData -> cellData.getValue().getClOrdIDProperty());
		TableColumn<OrderBean, Double> cumQty = new TableColumn<>("累积成交数量");
		cumQty.setCellValueFactory(cellData -> cellData.getValue().getCumQtyProperty().asObject());
		TableColumn<OrderBean, Double> leavesQty = new TableColumn<>("剩余数量");
		leavesQty.setCellValueFactory(cellData -> cellData.getValue().getLeavesProperty().asObject());
		TableColumn<OrderBean, String> orderID = new TableColumn<>("订单编号");
		orderID.setCellValueFactory(cellData -> cellData.getValue().getIDProperty());
		TableColumn<OrderBean, String> ordStatus = new TableColumn<>("委托状态");
		ordStatus.setCellValueFactory(cellData -> cellData.getValue().getOrdStatusProperty());
		TableColumn<OrderBean, String> ordType = new TableColumn<>("订单类型");
		ordType.setCellValueFactory(cellData -> cellData.getValue().getOrdTypeProperty());
		TableColumn<OrderBean, String> origClOrdID = new TableColumn<>("原客户订单编号");
		origClOrdID.setCellValueFactory(cellData -> cellData.getValue().getOrigClOrdIDProperty());
		TableColumn<OrderBean, String> price = new TableColumn<>("价格");
		price.setCellValueFactory(cellData -> cellData.getValue().getPriceProperty());
		TableColumn<OrderBean, String> side = new TableColumn<>("买卖方向");
		side.setCellValueFactory(cellData -> cellData.getValue().getSideProperty());
		TableColumn<OrderBean, String> symbol = new TableColumn<>("symbol");
		symbol.setCellValueFactory(cellData -> cellData.getValue().getSymbolProperty());
		TableColumn<OrderBean, String> senderCompID = new TableColumn<>("发送发ID");
		senderCompID.setCellValueFactory(cellData -> cellData.getValue().getSenderCompIDProperty());
		TableColumn<OrderBean, String> targetCompID = new TableColumn<>("接收方ID");
		targetCompID.setCellValueFactory(cellData -> cellData.getValue().getTargetCompIDProperty());
		
		TableColumn<OrderBean, String> MsgType = new TableColumn<>("消息类型");//msgType
		MsgType.setCellValueFactory(cellData -> cellData.getValue().getMsgTypeProperty());
		// TODO fill all values
		
		getTableView().getColumns().setAll(orderID,MsgType, symbol, ordType, price, side, ordStatus, leavesQty, cumQty, avgPx,
				clOrdID, origClOrdID, senderCompID, targetCompID);
	}

	public TableView<OrderBean> getTableView() {
		return tableView;
	}
	
	//新订单
	@Override
	public void onNewOrder(Order Neworder) {
		observableOrderList.add(new OrderBean(Neworder));
	}
	
	//清空所有委托
	public void ClearAllOrders() {
		observableOrderList.clear();
	}

}
