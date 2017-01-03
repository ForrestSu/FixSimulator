package com.simulator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simulator.controller.er.processor.ExecutionReportProcessor;
import com.simulator.controller.er.producer.ExecuteOrder;
import com.simulator.controller.er.producer.ExecutionReportProducer;
import com.simulator.controller.er.producer.PendingNewOrder;
import com.simulator.model.messages.ExecutionReport;
import com.simulator.model.state.Order;

/**
 * Applies the action asked by the view and sends the Execution out
 * 
 * @author sunquan
 *
 */
public class OrderControllerImpl implements OrderController {

	private final Logger log = LoggerFactory.getLogger(OrderControllerImpl.class);

	private final ExecutionReportProcessor erProcessor;

	public OrderControllerImpl(ExecutionReportProcessor erProcessor) {
		this.erProcessor = erProcessor;
	}

	private void processExecutionReport(ExecutionReportProducer executionProducer) {
		ExecutionReport er = executionProducer.getExecutionReport();
		erProcessor.process(er);
	}

	@Override
	public void execute(Order order, double lastPx, double lastQty) {
		log.info("Executing order {} with lastPx {} and lastQty {}",
				new Object[] { order.getOrderID(), lastPx, lastQty });
		processExecutionReport(new ExecuteOrder(order, lastPx, lastQty));
	}

	@Override
	public void reject(Order order, String reason) {
		// TODO Auto-generated method stub

	}

	@Override
	public void cancel(Order order, String reason) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doneForDay(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void expire(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void replace(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void acknowledge(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pendingNew(Order order) {
		log.info("To Pending New, order {}", order.getOrderID());
		processExecutionReport(new PendingNewOrder(order));
	}

	@Override
	public void pendingCancel(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pendingReplace(Order order) {
		// TODO Auto-generated method stub

	}

}
