package com.pga.suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.pga.tests.JUnityTests;
import com.pga.tests.ParametrizerTest;
import com.pga.tests.RuleTest;

@RunWith(Suite.class)
@SuiteClasses({
	JUnityTests.class,
	ParametrizerTest.class,
	RuleTest.class
})
public class SuiteTestes {
	
}
