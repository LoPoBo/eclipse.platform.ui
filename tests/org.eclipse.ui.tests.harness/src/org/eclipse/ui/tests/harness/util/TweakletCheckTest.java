/*******************************************************************************
 * Copyright (c) 2007 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.ui.tests.harness.util;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestResult;
import junit.framework.TestSuite;

import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.Platform;

/**
 * Tests to see if we're running with Tweaklets. If so, fail early so we don't
 * waste an entire morning trying to figure out why all of the part tests are
 * failing.
 * 
 * @since 3.4
 * 
 */
public class TweakletCheckTest extends TestCase {

	String[] allowedTweaklets = {"org.eclipse.ui.cocoa.titlePathUpdaterTweaklet"};
	
	/**
	 * 
	 */
	public TweakletCheckTest() {
		setName("test for tweaklets");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see junit.framework.TestCase#run(junit.framework.TestResult)
	 */
	public void run(TestResult result) {

		result.startTest(this);
		try {
			IExtensionPoint point = Platform.getExtensionRegistry()
			.getExtensionPoint("org.eclipse.ui.internalTweaklets");
			IExtension[] extensions = point.getExtensions();
			boolean abort = false;
			outer:
			for (int i = 0; i < extensions.length; i++) {
				IConfigurationElement[] elements = extensions[i].getConfigurationElements();
				for (int j = 0; j < elements.length; j++) {
					String id = elements[j].getAttribute("id");
					if(!isAllowedTweaklet(id)) {
						abort = true;
						break outer;
					}
				}
			}
			if (abort) {
				Error error = new Error(
						"Tweaklets present in test setup - all test results are now suspect.  Please restart the tests with the tweaklet extensions removed.");
				result.addError(this, error);
				result.stop();
			}
		} finally {
			result.endTest(this);
		}

	}

	private boolean isAllowedTweaklet(String id) {
		boolean allowed = false;
		for (int j = 0; j < allowedTweaklets.length; j++) {
			if(allowedTweaklets[j].equals(id)) {
				allowed = true;
				break;
			}
		}
		return allowed;
	}

	public static Test suite() {
		TestSuite test = new TestSuite();
		test.addTest(new TweakletCheckTest());
		test.addTest(new TweakletCheckTest());
		// should not run if there are tweaklets present - the first run should
		// aggressively take down the framework.
		return test;
	}
}
