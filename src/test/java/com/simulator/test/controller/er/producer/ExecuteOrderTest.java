package com.simulator.test.controller.er.producer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.simulator.controller.er.producer.ExecuteOrderImp;
import com.simulator.model.messages.MsgExecutionReport;
import com.simulator.model.state.Order;
import com.simulator.model.state.OrderBean;
import com.simulator.model.tags.ExecType;

/**
 * @author sunquan
 *
 */
@RunWith(JUnit4.class)
public class ExecuteOrderTest {

	@Test(expected = NullPointerException.class)
	public void executeIncompleteOrder() {
		Order order = new OrderBean();
		new ExecuteOrderImp(order, 1.12, 1000);
	}
	
	@Test
	public void fillOrder() {
		Order order = new OrderBean();
		order.setClOrdID("id1");
		order.setCumQty(0);
		order.setLeavesQty(100);
		ExecuteOrderImp executeOrder = new ExecuteOrderImp(order, 1.12, 100);
		MsgExecutionReport er = executeOrder.getExecutionReport();
		
		assertEquals(100, order.getCumQty(), 0);
		assertEquals(100, er.getCumQty(), 0);
		
		assertEquals(0, order.getLeavesQty(), 0);
		assertEquals(0, er.getLeavesQty(), 0);
		
		assertEquals(1.12, order.getAvgPx(), 0);
		assertEquals(1.12, er.getAvgPx(), 0);
		
		assertEquals(ExecType.FILLED, order.getOrdStatus());
		assertEquals(ExecType.FILLED, er.getOrdStatus());
	}
	
	@Test
	public void partialFillOrder() {
		Order order = new OrderBean();
		order.setClOrdID("id1");
		order.setCumQty(0);
		order.setLeavesQty(100);
		
		// partial fill half of the quantity
		ExecuteOrderImp executeOrder = new ExecuteOrderImp(order, 1.12, 50);
		MsgExecutionReport er = executeOrder.getExecutionReport();
		
		assertEquals(50, order.getCumQty(), 0);
		assertEquals(50, er.getCumQty(), 0);
		
		assertEquals(50, order.getLeavesQty(), 0);
		assertEquals(50, er.getLeavesQty(), 0);
		
		assertEquals(1.12, order.getAvgPx(), 0);
		assertEquals(1.12, er.getAvgPx(), 0);
		
		assertEquals(ExecType.PARTIALLY_FILLED, order.getOrdStatus());
		assertEquals(ExecType.PARTIALLY_FILLED, er.getOrdStatus());

		// partial fill the other half
		executeOrder = new ExecuteOrderImp(order, 1.14, 50);
		er = executeOrder.getExecutionReport();
		
		assertEquals(100, order.getCumQty(), 0);
		assertEquals(100, er.getCumQty(), 0);
		
		assertEquals(0, order.getLeavesQty(), 0);
		assertEquals(0, er.getLeavesQty(), 0);
		
		assertEquals(1.13, order.getAvgPx(), 0);
		assertEquals(1.13, er.getAvgPx(), 0);
		
		assertEquals(ExecType.FILLED, order.getOrdStatus());
		assertEquals(ExecType.FILLED, er.getOrdStatus());
	}
}
