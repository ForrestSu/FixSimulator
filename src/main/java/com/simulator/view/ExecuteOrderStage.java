package com.simulator.view;

import java.util.concurrent.Executor;

import com.simulator.controller.OrderController;
import com.simulator.model.state.OrderBean;

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

	private TextField txtLastQty;
	private TextField txtLastPx;
	private OrderBean order;

	ExecuteOrderStage(OrderController orderController, Executor backgroundExecutor) {
		BorderPane borderPaneRoot = new BorderPane();
		borderPaneRoot.setPadding(new Insets(10));
		final Scene scene = new Scene(borderPaneRoot, 300, 150);

		GridPane grid = new GridPane();
		DisplayUtils.applyStyleTo(grid);
		grid.setHgap(10D);
		grid.setVgap(10D);

		// last qty
		Label lblQty = new Label("Last qty");
		lblQty.setTextFill(Color.WHITE);
		GridPane.setHalignment(lblQty, HPos.RIGHT);
		grid.add(lblQty, 0, 0);
		txtLastQty = new TextField();
		txtLastQty.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(txtLastQty, Priority.ALWAYS);
		GridPane.setHalignment(txtLastQty, HPos.LEFT);
		grid.add(txtLastQty, 1, 0);

		// last price
		Label lblPx = new Label("Last px");
		lblPx.setTextFill(Color.WHITE);
		grid.add(lblPx, 0, 1);
		GridPane.setHalignment(lblPx, HPos.RIGHT);
		txtLastPx = new TextField();
		txtLastPx.setMaxWidth(Double.MAX_VALUE);
		GridPane.setHgrow(txtLastPx, Priority.ALWAYS);
		GridPane.setHalignment(txtLastPx, HPos.LEFT);
		grid.add(txtLastPx, 1, 1);

		// button
		Button buttonExecute = new Button("Execute");
		buttonExecute.setOnAction(event -> {
			backgroundExecutor.execute(() -> {
				orderController.execute(order, Double.valueOf(txtLastPx.getText()),
						Double.valueOf(txtLastQty.getText()));
			});
			ExecuteOrderStage.this.hide();
		});
		buttonExecute.disableProperty()
				.bind(Bindings.isEmpty(txtLastPx.textProperty()).or(Bindings.isEmpty(txtLastQty.textProperty())));

		buttonExecute.setAlignment(Pos.BASELINE_RIGHT);
		GridPane.setHalignment(buttonExecute, HPos.RIGHT);
		grid.add(buttonExecute, 1, 2);
		borderPaneRoot.setCenter(grid);

		setScene(scene);
	}

	public void setTargetOrder(OrderBean theOrder) {
		this.order = theOrder;
		setTitle("Execute order " + order.getOrderID());
	}
}