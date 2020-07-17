package com.simulator.test.controller.er.producer;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import com.simulator.controller.er.producer.PendingNewOrderImp;
import com.simulator.model.messages.MsgExecutionReport;
import com.simulator.model.state.Order;
import com.simulator.model.state.OrderBean;
import com.simulator.model.tags.ExecType;

@RunWith(JUnit4.class)
public class PendingNewOrderTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void incompleteOrderShouldThrowNPE_NeedsClOrdID_And_LeavesQty() {
		Order order = new OrderBean();
		PendingNewOrderImp pnOrder = new PendingNewOrderImp(order);

		thrown.expect(NullPointerException.class);
		pnOrder.getExecutionReport();
	}
	
	@Test
	public void incompleteOrderShouldThrowNPE_NeedsLeavesQty() {
		Order order = new OrderBean();
		order.setClOrdID("id1");

		PendingNewOrderImp pnOrder = new PendingNewOrderImp(order);
		thrown.expect(NullPointerException.class);
		pnOrder.getExecutionReport();
	}
	
	@Test
	public void pendingNewOrder() {
		Order order = new OrderBean();
		order.setClOrdID("id1");
		order.setLeavesQty(100);
		order.setOrderQty(100);
		MsgExecutionReport pendingEr = new PendingNewOrderImp(order).getExecutionReport();

		assertEquals(0, order.getAvgPx(), 0);
		assertEquals(0, pendingEr.getAvgPx(), 0);

		assertEquals(0, order.getCumQty(), 0);
		assertEquals(0, pendingEr.getCumQty(), 0);

		assertEquals(100, order.getLeavesQty(), 0);
		assertEquals(100, pendingEr.getLeavesQty(), 0);

		assertEquals(ExecType.PENDING_NEW, order.getOrdStatus());
		assertEquals(ExecType.PENDING_NEW, pendingEr.getOrdStatus());
	}

}
