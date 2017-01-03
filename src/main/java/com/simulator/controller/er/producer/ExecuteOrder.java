package com.simulator.controller.er.producer;

import com.simulator.model.messages.ExecutionReport;
import com.simulator.model.state.Order;
import com.simulator.model.tags.ExecType;
import com.simulator.model.tags.OrdStatus;

/**
 * Applies the execution on the current order and produces the corresponding
 * {@link ExecutionReport} report
 * 
 * @author sunquan
 *
 */
public class ExecuteOrder implements ExecutionReportProducer {

	private final Order order;
	private final double lastPx;
	private final double lastQty;

	public ExecuteOrder(Order order, double lastPx, double lastQty) {
		this.order = order;
		this.lastPx = lastPx;
		this.lastQty = lastQty;

		// update order
		double newLeavesQty = order.getLeavesQty() - lastQty;
		order.setLeavesQty(newLeavesQty);
		OrdStatus ordStatus; // should be same as execType
		if (newLeavesQty > 0) {
			ordStatus = OrdStatus.PARTIALLY_FILLED;
		} else {
			ordStatus = OrdStatus.FILLED;
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

	private boolean noFillsYet() {
		return Double.compare(order.getCumQty(), 0D) == 0;
	}

	@Override
	public ExecutionReport getExecutionReport() {
		ExecutionReport.Builder builder = new ExecutionReport.Builder(ExecType.TRADE, order);
		builder.lastPx(lastPx).lastQty(lastQty);
		return builder.build();
	}

}