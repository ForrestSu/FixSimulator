package com.simulator.controller.er.processor;

import com.simulator.model.messages.ExecutionReport;

/**
 * This component gets {@code ExecutionReport} messages and processes them
 * applying a certain strategy
 * 
 * @author sunquan
 *
 */
public interface ExecutionReportProcessor {

	public void process(ExecutionReport er);
}
