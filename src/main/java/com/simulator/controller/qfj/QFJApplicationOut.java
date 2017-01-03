package com.simulator.controller.qfj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simulator.controller.er.observer.ExecutionReportObserver;
import com.simulator.model.messages.ExecutionReport;
import com.simulator.model.state.ReadOnlyOrder;

import quickfix.Session;
import quickfix.SessionNotFound;
import quickfix.field.AvgPx;
import quickfix.field.ClOrdID;
import quickfix.field.CumQty;
import quickfix.field.ExecID;
import quickfix.field.ExecType;
import quickfix.field.LastPx;
import quickfix.field.LastShares;
import quickfix.field.LeavesQty;
import quickfix.field.OrdStatus;
import quickfix.field.OrderID;
import quickfix.field.Side;
import quickfix.field.Symbol;

/**
 * @author sunquan
 *
 */
public class QFJApplicationOut implements ExecutionReportObserver {

	private static final Logger logger = LoggerFactory.getLogger(QFJApplicationOut.class);

	@Override
	public void onExecutionReport(ExecutionReport er) {
		quickfix.fix42.ExecutionReport qfjER = new quickfix.fix42.ExecutionReport();
		ReadOnlyOrder order = er.getOrder();
		qfjER.set(new ClOrdID(er.getClOrdID()));
		qfjER.set(new OrderID(order.getOrderID()));
		qfjER.set(new ExecID(er.getExecID()));
		qfjER.set(new OrdStatus(er.getOrdStatus().toChar()));
		qfjER.set(new ExecType(er.getExecType().toChar()));
		qfjER.set(new CumQty(er.getCumQty()));
		qfjER.set(new AvgPx(er.getAvgPx()));
		qfjER.set(new LeavesQty(er.getLeavesQty()));
		qfjER.set(new Side(er.getOrder().getSide().toChar()));
		//qfjER.set(new Instrument(new Symbol(er.getOrder().getSymbol()))); FIX4.4
		qfjER.set(new Symbol(er.getOrder().getSymbol()));

		// non mandatory
		if (er.getExecType() == com.simulator.model.tags.ExecType.TRADE) {
			qfjER.set(new LastPx(er.getLastPx()));
			//qfjER.set(new LastQty(er.getLastQty())); Fix4.4
			qfjER.set(new LastPx(er.getLastPx()));
			qfjER.set(new LastShares(er.getLastQty()));//single deal amount
		}
		try {
			Session.sendToTarget(qfjER); // reverse
			logger.info("Sent to [SenderCompID={},TargetCompID={}] {}. Msg {}", order.getSenderCompID(),
					order.getTargetCompID(), qfjER);
		} catch (SessionNotFound e) {
			logger.error("Session to [SenderCompID={},TargetCompID={}] not found. Could not send msg {}",
					order.getSenderCompID(), order.getTargetCompID(), qfjER);
			// TODO implement rollback
		}
	}

}
