package com.simulator.controller.qfj;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simulator.controller.er.observer.ExecutionReportObserver;
import com.simulator.model.messages.MsgExecutionReport;
import com.simulator.model.state.ReadOrder;

import quickfix.Session;
import quickfix.SessionNotFound;
import quickfix.field.AvgPx;
import quickfix.field.ClOrdID;
import quickfix.field.CumQty;
import quickfix.field.CxlRejReason;
import quickfix.field.ExecBroker;
import quickfix.field.ExecID;
import quickfix.field.ExecTransType;
import quickfix.field.ExecType;
import quickfix.field.LastPx;
import quickfix.field.LastShares;
import quickfix.field.LeavesQty;
import quickfix.field.OrdStatus;
import quickfix.field.OrderID;
import quickfix.field.OrderQty;
import quickfix.field.SenderCompID;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.TargetCompID;
import quickfix.field.Text;
import quickfix.field.TransactTime;
import quickfix.fix42.Message;

/**
 * @author sunquan
 *
 */
public class QFJApplicationOut implements ExecutionReportObserver {

	private static final Logger logger = LoggerFactory.getLogger(QFJApplicationOut.class);
   
	//发送FIX消息
	private static boolean sendFixMsg(Message message){
		boolean ret=false;
		try {
			Session.sendToTarget(message); // reverse
			ret = true;
			logger.info("FIX消息:{}",message);
		} catch (SessionNotFound e) {
			logger.error("无法发送消息{}:{}",message ,e.getMessage());
		}
		return ret;
    }
	@Override
	public boolean onExecutionReport(MsgExecutionReport er) {
		Message message = null;
		//如果是撤单废单
		if(er.getOrdStatus() == com.simulator.model.tags.ExecType.CANCEL_REJECTED)
		{
			message = getRejectMessage(er) ;
			return  sendFixMsg(message);
		}else
		{
		    message = getExecMessage(er);
		    return  sendFixMsg(message);
		}
	}
	 //生成一笔撤单拒绝报告
	private quickfix.fix42.OrderCancelReject getRejectMessage(MsgExecutionReport er)
	{
		quickfix.fix42.OrderCancelReject orderER = new quickfix.fix42.OrderCancelReject();
		ReadOrder order = er.getOrder();
		orderER.getHeader().setString(SenderCompID.FIELD,order.getTargetCompID());
		orderER.getHeader().setString(TargetCompID.FIELD,order.getSenderCompID());
		//DeliverToCompID
		orderER.set(new ClOrdID(er.getClOrdID()));
		orderER.set(new OrderID(order.getOrderID()));
		/*
		 * 虚拟一个执行券商
		 */
		orderER.set(new ExecBroker("Simulator")); 
		orderER.set(new OrdStatus(er.getOrdStatus().toChar()));
		/*
		 * 0=Too late to cancel
		 * 1=Unknown order
		 * 2=Broker Option
		 * 3=Order already in Pending Cancel or Pending Replace status
		 */
		orderER.set(new CxlRejReason(2));//撤单废单
		orderER.set(new Text(er.getText()));//具体说明
		//执行类型TAG20 置为0
		orderER.set(new TransactTime());//这里默认是当前日期
		return orderER;
	}
	//生成一个执行报告
	private quickfix.fix42.ExecutionReport getExecMessage(MsgExecutionReport er)
	{
		quickfix.fix42.ExecutionReport orderER = new quickfix.fix42.ExecutionReport();
		ReadOrder order = er.getOrder();
		orderER.getHeader().setString(SenderCompID.FIELD,order.getTargetCompID());
		orderER.getHeader().setString(TargetCompID.FIELD,order.getSenderCompID());
		//DeliverToCompID
		orderER.set(new ClOrdID(er.getClOrdID()));
		orderER.set(new OrderID(order.getOrderID()));
		orderER.set(new ExecID(er.getExecID()));
		orderER.set(new OrdStatus(er.getOrdStatus().toChar()));
		orderER.set(new ExecType(er.getExecType().toChar()));
		orderER.set(new CumQty(er.getCumQty()));
		orderER.set(new AvgPx(er.getAvgPx()));
		orderER.set(new OrderQty(er.getOrderQty()));
		orderER.set(new LeavesQty(er.getLeavesQty()));
		orderER.set(new Side(er.getOrder().getSide().toChar()));
		orderER.set(new Symbol(er.getOrder().getSymbol()));
		orderER.set(new Text(er.getText()));
		//执行类型TAG20 置为0
		orderER.set(new ExecTransType('0'));
		orderER.set(new TransactTime());//这里默认是当前日期
		
		//如果是成交
		if ( (er.getExecType() == com.simulator.model.tags.ExecType.PARTIALLY_FILLED)||
			 (er.getExecType() == com.simulator.model.tags.ExecType.FILLED)
		   ){
			orderER.set(new LastPx(er.getLastPx()));
			orderER.set(new LastShares(er.getLastQty()));//single deal amount
			//期货成交需要送成交时间字段
			//10044	LocalTransactTime
			orderER.setUtcTimeStamp(10044, new Date());
		}
		return orderER;
	}
}
