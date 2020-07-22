package com.simulator.model.state;

import com.simulator.model.tags.ExecType;
import com.simulator.model.tags.OrdType;
import com.simulator.model.tags.Side;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Getter;

/**
 * Implementation for the {@link com.simulator.view.OrderBlotter}
 * 
 * @author sunquan
 *
 */
public class OrderBean implements Order {

	private StringProperty senderCompID;
	private StringProperty targetCompID;
	private StringProperty orderID;
	private StringProperty origClOrdID;
	private StringProperty clOrdID;
	private StringProperty symbol;
	private StringProperty msgType;
	private DoubleProperty leavesQty;
	private DoubleProperty cumQty;
	private DoubleProperty orderQty;
	private DoubleProperty avgPx;
	private StringProperty ordStatus;
	private StringProperty ordType; //TAG35
	private StringProperty side;
	private StringProperty Text;
	private ExecType ordStatusEnum;
	private OrdType ordTypeEnum;
	private Side sideEnum;
	private StringProperty transactTime;

	// non mandatory fields
	private StringProperty price;

	public OrderBean() {
		setAvgPx(0);
		setCumQty(0);
		setOrdStatus(ExecType.NONE_YET);
		setOrderID("O" + System.nanoTime());
	}

	public OrderBean(Order from) {
		setMsgType(from.getMsgType());
		setAvgPx(from.getAvgPx());
		setClOrdID(from.getClOrdID());
		setCumQty(from.getCumQty());
		setLeavesQty(from.getLeavesQty());
		setOrderID(from.getOrderID());
		setOrdStatus(from.getOrdStatus());
		setOrdType(from.getOrdType());
		setPrice(from.getPrice());
		setOrigClOrdID(from.getOrigClOrdID());
		setOrderQty(from.getOrderQty());
		setSide(from.getSide());
		setSymbol(from.getSymbol());
		setTimeInForce(from.getTimeInForce());
		setSenderCompID(from.getSenderCompID());
		setTargetCompID(from.getTargetCompID());
		setTransactTime(from.getTransactTime());
	}
	
	@Override
	public String getMsgType() {
		return msgType.get();
	}
	//用于界面展示
	public StringProperty getMsgTypeProperty() {
		return msgType;
	}

	@Override
	public String getSenderCompID() {
		return senderCompID.get();
	}

	public StringProperty getSenderCompIDProperty() {
		return senderCompID;
	}

	@Override
	public String getTargetCompID() {
		return targetCompID.get();
	}

	public StringProperty getTargetCompIDProperty() {
		return targetCompID;
	}

	@Override
	public String getOrderID() {
		return orderID.get();
	}

	public StringProperty getIDProperty() {
		return orderID;
	}

	@Override
	public String getClOrdID() {
		return clOrdID.get();
	}

	public StringProperty getClOrdIDProperty() {
		return clOrdID;
	}

	@Override
	public String getOrigClOrdID() {
		return origClOrdID.get();
	}

	public StringProperty getOrigClOrdIDProperty() {
		return origClOrdID;
	}

	@Override
	public String getSymbol() {
		return symbol.get();
	}

	public StringProperty getSymbolProperty() {
		return symbol;
	}

	@Override
	public ExecType getOrdStatus() {
		return ordStatusEnum;
	}

	public StringProperty getOrdStatusProperty() {
		return ordStatus;
	}

	@Override
	public char getTimeInForce() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public Side getSide() {
		return sideEnum;
	}

	public StringProperty getSideProperty() {
		return side;
	}

	@Override
	public OrdType getOrdType() {
		return ordTypeEnum;
	}

	public StringProperty getOrdTypeProperty() {
		return ordType;
	}

	@Override
	public double getOrderQty() {
		return orderQty.get();
	}
	
	public DoubleProperty getOrderQtyProperty() {
		return orderQty;
	}

	@Override
	public double getAvgPx() {
		return avgPx.get();
	}

	public DoubleProperty getAvgPxProperty() {
		return avgPx;
	}

	@Override
	public Double getPrice() {
		return (price == null || price.get() == null ? null : Double.parseDouble(price.get()));
	}

	public StringProperty getPriceProperty() {
		return price;
	}

	@Override
	public double getCumQty() {
		return cumQty.get();
	}

	public DoubleProperty getCumQtyProperty() {
		return cumQty;
	}

	@Override
	public double getLeavesQty() {
		return leavesQty.get();
	}

	public DoubleProperty getLeavesProperty() {
		return leavesQty;
	}
	
	@Override
	public String getText() {
		if(Text==null) return "";
		else return Text.get();
	}
	//用于界面展示
	public StringProperty getTextProperty() {
		return Text;
	}
	 ////////////////set Method ///////////////
	@Override
	public void setMsgType(String msgtype) {
		//首次初始化
		if (msgType == null)
			msgType = new SimpleStringProperty(this, "msgType"); 
		this.msgType.set(msgtype);   
	}
	
	@Override
	public void setOrderID(String ID) {
		if (orderID == null)
			orderID = new SimpleStringProperty(this, "orderID");
		this.orderID.set(ID);
	}

	@Override
	public void setClOrdID(String clientID) {
		if (clOrdID == null)
			clOrdID = new SimpleStringProperty(this, "clOrdID");
		this.clOrdID.set(clientID);
	}

	@Override
	public void setOrigClOrdID(String origClientID) {
		if (origClOrdID == null)
			origClOrdID = new SimpleStringProperty(this, "origClOrdID");
		this.origClOrdID.set(origClientID);
	}

	@Override
	public void setSymbol(String sym) {
		if (symbol == null)
			symbol = new SimpleStringProperty(this, "symbol");
		this.symbol.set(sym);
	}

	@Override
	public void setOrdStatus(ExecType status) {
		this.ordStatusEnum = status;
		if (ordStatus == null)
			ordStatus = new SimpleStringProperty(this, "ordStatus");
		this.ordStatus.set(status.toString());
	}

	@Override
	public void setTimeInForce(char tif) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setSide(Side s) {
		this.sideEnum = s;
		if (side == null)
			side = new SimpleStringProperty(this, "side");
		this.side.set(s.toString());
	}

	@Override
	public void setOrdType(OrdType type) {
		this.ordTypeEnum = type;
		if (ordType == null)
			ordType = new SimpleStringProperty(this, "ordType");
		//撤单委托没有订单类型
		if(type != null)
		  this.ordType.set(type.toString());
	}

	@Override
	public void setOrderQty(double qty) {
		if (orderQty == null)
			orderQty = new SimpleDoubleProperty(this, "orderQty");
		this.orderQty.set(qty);
	}

	@Override
	public void setAvgPx(double price) {
		if (avgPx == null)
			avgPx = new SimpleDoubleProperty(this, "avgPx");
		this.avgPx.set(price);
	}

	@Override
	public void setPrice(Double px) {
		if (price == null)
			price = new SimpleStringProperty(this, "price");
		if (px != null)
			this.price.set(px.toString());
	}

	@Override
	public void setCumQty(double cum) {
		if (cumQty == null)
			cumQty = new SimpleDoubleProperty(this, "累积成交数量");
		this.cumQty.set(cum);
	}

	@Override
	public void setLeavesQty(double lvsQty) {
		if (leavesQty == null)
			leavesQty = new SimpleDoubleProperty(this, "剩余数量");
		leavesQty.set(lvsQty);
	}

	@Override
	public void setSenderCompID(String senderCID) {
		if (senderCompID == null)
			senderCompID = new SimpleStringProperty(this, "senderCompID");
		this.senderCompID.set(senderCID);
	}

	@Override
	public void setTargetCompID(String targetCID) {
		if (targetCompID == null)
			targetCompID = new SimpleStringProperty(this, "targetCompID");
		this.targetCompID.set(targetCID);
	}

	@Override
	public void setText(String stext) {
		if (Text == null)
			Text = new SimpleStringProperty(this, "Text");
		this.Text.set(stext); 
	}

	@Override
	public String getTransactTime() {
		return transactTime.get();
	}

	public StringProperty getTransactTimProperty() {
		return transactTime;
	}

	@Override
	public void setTransactTime(String transactTime) {
		if (this.transactTime == null)
			this.transactTime = new SimpleStringProperty(this, "创建时间");
		this.transactTime.set(transactTime);
	}

}
