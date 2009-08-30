/**
 * 
 */
package org.jlinalg.optimise;

/**
 * The error to risen if the optimiser encounters problems.
 * 
 * @author Georg Thimm
 */
@SuppressWarnings("serial")
public class OptimiserError
		extends Error
{

	/**
	 * @param string
	 *            the error message
	 */
	public OptimiserError(String string)
	{
		super(string);
	}

}
