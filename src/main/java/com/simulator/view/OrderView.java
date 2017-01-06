package com.simulator.view;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import com.simulator.controller.OrderController;
import com.simulator.model.state.OrderBean;
import com.simulator.util.DisplayUtils;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.VPos;
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
import javafx.stage.WindowEvent;

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
    private Text infotitle; //提示错误信息
    
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
		//任务关闭时,子线程也一起退出!
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
	            @Override
	            public void handle(WindowEvent event) {
	                System.exit(0);
	            }
	        });
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
		infotitle = new Text("");
		infotitle.setFont(Font.font("Console", FontWeight.LIGHT, 14));
		infotitle.setFill(Color.YELLOW);
		infotitle.setTextOrigin(VPos.BOTTOM);
		//订单确认
		Button buttonAck = new Button("委托确认");
		buttonAck.setOnAction(event -> {
			infotitle.setText("");
			OrderBean selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
			controller.acknowledge(selectedOrder);
		});
		
		//订单拒绝
		Button buttonRejct = new Button("废单/拒绝");
		buttonRejct.setOnAction(event -> {
			infotitle.setText("");
			OrderBean selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
			if(selectedOrder.getMsgType().equals("D"))
			{
				infotitle.setText("New Order Reject!");
				controller.reject(selectedOrder, "NewOrder reject!");
			}else if(selectedOrder.getMsgType().equals("F"))
			{
				infotitle.setText("Cancel Order Reject!");
				controller.cancelReject(selectedOrder, "CancelOrder Reject!");
			}else {
				infotitle.setText("该订单类型不支持发送废单消息！msgType="+selectedOrder.getMsgType());
			}
		});
		
        //订单成交
		Button buttonExecute = new Button("成交");
		buttonExecute.setOnAction(event -> {
			infotitle.setText("");
			//弹出一个框
			if (executeOrderStage == null) {
				executeOrderStage = new ExecuteOrderStage(controller, backgroundExecutor);
			}
			OrderBean selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
			executeOrderStage.setTargetOrder(selectedOrder);
			executeOrderStage.show();
		});
		
	    //撤单成交
		Button buttonCancelReject = new Button("撤单成交");
		buttonCancelReject.setOnAction(event -> {
			infotitle.setText("");
			OrderBean selectedOrder = orderTableView.getSelectionModel().getSelectedItem();
			//这里需要获取这笔原委托的订单数量,累计成交数量
			OrderBean orgiOrder = blotter.searchOrderByClId(selectedOrder.getOrigClOrdID());
			if(orgiOrder==null){
				infotitle.setText("未找到该撤单委托对应的原委托！原委托号"+selectedOrder.getOrigClOrdID());
			}
			else {
				selectedOrder.setOrderQty(orgiOrder.getOrderQty());
				selectedOrder.setCumQty(orgiOrder.getCumQty());
				controller.cancel(selectedOrder);
			}
		});
		//清空所有订单
		Button buttonCLear = new Button("清空");
		buttonCLear.setOnAction(event -> {
			infotitle.setText("");
			blotter.ClearAllOrders();
		});
		
		hbox.getChildren().addAll(buttonAck, buttonRejct,buttonExecute,buttonCancelReject,buttonCLear,infotitle);
		disableIfNoSelection(buttonAck, buttonRejct,buttonExecute,buttonCancelReject,buttonCLear);

		return hbox;
	}
	
    //按钮不可用
	private void disableIfNoSelection(Button... buttons) {
		for (Button button : buttons) {
			button.disableProperty().bind(orderTableView.getSelectionModel().selectedItemProperty().isNull());
		}
	}

}
