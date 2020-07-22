package com.simulator.controller.qfj;

import com.simulator.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simulator.model.state.Order;
import com.simulator.model.state.OrderBean;
import com.simulator.model.state.OrderBook;
import com.simulator.model.tags.OrdType;

import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.field.MsgType;
import quickfix.field.SenderCompID;
import quickfix.field.TargetCompID;
import quickfix.fix42.Logon;
import quickfix.fix42.Logout;
import quickfix.fix42.NewOrderSingle;
import quickfix.fix42.OrderCancelRequest;

import java.time.format.DateTimeFormatter;


/**
 * This component listens to FIX messages coming from clients, transforms them
 * into FixBro datamodel and sends them in for processing
 * 
 * @author sunquan
 *
 */
public class QFJApplicationIn extends quickfix.fix42.MessageCracker implements quickfix.Application {

	private static final Logger logger = LoggerFactory.getLogger(QFJApplicationIn.class);
//	private final MessageSender messageSender;

	// callback notifying of every "admin" message (eg. logon,logout, heartbeat)
	// received from the outside world
	@Override
	public void fromAdmin(Message msg, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, RejectLogon {
		  String type = msg.getHeader().getString(MsgType.FIELD);
		  if(type.equals(Logon.MSGTYPE))
		    logger.info("session {} 登陆!",sessionID); 
		  else if (type.equals(Logout.MSGTYPE))
			logger.info("session {} 退出登录!",sessionID); 
		  else
			logger.debug("fromAdmin on session {}. Msg {}", sessionID, msg);
	}

	// callback notifying of every "app" message (eg. NOS, OCR, OCRR) received
	// from the outside world
	@Override
	public void fromApp(Message msg, SessionID sessionID)
			throws FieldNotFound, IncorrectDataFormat, IncorrectTagValue, UnsupportedMessageType {
			logger.info("fromApp on session {}. Msg {}", sessionID, msg);
		crack(msg, sessionID);
	}

	@Override
	public void onCreate(SessionID session) {
		logger.info("creating {}", session);
	}

	@Override
	public void onLogon(SessionID sessionID) {
		logger.info("loging on {}", sessionID);
	}

	@Override
	public void onLogout(SessionID sessionID) {
		logger.info("loging out {}", sessionID);
	}

	@Override
	public void toAdmin(Message msg, SessionID sessionID) {
		logger.info("toAdmin {}", sessionID);
	}

	@Override
	public void toApp(Message msg, SessionID sessionID) throws DoNotSend {
		logger.info("toApp on session {}. Msg {}", sessionID, msg);
	}
    //1 new order
	@Override 
	public void onMessage(NewOrderSingle message, SessionID sessionID)
			throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
		logger.info("session {} 收到一笔新订单!", sessionID);
		//将这笔委托显示在界面上
		Order order = new OrderBean();
		order.setMsgType(message.getHeader().getString(MsgType.FIELD));
		order.setSenderCompID(message.getHeader().getString(SenderCompID.FIELD));
		order.setTargetCompID(message.getHeader().getString(TargetCompID.FIELD));
		order.setClOrdID(message.getClOrdID().getValue());
		order.setLeavesQty(message.getOrderQty().getValue());
		//订单类型为枚举类型
		order.setOrdType( OrdType.valueOf( message.getOrdType().getValue() ));
		//无原始订单编号
		order.setOrigClOrdID("0");
		//如果是市价订单,则没有价格
		if(message.isSetPrice())
			order.setPrice(message.getPrice().getValue());
		order.setOrderQty(message.getOrderQty().getValue());
		//买卖方向为枚举类型
		order.setSide(com.simulator.model.tags.Side.valueOf(message.getSide().getValue()));
		order.setSymbol(message.getSymbol().getValue());
		// 58 备注
		if (message.isSetText()) {
			order.setText(message.getText().getValue());
		}
		if (message.isSetTransactTime()) {
			String date = DateUtil.DateTimeFormat(message.getTransactTime().getValue());
			order.setTransactTime(date);
		}
		/*
		 * 将这笔委托显示在界面上
		 */
		OrderBook.getInstance().addOrder(order);  
	}
    //2 cancel order
	@Override
	public void onMessage(OrderCancelRequest message, SessionID sessionID)
			throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
		logger.info("session {} 收到一笔撤单!", sessionID);
		Order order = new OrderBean();
		order.setMsgType(message.getHeader().getString(MsgType.FIELD));
		order.setSenderCompID(message.getHeader().getString(SenderCompID.FIELD));
		order.setTargetCompID(message.getHeader().getString(TargetCompID.FIELD));
		//订单编号
		order.setClOrdID(message.getClOrdID().getValue());
		order.setLeavesQty(message.getOrderQty().getValue());
		//原始订单编号
		order.setOrigClOrdID(message.getOrigClOrdID().getValue());
		//撤单数量
		order.setOrderQty(message.getOrderQty().getValue()); 
		//买卖方向为枚举类型
		order.setSide(com.simulator.model.tags.Side.valueOf(message.getSide().getValue()));
		order.setSymbol(message.getSymbol().getValue());
		if(message.isSetText())
		   order.setText(message.getText().getValue());
		/*
		 * 将这笔委托显示在界面上
		 */
		OrderBook.getInstance().addOrder(order);  
	}
	
	
}
