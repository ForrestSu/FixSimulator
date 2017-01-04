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
import quickfix.field.DeliverToCompID;
import quickfix.field.ExecID;
import quickfix.field.ExecTransType;
import quickfix.field.ExecType;
import quickfix.field.LastPx;
import quickfix.field.LastShares;
import quickfix.field.LeavesQty;
import quickfix.field.OrdStatus;
import quickfix.field.OrderID;
import quickfix.field.SenderCompID;
import quickfix.field.Side;
import quickfix.field.Symbol;
import quickfix.field.TargetCompID;
import quickfix.field.Text;
import quickfix.field.TransactTime;

/**
 * @author sunquan
 *
 */
public class QFJApplicationOut implements ExecutionReportObserver {

	private static final Logger logger = LoggerFactory.getLogger(QFJApplicationOut.class);

	@Override
	public boolean onExecutionReport(MsgExecutionReport er) {
		boolean ret=false;
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
		try {
			Session.sendToTarget(orderER); // reverse
			ret = true;
			logger.info("ExecutionReport:{}",orderER);
		} catch (SessionNotFound e) {
			logger.error("无法发送消息{}:{}",orderER ,e.getMessage());
			// TODO implement rollback
		}
		return ret;
	}

}
