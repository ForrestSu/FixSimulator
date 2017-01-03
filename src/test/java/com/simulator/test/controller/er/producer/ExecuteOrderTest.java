package com.simulator.test.controller.er.producer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.simulator.controller.er.producer.ExecuteOrder;
import com.simulator.model.messages.ExecutionReport;
import com.simulator.model.state.Order;
import com.simulator.model.state.OrderBean;
import com.simulator.model.tags.OrdStatus;

/**
 * @author sunquan
 *
 */
@RunWith(JUnit4.class)
public class ExecuteOrderTest {

	@Test(expected = NullPointerException.class)
	public void executeIncompleteOrder() {
		Order order = new OrderBean();
		new ExecuteOrder(order, 1.12, 1000);
	}
	
	@Test
	public void fillOrder() {
		Order order = new OrderBean();
		order.setClOrdID("id1");
		order.setCumQty(0);
		order.setLeavesQty(100);
		ExecuteOrder executeOrder = new ExecuteOrder(order, 1.12, 100);
		ExecutionReport er = executeOrder.getExecutionReport();
		
		assertEquals(100, order.getCumQty(), 0);
		assertEquals(100, er.getCumQty(), 0);
		
		assertEquals(0, order.getLeavesQty(), 0);
		assertEquals(0, er.getLeavesQty(), 0);
		
		assertEquals(1.12, order.getAvgPx(), 0);
		assertEquals(1.12, er.getAvgPx(), 0);
		
		assertEquals(OrdStatus.FILLED, order.getOrdStatus());
		assertEquals(OrdStatus.FILLED, er.getOrdStatus());
	}
	
	@Test
	public void partialFillOrder() {
		Order order = new OrderBean();
		order.setClOrdID("id1");
		order.setCumQty(0);
		order.setLeavesQty(100);
		
		// partial fill half of the quantity
		ExecuteOrder executeOrder = new ExecuteOrder(order, 1.12, 50);
		ExecutionReport er = executeOrder.getExecutionReport();
		
		assertEquals(50, order.getCumQty(), 0);
		assertEquals(50, er.getCumQty(), 0);
		
		assertEquals(50, order.getLeavesQty(), 0);
		assertEquals(50, er.getLeavesQty(), 0);
		
		assertEquals(1.12, order.getAvgPx(), 0);
		assertEquals(1.12, er.getAvgPx(), 0);
		
		assertEquals(OrdStatus.PARTIALLY_FILLED, order.getOrdStatus());
		assertEquals(OrdStatus.PARTIALLY_FILLED, er.getOrdStatus());

		// partial fill the other half
		executeOrder = new ExecuteOrder(order, 1.14, 50);
		er = executeOrder.getExecutionReport();
		
		assertEquals(100, order.getCumQty(), 0);
		assertEquals(100, er.getCumQty(), 0);
		
		assertEquals(0, order.getLeavesQty(), 0);
		assertEquals(0, er.getLeavesQty(), 0);
		
		assertEquals(1.13, order.getAvgPx(), 0);
		assertEquals(1.13, er.getAvgPx(), 0);
		
		assertEquals(OrdStatus.FILLED, order.getOrdStatus());
		assertEquals(OrdStatus.FILLED, er.getOrdStatus());
	}
}
