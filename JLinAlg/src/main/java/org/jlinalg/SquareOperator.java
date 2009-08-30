/**
 * 
 */
package org.jlinalg;

/**
 * An operator calculating the squares of elements in a {@link Vector} or
 * {@link Matrix}.
 * 
 * @author Georg Thimm 2009
 * @param <RE>
 *            see {@link MonadicOperator}
 */
public class SquareOperator<RE extends IRingElement>
		implements MonadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final MonadicOperator<? extends IRingElement> INSTANCE = new SquareOperator<IRingElement>();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static MonadicOperator<? extends IRingElement> getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The constructor should only be called to create the singleton instance
	 * (see {@link #getInstance()}.
	 */
	private SquareOperator()
	{
	}

	@SuppressWarnings("unchecked")
	public <ARG extends IRingElement> RE apply(ARG x)
	{
		return (RE) x.multiply(x);
	}

}
