package com.simulator.controller.er.observer;

import com.simulator.model.messages.ExecutionReport;

/**
 * Implementors will be called back whenever FIXBro produces an
 * {@link ExecutionReport}
 * 
 * @author sunquan
 *
 */
public interface ExecutionReportObserver {

	void onExecutionReport(ExecutionReport er);

}
