package com.simulator.controller.er.producer;

import com.simulator.model.messages.MsgExecutionReport;
import com.simulator.model.state.Order;
import com.simulator.model.tags.ExecType;

/**
 * 根据出入的订单,生成对应的执行报告
 * 
 * @author sunquan
 *
 */
public class ExecuteOrderImp implements ExecReportProducerInterf {

	private final Order order;
	private final double lastPx;
	private final double lastQty;

	public ExecuteOrderImp(Order inOrder, double lastPx, double lastQty) {
		this.order = inOrder;
		this.lastPx = lastPx;
		this.lastQty = lastQty;

		// update order 
		double newLeavesQty = order.getLeavesQty() - lastQty;
		order.setLeavesQty(newLeavesQty);
		ExecType ordStatus; // should be same as execType
		if (newLeavesQty > 0) {
			ordStatus = ExecType.PARTIALLY_FILLED;
		} else {
			ordStatus = ExecType.FILLED;
		}
		order.setOrdStatus(ordStatus);
		// calculate average price
		if (noFillsYet()) {
			order.setAvgPx(lastPx);
		} else if (needsAvgPxCalculation()) {
			// AvgPx = [(AvgPx * CumQty) + (LastShares * LastPx)] / (LastShares
			// + CumQty)
			double newAvgPx = ((order.getAvgPx() * order.getCumQty()) + (lastQty * lastPx))
					/ (lastQty + order.getCumQty());
			order.setAvgPx(newAvgPx);
		}
		order.setCumQty(order.getCumQty() + lastQty);
	}

	private boolean needsAvgPxCalculation() {
		return Double.compare(order.getAvgPx(), lastPx) != 0;
	}
    //判断剩余订单数量是否为0
	private boolean noFillsYet() {
		return Double.compare(order.getCumQty(), 0D) == 0;
	}

	@Override
	public MsgExecutionReport getExecutionReport() {
		MsgExecutionReport.Builder builder = new MsgExecutionReport.Builder(order);
		builder.lastPx(lastPx).lastQty(lastQty);
		return builder.build();
	}

}