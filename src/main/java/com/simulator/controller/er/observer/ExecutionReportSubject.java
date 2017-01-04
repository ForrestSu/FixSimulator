package com.simulator.controller.er.observer;

/**
 * @author sunquan
 *
 */
public interface ExecutionReportSubject {

	void registerExecutionReportObservable(ExecutionReportObserver observer);

	void unRegisterExecutionReportObservable(ExecutionReportObserver observer);
	
}
