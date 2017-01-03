package com.simulator.model.messages;

import java.util.Date;

import com.simulator.model.tags.OrdType;
import com.simulator.model.tags.Side;

/**
 * Class representing a 35=D message
 * 
 * @author sunquan
 *
 */
public class NewOrderSingle {

	// MANDATORY FIELDS
	private final String senderCompID; // 49
	private final String targetCompID; // 56
	private final String clOrdID; // 11
	private final String symbol; // 55
	private final Side side; // 54
	private final Date transactTime; // 60
	private final double orderQty; // 38
	private final OrdType ordType; // 40

	// non mandatory
	private Double price; // 44

	private NewOrderSingle(Builder builder) {
		this.senderCompID = builder.senderCompID;
		this.targetCompID = builder.targetCompID;
		this.clOrdID = builder.clOrdID;
		this.symbol = builder.symbol;
		this.side = builder.side;
		this.transactTime = builder.transactTime;
		this.orderQty = builder.orderQty;
		this.ordType = builder.ordType;
		this.price = builder.price;
	}

	public String getSenderCompID() {
		return senderCompID;
	}

	public String getTargetCompID() {
		return targetCompID;
	}
	
	public String getClOrdID() {
		return clOrdID;
	}

	public String getSymbol() {
		return symbol;
	}

	public Side getSide() {
		return side;
	}

	public Date getTransactTime() {
		return transactTime;
	}

	public double getOrderQty() {
		return orderQty;
	}

	public OrdType getOrdType() {
		return ordType;
	}

	public Double getPrice() {
		return price;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("[NewOrderSingle ");
		builder.append("senderCompID:").append(senderCompID).append(" targetCompID:").append(targetCompID)
				.append(" clOrdID:").append(clOrdID).append(" orderQty:").append(orderQty).append(" ordType:")
				.append(ordType).append(" price:").append(price).append(" side:").append(side).append(" symbol:")
				.append(symbol).append(" transactTime:").append(transactTime).append("]");
		return builder.toString();
	}

	public final static class Builder {

		private String senderCompID;
		private String targetCompID;
		private String clOrdID;
		private String symbol;
		private Side side;
		private Date transactTime;
		private double orderQty;
		private OrdType ordType;

		// non mandatory
		private Double price;

		public Builder() {
		}

		public Builder senderCompID(String senderCompID) {
			this.senderCompID = senderCompID;
			return this;
		}

		public Builder targetCompID(String targetCompID) {
			this.targetCompID = targetCompID;
			return this;
		}

		public Builder clOrdID(String clOrdID) {
			this.clOrdID = clOrdID;
			return this;
		}

		public Builder side(char side) {
			this.side = Side.valueOf(side);
			return this;
		}

		public Builder symbol(String sym) {
			this.symbol = sym;
			return this;
		}

		public Builder transactTime(Date tt) {
			this.transactTime = (Date) tt.clone();
			return this;
		}

		public Builder orderQty(double qty) {
			this.orderQty = qty;
			return this;
		}

		public Builder ordType(char type) {
			this.ordType = OrdType.valueOf(type);
			return this;
		}

		public Builder price(double px) {
			this.price = px;
			return this;
		}

		public NewOrderSingle build() {
			return new NewOrderSingle(this);
		}
	}

}
