package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * product of two RingElements
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 */
@SuppressWarnings({
	"unchecked"
})
public class MultiplyOperator<RE extends IRingElement<RE>>
		implements DyadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final DyadicOperator<? extends IRingElement<?>> INSTANCE = new MultiplyOperator();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static DyadicOperator<? extends IRingElement<?>> getInstance()
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
	public MultiplyOperator()
	{
	}

	@Override
	public RE apply(RE x, RE y)
	{
		return x.multiply(y);
	}

}
