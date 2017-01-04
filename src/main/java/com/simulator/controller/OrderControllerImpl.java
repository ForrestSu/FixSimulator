package com.simulator.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simulator.controller.er.processor.ExecutionReportProcessor;
import com.simulator.controller.er.producer.*;
import com.simulator.model.messages.MsgExecutionReport;
import com.simulator.model.state.Order;

/**
 * Applies the action asked by the view and sends the Execution out
 * 
 * @author sunquan
 *
 */
public class OrderControllerImpl implements OrderController {

	private final Logger logger = LoggerFactory.getLogger(OrderControllerImpl.class);

	private final ExecutionReportProcessor erProcessor;

	public OrderControllerImpl(ExecutionReportProcessor erProcessor) {
		this.erProcessor = erProcessor;
	}
    
	//实际发送执行报告
	private void DoOrderExecutionReport(ExecReportProducerInterf executionProducer) {
		MsgExecutionReport er = executionProducer.getExecutionReport();
		erProcessor.process(er);
	}
    
	//执行报告
	@Override
	public void execute(Order order, double lastPx, double lastQty) {
		logger.info("Executing order {} with lastPx {} and lastQty {}",order.getOrderID(), lastPx, lastQty);
		DoOrderExecutionReport(new ExecuteOrderImp(order, lastPx, lastQty));
	}
    //订单拒绝 
	@Override
	public void reject(Order order, String reason) {
		logger.info("To Comfirm order {}", order.getOrderID());
		DoOrderExecutionReport(new RejectOrderImp(order,reason));
	}
    //撤单成交，注意数量
	@Override
	public void cancel(Order order) {
		// TODO Auto-generated method stub
		logger.info("To Canceled order {}", order.getOrderID());
		DoOrderExecutionReport(new CancelAckOrderImp(order));
	}

	@Override
	public void doneForDay(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void expire(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void replace(Order order) {
		// TODO Auto-generated method stub

	}
	
	 //发送委托确认
	@Override
	public void acknowledge(Order order) {
		logger.info("To Comfirm order {}", order.getOrderID());
		DoOrderExecutionReport(new ComfirmOrderImp(order));
	}
  
	@Override
	public void pendingNew(Order order) {
		logger.info("To Pending New, order {}", order.getOrderID());
		DoOrderExecutionReport(new PendingNewOrderImp(order));
	}

	@Override
	public void pendingCancel(Order order) {
		// TODO Auto-generated method stub

	}

	@Override
	public void pendingReplace(Order order) {
		// TODO Auto-generated method stub

	}

}
