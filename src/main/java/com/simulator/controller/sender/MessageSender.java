package com.simulator.controller.sender;

import com.simulator.model.messages.NewOrderSingle;

/**
 * Point of entry for messages coming from the external world
 * 
 * @author sunquan
 *
 */
public interface MessageSender {

	void sendNewOrderSingle(NewOrderSingle nos);

	// TODO impl. others
}
