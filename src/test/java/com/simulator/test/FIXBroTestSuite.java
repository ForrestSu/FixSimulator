package com.simulator.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.simulator.test.controller.er.producer.ExecuteOrderTest;
import com.simulator.test.controller.er.producer.PendingNewOrderTest;

@RunWith(Suite.class)
@SuiteClasses({ PendingNewOrderTest.class, ExecuteOrderTest.class })
public class FIXBroTestSuite {
	// holder class
}
