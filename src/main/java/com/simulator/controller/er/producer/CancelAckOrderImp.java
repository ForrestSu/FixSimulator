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
public class CancelAckOrderImp implements ExecReportProducerInterf {
	
	private final Order order;

	public CancelAckOrderImp(Order targetOrder) {
		this.order = targetOrder;
		order.setOrdStatus(ExecType.CANCELED);
	}

	@Override
	public MsgExecutionReport getExecutionReport() {
		return (new MsgExecutionReport.Builder(order)).build();
	}

}
