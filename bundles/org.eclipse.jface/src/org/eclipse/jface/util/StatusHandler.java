/*******************************************************************************
 * Copyright (c) 2008, 2015 IBM Corporation and others.
 *
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 ******************************************************************************/

package org.eclipse.jface.util;

import org.eclipse.core.runtime.IStatus;

/**
 * A mechanism to handle statuses throughout JFace.
 * <p>
 * Clients may provide their own implementation to change how statuses are
 * handled from within JFace.
 * </p>
 *
 * @see org.eclipse.jface.util.Policy#getStatusHandler()
 * @see org.eclipse.jface.util.Policy#setStatusHandler(StatusHandler)
 *
 * @since 3.4
 */
abstract public class StatusHandler {

	/**
	 * Show the given status.
	 *
	 * @param status
	 *            status to handle
	 * @param title
	 *            title for the status
	 */
	abstract public void show(IStatus status, String title);
}
