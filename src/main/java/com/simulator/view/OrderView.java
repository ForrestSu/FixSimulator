package com.simulator.view;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.simulator.controller.OrderController;
import com.simulator.model.state.OrderBean;
import com.simulator.util.DisplayUtils;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * View part of the MVC model
 * 
 * @author sunquan
 *
 */
public class OrderView extends Application {

	private final OrderController controller; //控制器
	private ExecuteOrderStage executeOrderStage;
	private OrderBlotter blotter; //OrderBlottle实例
	private TableView<OrderBean> orderTableView;//引用OrderBlottle中的tableView
	private final Executor backgroundExecutor;//执行器

	public OrderView(OrderController controller) {
		this.controller = controller;
		this.backgroundExecutor = Executors.newCachedThreadPool();
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		final BorderPane root = new BorderPane();
		final Scene scene = new Scene(root, 700, 500);
		scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
		root.setTop(buildTopPane());
		root.setCenter(buildCenterPane());
		root.setBottom(buildBottomPane());

		primaryStage.setScene(scene);
		primaryStage.setTitle("FIX Simulator");
		//primaryStage.getIcons().add(new Image(OrderView.class.getResourceAsStream("/resource/orange-ball.png")));
		primaryStage.show();
	}
    //顶部的面板
	private Node buildTopPane() {
		VBox vbox = new VBox();
		DisplayUtils.applyStyleTo(vbox);//设置样式
		vbox.setSpacing(8);
		Text title = new Text("ORDER BLOTTER");
		title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
		title.setFill(Color.WHITE);
		vbox.getChildren().addAll(title);
		return vbox;
	}

	// build Order blotter
	private Node buildCenterPane() {
		blotter = new OrderBlotter();
		orderTableView = blotter.getTableView();
		return orderTableView;
	}

	private Node buildBottomPane() {
		HBox hbox = new HBox();
		hbox.setSpacing(10);
		DisplayUtils.applyStyleTo(hbox);
        
		
		//订单确认
		Button buttonAck = new Button("委托确认");
		buttonAck.setOnAction(event -> {
			OrderBean selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
			controller.acknowledge(selectedOrder);
		});
		
		//订单拒绝
		Button buttonRejct = new Button("拒绝");
		buttonRejct.setOnAction(event -> {
			OrderBean selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
			controller.pendingNew(selectedOrder);
		});
		
        //订单成交
		Button buttonExecute = new Button("成交");
		buttonExecute.setOnAction(event -> {
			OrderBean selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
			createOrShowExecuteWindow(selectedOrder.getOrderID());
		});
		
	    //撤单废单
		Button buttonCancelReject = new Button("撤单废单");
		buttonCancelReject.setOnAction(event -> {
			OrderBean selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
			controller.pendingNew(selectedOrder);
		});
		
		//清空所有订单
		Button buttonCLear = new Button("清空");
		buttonCLear.setOnAction(event -> {
			blotter.ClearAllOrders();
		});
		
		hbox.getChildren().addAll(buttonAck, buttonRejct,buttonExecute,buttonCancelReject,buttonCLear);
		disableIfNoSelection(buttonAck, buttonRejct,buttonExecute,buttonCancelReject,buttonCLear);

		return hbox;
	}

	private void disableIfNoSelection(Button... buttons) {
		for (Button button : buttons) {
			button.disableProperty().bind(orderTableView.getSelectionModel().selectedItemProperty().isNull());
		}
	}
    // 显示一个窗体，并发送执行报告
	private void createOrShowExecuteWindow(String orderID) {
		if (executeOrderStage == null) {
			executeOrderStage = new ExecuteOrderStage(controller, backgroundExecutor);
		}
		OrderBean order = orderTableView.getSelectionModel().getSelectedItem();
		executeOrderStage.setTargetOrder(order);
		executeOrderStage.show();
	}

}
