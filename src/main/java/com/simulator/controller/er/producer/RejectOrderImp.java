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
public class RejectOrderImp implements ExecReportProducerInterf {
	
	private final Order order;

	public RejectOrderImp(Order targetOrder,String sRejReason) {
		this.order = targetOrder;
		order.setOrdStatus(ExecType.REJECTED);
		order.setText(sRejReason);
	}

	@Override
	public MsgExecutionReport getExecutionReport() {
		return (new MsgExecutionReport.Builder(order)).build();
	}

}
