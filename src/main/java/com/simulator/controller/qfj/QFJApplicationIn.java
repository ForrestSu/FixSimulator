package com.simulator.controller.qfj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simulator.controller.sender.MessageSender;
import com.simulator.model.messages.NewOrderSingle;

import quickfix.DoNotSend;
import quickfix.FieldNotFound;
import quickfix.IncorrectDataFormat;
import quickfix.IncorrectTagValue;
import quickfix.Message;
import quickfix.Message.Header;
import quickfix.RejectLogon;
import quickfix.SessionID;
import quickfix.UnsupportedMessageType;
import quickfix.field.MsgType;
import quickfix.field.SenderCompID;
import quickfix.field.TargetCompID;
import quickfix.fix42.Logon;
import quickfix.fix42.Logout;


/**
 * This component listens to FIX messages coming from clients, transforms them
 * into FixBro datamodel and sends them in for processing
 * 
 * @author sunquan
 *
 */
public class QFJApplicationIn extends quickfix.fix42.MessageCracker implements quickfix.Application {

	private static final Logger logger = LoggerFactory.getLogger(QFJApplicationIn.class);
	private final MessageSender messageSender;

	public QFJApplicationIn(MessageSender messageSender) {
		this.messageSender = messageSender;
	}

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
	public void onMessage(quickfix.fix42.NewOrderSingle qfjNos, SessionID sessionID)
			throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
		logger.info("NewOrderSingle received on session {} => {}", sessionID, qfjNos);
		NewOrderSingle.Builder builder = new NewOrderSingle.Builder();
		// mandatory fields
        Header header = qfjNos.getHeader();
		builder.senderCompID(header.getField(new SenderCompID()).getValue())
		       .targetCompID(header.getField(new TargetCompID()).getValue())
			   .clOrdID(qfjNos.getClOrdID().getValue()).orderQty(qfjNos.getOrderQty().getValue())
			   .ordType(qfjNos.getOrdType().getValue()).side(qfjNos.getSide().getValue())
			   .symbol(qfjNos.getSymbol().getValue())
				//.symbol(qfjNos.getInstrument().getSymbol().getValue()) FIX4.4
				.transactTime(qfjNos.getTransactTime().getValue());

		// non mandatory fields
		if (qfjNos.isSetPrice())
			builder.price(qfjNos.getPrice().getValue());
		NewOrderSingle nos = builder.build();
		logger.info("Transformed {}", nos);
		messageSender.sendNewOrderSingle(nos);
	}
    //2 cancel order
	@Override
	public void onMessage(quickfix.fix42.OrderCancelRequest qfjOcr, SessionID sessionID)
			throws FieldNotFound, UnsupportedMessageType, IncorrectTagValue {
		logger.info("OrderCancelRequest received on session {} => {}", sessionID, qfjOcr);
	}
	
	
}
