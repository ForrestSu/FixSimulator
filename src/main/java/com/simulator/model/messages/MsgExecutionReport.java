package com.simulator.model.messages;

import com.simulator.model.state.ReadOrder;
import com.simulator.model.tags.ExecType;

/**
 * Immutable class. This execution structure is based on the structure
 * ExecutionReport from FIX 4.4. "Never changing" fields are available through
 * the Order object, like for instance Side and Symbol. The rest of the fields
 * are available via at the root level of this structure because they are
 * potentially different at each execution
 * 
 * @author sunquan
 *
 */
public class MsgExecutionReport {

	private final ReadOrder order;

	private final String clOrdID;
	private final String execID;
	private final ExecType execType;
	private final ExecType ordStatus;
	private final double avgPx;
	private final double cumQty;
	private final double leavesQty;
	private final String Text;

	// non mandatory
	private Double lastQty;
	private Double lastPx;

	public static class Builder {
		// mandatory fields
		private final ReadOrder order;
		// optional fields
		private double lastQty;
		private double lastPx;

		public Builder( ReadOrder order) {
			this.order = order;
		}

		public Builder lastPx(double lastPx) {
			this.lastPx = lastPx;
			return this;
		}

		public Builder lastQty(double lastQty) {
			this.lastQty = lastQty;
			return this;
		}

		public MsgExecutionReport build() {
			return new MsgExecutionReport(this);
		}

	}

	private MsgExecutionReport(Builder builder) {
		this.clOrdID = builder.order.getClOrdID();
		this.execID = "E" + System.currentTimeMillis(); // ensure unicity
		this.order = builder.order;
		this.avgPx = builder.order.getAvgPx();
		this.cumQty = builder.order.getCumQty();
		this.execType = builder.order.getOrdStatus();
		this.leavesQty = builder.order.getLeavesQty();
		this.ordStatus = builder.order.getOrdStatus(); //same as ExecType 
        this.Text = builder.order.getText();
        
		// non mandatory: prevent filling them with null values
		if ((this.execType == ExecType.PARTIALLY_FILLED) || (this.execType==ExecType.FILLED) ) {
			this.lastQty = builder.lastQty;
			this.lastPx = builder.lastPx;
		}
	}

	public String getExecID() {
		return execID;
	}

	public double getAvgPx() {
		return avgPx;
	}

	public double getCumQty() {
		return cumQty;
	}

	public ExecType getExecType() {
		return execType;
	}

	public Double getLastPx() {
		return lastPx;
	}

	public Double getLastQty() {
		return lastQty;
	}

	public double getLeavesQty() {
		return leavesQty;
	}

	public ReadOrder getOrder() {
		return order;
	}

	public ExecType getOrdStatus() {
		return ordStatus;
	}

	public String getClOrdID() {
		return clOrdID;
	}
	public String getText() {
		return Text;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[ExecutionReport ");
		builder.append("clOrdID: ").append(getClOrdID()).append(" execID:").append(execID).append(" execType:")
				.append(execType).append(" ordStatus:").append(ordStatus).append(" lastQty:").append(lastQty)
				.append(" lastPx:").append(lastPx).append(" leavesQty:").append(leavesQty).append(" cumQty:")
				.append(cumQty).append(" avgPx:").append(avgPx).append(" ").append(order).append("]");
		return builder.toString();
	}

}
