package com.simulator.controller.er.processor;

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.simulator.controller.er.observer.ExecutionReportObservable;
import com.simulator.controller.er.observer.ExecutionReportObserver;
import com.simulator.model.messages.ExecutionReport;

/**
 * Distributes the {@link ExecutionReport}s produced by FIXBro to all
 * {@link ExecutionReportObserver}s
 * 
 * @author sunquan
 *
 */
public class ExecutionReportProcessorImpl implements ExecutionReportProcessor, ExecutionReportObservable {
	
	private final Logger log = LoggerFactory.getLogger(ExecutionReportProcessorImpl.class);

	private final List<ExecutionReportObserver> erObserverList;
	private final ExecutorService executorService;

	public ExecutionReportProcessorImpl(List<ExecutionReportObserver> observers) {
		erObserverList = new CopyOnWriteArrayList<>(observers);
		executorService = Executors.newCachedThreadPool();
	}

	@Override
	public void process(ExecutionReport er) {
		erObserverList.forEach(observer -> {
			log.info("Sending {}", er);
			executorService.execute(() -> observer.onExecutionReport(er));
		});
	}

	@Override
	public void registerExecutionReportObservable(ExecutionReportObserver observer) {
		erObserverList.add(observer);
	}

	@Override
	public void unRegisterExecutionReportObservable(ExecutionReportObserver observer) {
		Iterator<ExecutionReportObserver> it = erObserverList.iterator();
		while (it.hasNext()) {
			ExecutionReportObserver o = it.next();
			if (o.equals(observer)) {
				it.remove();
				return;
			}
		}
	}

}
