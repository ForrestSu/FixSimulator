package com.simulator.controller.sender;

import com.simulator.model.messages.NewOrderSingle;

/**
 * Sends the message without thread switch
 * 
 * @author sunquan
 *
 */
public class DirectMessageSender implements MessageSender {

	@Override
	public void sendNewOrderSingle(NewOrderSingle nos) {
		new ProcessNewOrder(nos);
	}

}
