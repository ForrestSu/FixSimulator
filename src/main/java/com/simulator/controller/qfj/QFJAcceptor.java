package com.simulator.controller.qfj;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import quickfix.Application;
import quickfix.ConfigError;
import quickfix.DefaultMessageFactory;
import quickfix.FileStoreFactory;
import quickfix.SessionSettings;
import quickfix.SocketAcceptor;

import java.io.InputStream;

/**
 * This class manages the life cycle of a QFJ acceptor. It requires a .cfg file
 * specifying the QFJ properties, like senderCompID, targetCompID, etc
 * 
 * @author sunquan
 *
 */
public class QFJAcceptor {

	private static final Logger LOG = LoggerFactory.getLogger(QFJAcceptor.class);

	private final SocketAcceptor acceptor;

	public QFJAcceptor(InputStream cfg, Application application) throws ConfigError {
		final SessionSettings settings = new SessionSettings(cfg);
		acceptor = new SocketAcceptor(application, new FileStoreFactory(settings), settings,
				new DefaultMessageFactory());
	}

	public void start() throws ConfigError {
		LOG.info("Starting");
		acceptor.start();
		LOG.info("Started");
	}

	public void stop() {
		LOG.info("Stopping");
		acceptor.stop();
		LOG.info("Stopped");
	}

}
