package com.simulator.controller.er.observer;

/**
 * @author sunquan
 *
 */
public interface ExecutionReportObservable {

	void registerExecutionReportObservable(ExecutionReportObserver observer);

	void unRegisterExecutionReportObservable(ExecutionReportObserver observer);
}
