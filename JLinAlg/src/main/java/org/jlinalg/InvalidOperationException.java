package org.jlinalg;

import java.io.Serializable;

/**
 * This class provides a run-time exception that gets thrown whenever an invalid
 * operation is attempted.
 */

public class InvalidOperationException
		extends RuntimeException
		implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param theMessage
	 *            the reason why this exception was risen.
	 */
	public InvalidOperationException(String theMessage)
	{
		super(theMessage);
	}
}
