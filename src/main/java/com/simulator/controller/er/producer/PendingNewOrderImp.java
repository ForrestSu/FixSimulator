package com.simulator.controller.er.producer;

import com.simulator.model.messages.MsgExecutionReport;
import com.simulator.model.state.Order;
import com.simulator.model.tags.ExecType;

/**
 * Changes the order given to PENDING NEW state and generates an
 * {@link MsgExecutionReport} accordingly
 * 
 * @author sunquan
 *
 */
public class PendingNewOrderImp implements ExecReportProducerInterf {
	
	private final Order order;

	public PendingNewOrderImp(Order targetOrder) {
		this.order = targetOrder;
		order.setOrdStatus(ExecType.PENDING_NEW);
	}

	@Override
	public MsgExecutionReport getExecutionReport() {
		return (new MsgExecutionReport.Builder(order)).build();
	}

}
