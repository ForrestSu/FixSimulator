package com.simulator.main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

import com.simulator.controller.OrderController;
import com.simulator.controller.OrderControllerImpl;
import com.simulator.controller.er.observer.ExecutionReportObserver;
import com.simulator.controller.er.processor.ExecutionReportProcessor;
import com.simulator.controller.er.processor.ExecutionReportProcessorImpl;
import com.simulator.controller.qfj.QFJAcceptor;
import com.simulator.controller.qfj.QFJApplicationIn;
import com.simulator.controller.qfj.QFJApplicationOut;
import com.simulator.model.state.Order;
import com.simulator.model.state.OrderBean;
import com.simulator.model.state.OrderBook;
import com.simulator.model.tags.ExecType;
import com.simulator.model.tags.OrdType;
import com.simulator.model.tags.Side;
import com.simulator.view.OrderView;

import javafx.application.Application;
import javafx.stage.Stage;
import quickfix.ConfigError;

/**
 * Starts the application and delegates to the actual JavaFX application. An
 * instance of this class will be created by JavaFX after having called
 * launch(args). FIXBro will create the actual application (view) and the
 * controller
 * 
 * @author sunquan
 *
 */
public class FIXSim extends Application {

	private Application view;
	private QFJAcceptor acceptor;
	private static String CfgFile;

	private static String getCfgFile(String[] args) {
		String cfg = "fixsimulator.cfg";
		if (args != null && args.length > 0 && args[0].endsWith(".cfg")) {
			cfg = args[0];
		}
		return cfg;
	}

	public FIXSim() throws ConfigError, FileNotFoundException {
		System.out.println("config: " + CfgFile);
		InputStream cfg = new FileInputStream(CfgFile);
		// final String cssUrl = getClass().getResource("application.css").toExternalForm();
		final String cssUrl = null;
		// sending strategy: directly
		acceptor = new QFJAcceptor(cfg, new QFJApplicationIn());
		
		List<ExecutionReportObserver> erObservers = Arrays.asList(new QFJApplicationOut());
		ExecutionReportProcessor erProcessor = new ExecutionReportProcessorImpl(erObservers);
		OrderController controller = new OrderControllerImpl(erProcessor);

		view = new OrderView(controller, cssUrl);
	}

	public static void main(String[] args) {
		// launch JavaFX
		CfgFile = getCfgFile(args);
		launch(args);
	}
	 private void AddTestData(){
    	// TEST DATA
		// add some fake orders into the order book
		 Order order1 = new OrderBean();
		 order1.setMsgType("D");
		 order1.setAvgPx(0D);
		 order1.setClOrdID("1234");
		 order1.setCumQty(0D);
		 order1.setLeavesQty(1000D);
		 order1.setOrderID("E1234");
		 order1.setOrdType(OrdType.LIMIT);
		 order1.setOrdStatus(ExecType.CANCELED);
		 order1.setOrigClOrdID("orig1234");
		 order1.setPrice(1.35D);
		 order1.setOrderQty(1000D);
		 order1.setSide(Side.BUY);
		 order1.setSymbol("EUR/USD");
		 order1.setSenderCompID("BANZAI");
		 order1.setTargetCompID("FIXB");
		 OrderBook.getInstance().addOrder(order1);
		 
		 Order order2 = new OrderBean();
		 order2.setMsgType("D");
		 order2.setAvgPx(0D);
		 order2.setClOrdID("1235");
		 order2.setCumQty(0D);
		 order2.setLeavesQty(2000D);
		 order2.setOrderID("myOrderID2");
		 order2.setOrdType(OrdType.MARKET);
		 order2.setOrdStatus(ExecType.NEW);
		 order2.setOrigClOrdID("orig1235");
		 order2.setOrderQty(2000D);
		 order2.setSide(Side.SELL);
		 order2.setPrice(1.35D);
		 order2.setSymbol("EUR/USD");
		 order2.setSenderCompID("BANZAI2");
		 order2.setTargetCompID("FIXB");
		 OrderBook.getInstance().addOrder(order2);
    }
	@Override
	public void start(Stage primaryStage) throws Exception {
		acceptor.start(); // start accepting connections
		view.start(primaryStage);
		//AddTestData();
	}

	@Override
	public void stop() throws Exception {
		view.stop();
		acceptor.stop();
	}
}
