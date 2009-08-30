/*
 * Created on 15.05.2005
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package org.jlinalg;

/**
 * @author Andreas
 *         To change the template for this generated type comment go to
 *         Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class DivisionByZeroException
		extends RuntimeException
{

	/**
 	 * 
 	 */
	private static final long serialVersionUID = 1L;

	/**
	 * @param theMessage
	 *            a descriptin of where this occured.
	 */
	public DivisionByZeroException(String theMessage)
	{
		super(theMessage);
	}

}
