package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * the logical AND operator for IRingElements
 * 
 * @author ???, Georg Thimm
 * @param <RE>
 */
@SuppressWarnings({
	"unchecked"
})
public class AndOperator<RE extends IRingElement<RE>>
		implements DyadicOperator<RE>
{

	/**
	 * The singleton for this class.
	 */
	private static final DyadicOperator<? extends IRingElement<?>> INSTANCE = new AndOperator();

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
	public AndOperator()
	{
	}

	@Override
	public RE apply(RE x, RE y)
	{
		return ((x.isZero() || y.isZero()) ? x.getFactory().zero() : x
				.getFactory().one());
	}
}
