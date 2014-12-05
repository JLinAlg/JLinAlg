package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * logical OR of two FieldElements
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 */
@SuppressWarnings({
		"deprecation", "unchecked"
})
public class OrOperator<RE extends IRingElement<RE>>
		implements DyadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final DyadicOperator<? extends IRingElement<?>> INSTANCE = new OrOperator();

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
	public OrOperator()
	{
	}

	@Override
	public RE apply(RE x, RE y)
	{
		return ((x.isZero() && y.isZero()) ? x.getFactory().zero() : x
				.getFactory().one());
	}
}
