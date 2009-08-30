package org.jlinalg.optimise;

import org.jlinalg.IRingElement;

/**
 * This interface is to be used in combination with {@link Optimiser}. It is the
 * contract to be fulfilled by a class that is to be optimised. This is the
 * interface the optimiser will use. The target has to provide access to a set
 * of minimum and maximum values for set of parameters. The parameters can be
 * mixed, as long as <code>{@link #getParameters()}[i]</code>,
 * <code>{@link #maxParameterValues}[i]</code>, and
 * <code>{@link #minParameterValues}[i]</code> are of the same type.
 * 
 * @author Georg Thimm 2009
 * @param <RESIDUAL>
 *            The type of the residual.
 */
public interface Target<RESIDUAL extends IRingElement>
{
	/**
	 * Give access to the residual for the parameters as they are stored in the
	 * target object.
	 * 
	 * @return the residual for the state of the target object or
	 *         <code>null</code> if the parameters are not a valid state for the
	 *         target.
	 */
	public RESIDUAL getResidual();

	/**
	 * @return the parameters as they are stored in the target.
	 */
	public IRingElement[] getParameters();

	/**
	 * Change the parameters
	 * 
	 * @param values
	 *            the new value
	 */
	public void setParameters(IRingElement[] values);

	/**
	 * Change one parameter
	 * 
	 * @param index
	 *            the position of the new parameter.
	 * @param value
	 *            the new value of a parameter
	 * @return true if value is in the range specified by
	 *         <code>{@link #maxParameterValues()}[index]</code> and
	 *         <code>{@link #minParameterValues()}[index]</code>
	 */
	public boolean setParameter(int index, IRingElement value);

	/**
	 * @return the minimum permissible values for the parameters. This may be
	 *         <code>null</code> (no lower limit is then used), or individual
	 *         elements may be <code>null</code> (and then for this element no
	 *         lower limit exists).
	 */
	public IRingElement[] minParameterValues();

	/**
	 * @return the maximum permissible values for the parameters. This may be
	 *         <code>null</code> (no upper limit is then used), or individual
	 *         elements may be <code>null</code> (and then for this element no
	 *         upper limit exists).
	 */
	public IRingElement[] maxParameterValues();

	/**
	 * @param index
	 *            the number of the parameter
	 * @return a parameter
	 */
	public IRingElement getParameter(int index);
}
