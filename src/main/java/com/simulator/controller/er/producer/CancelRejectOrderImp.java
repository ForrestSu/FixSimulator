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
public class CancelRejectOrderImp implements ExecReportProducerInterf {
	
	private final Order order;

	public CancelRejectOrderImp(Order targetOrder,String sRejReason) {
		this.order = targetOrder;
		order.setOrdStatus(ExecType.CANCEL_REJECTED);
		order.setText(sRejReason);
	}

	@Override
	public MsgExecutionReport getExecutionReport() {
		return (new MsgExecutionReport.Builder(order)).build();
	}

}
