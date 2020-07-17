package com.simulator.controller.er.observer;

import com.simulator.model.messages.MsgExecutionReport;

/**
 * Implementors will be called back whenever FIXBro produces an
 * {@link MsgExecutionReport}
 * 
 * @author sunquan
 *
 */
public interface ExecutionReportObserver {

	boolean onExecutionReport(MsgExecutionReport er);

}
