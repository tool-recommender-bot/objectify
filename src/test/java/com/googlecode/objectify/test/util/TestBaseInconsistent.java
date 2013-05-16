/*
 */

package com.googlecode.objectify.test.util;

import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.google.appengine.tools.development.testing.LocalDatastoreServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalMemcacheServiceTestConfig;
import com.google.appengine.tools.development.testing.LocalServiceTestHelper;
import com.google.appengine.tools.development.testing.LocalTaskQueueTestConfig;
import com.googlecode.objectify.ObjectifyFilter;

/**
 * For rare tests which require delayed eventual consistency.
 *
 * @author Jeff Schnitzer <jeff@infohazard.org>
 */
public class TestBaseInconsistent
{
	/** */
	private final LocalServiceTestHelper helper =
			new LocalServiceTestHelper(
					new LocalDatastoreServiceTestConfig().setDefaultHighRepJobPolicyUnappliedJobPercentage(100f),
					new LocalMemcacheServiceTestConfig(),
					new LocalTaskQueueTestConfig());
	/** */
	@BeforeMethod
	public void setUp() {
		this.helper.setUp();

		// Initialize a new factory each time.
		TestObjectifyService.initialize();
	}

	/** */
	@AfterMethod
	public void tearDown() {
		// This is normally done in ObjectifyFilter but that doesn't exist for tests
		ObjectifyFilter.complete();

		this.helper.tearDown();
	}
}
