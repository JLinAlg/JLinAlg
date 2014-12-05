/**
 * 
 */
package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * An operator calculating the squares of elements in a
 * {@link org.jlinalg.Vector} or {@link org.jlinalg.Matrix}.
 * 
 * @author Georg Thimm 2009
 * @param <RE>
 *            see {@link MonadicOperator}
 */
@SuppressWarnings({
	"unchecked"
})
public class SquareOperator<RE extends IRingElement<RE>>
		implements MonadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final MonadicOperator<? extends IRingElement<?>> INSTANCE = new SquareOperator();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static MonadicOperator<? extends IRingElement<?>> getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The constructor should only be called to create the singleton instance
	 * (see {@link #getInstance()}.
	 * 
	 * @deprecated use {@link #getInstance()}
	 */
	@Deprecated
	private SquareOperator()
	{
	}

	/**
	 * @param x
	 *            the element to be squared.
	 * @return the sqare of <code>x</code>.
	 */
	@Override
	public RE apply(RE x)
	{
		return x.multiply(x);
	}

}
