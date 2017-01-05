package com.simulator.view;

import java.util.concurrent.Executor;

import com.simulator.controller.OrderController;
import com.simulator.model.state.OrderBean;
import com.simulator.util.DisplayUtils;

import javafx.beans.binding.Bindings;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * Window alowing to set lastPx and lastQty for an execution
 * 
 * @author sunquan
 *
 */
public class ExecuteOrderStage extends Stage {
    private Label lblPx;
    private TextField txtLastPx;
    private Label lblQty;
	private TextField txtLastQty;
	private Label lblWarn;
	private OrderBean order;

	ExecuteOrderStage(OrderController orderController, Executor backgroundExecutor) {
		BorderPane borderPaneRoot = new BorderPane();
		borderPaneRoot.setPadding(new Insets(10));
		final Scene scene = new Scene(borderPaneRoot, 350, 150);

		GridPane grid = new GridPane();
		DisplayUtils.applyStyleTo(grid);
		grid.setHgap(10D);
		grid.setVgap(10D);

		// last price
		lblPx = new Label("成交价格");
		lblPx.setTextFill(Color.WHITE);
		grid.add(lblPx, 0, 0);
		GridPane.setHalignment(lblPx, HPos.LEFT);
		txtLastPx = new TextField();
		txtLastPx.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(txtLastPx, Priority.ALWAYS);
		GridPane.setHalignment(txtLastPx, HPos.LEFT);
		grid.add(txtLastPx, 1, 0);
		
		// last qty
		lblQty = new Label("成交数量");
		lblQty.setTextFill(Color.WHITE);
		GridPane.setHalignment(lblQty, HPos.LEFT);
		grid.add(lblQty, 0, 1);
		txtLastQty = new TextField();
		txtLastQty.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(txtLastQty, Priority.ALWAYS);
		GridPane.setHalignment(txtLastQty, HPos.LEFT);
		grid.add(txtLastQty, 1, 1);
		lblWarn = new Label("");
		lblWarn.setTextFill(Color.YELLOW);
		grid.add(lblWarn, 0, 2);

		// button
		Button buttonExecute = new Button("Execute");
		buttonExecute.setOnAction(event -> {
			//价格和数量检查
			Double inputAmount= Double.valueOf(txtLastQty.getText());
			Double Price =Double.valueOf(txtLastPx.getText());
			//订单剩余数量
			if(Double.compare(order.getLeavesQty(),inputAmount)>=0)
			{
				backgroundExecutor.execute(() -> {
					orderController.execute(order, Price,inputAmount);
				});
				ExecuteOrderStage.this.hide();
			}else{
				lblWarn.setText("剩余数量不足:"+order.getLeavesQty());
			}
		});
		buttonExecute.disableProperty()
				.bind(Bindings.isEmpty(txtLastPx.textProperty())
						.or(Bindings.isEmpty(txtLastQty.textProperty())) );

		buttonExecute.setAlignment(Pos.BASELINE_RIGHT);
		GridPane.setHalignment(buttonExecute, HPos.RIGHT);
		grid.add(buttonExecute, 1, 2);
		borderPaneRoot.setCenter(grid);

		setScene(scene);
	}

	public void setTargetOrder(OrderBean theOrder) {
		this.order = theOrder;
		setTitle("Execute order " + order.getOrderID());
		lblPx.setText("成交价格("+order.getPrice()+")");
		lblQty.setText("成交数量("+order.getLeavesQty()+")");
		lblWarn.setText("");
	}
}