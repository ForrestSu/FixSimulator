package com.simulator.controller.er.producer;

import com.simulator.model.messages.ExecutionReport;
import com.simulator.model.state.Order;
import com.simulator.model.tags.ExecType;
import com.simulator.model.tags.OrdStatus;

/**
 * Changes the order given to PENDING NEW state and generates an
 * {@link ExecutionReport} accordingly
 * 
 * @author sunquan
 *
 */
public class PendingNewOrder implements ExecutionReportProducer {
	
	private final Order order;

	public PendingNewOrder(Order targetOrder) {
		this.order = targetOrder;
		order.setOrdStatus(OrdStatus.PENDING_NEW);
	}

	@Override
	public ExecutionReport getExecutionReport() {
		return new ExecutionReport.Builder(ExecType.PENDING_NEW,  order).build();
	}

}
