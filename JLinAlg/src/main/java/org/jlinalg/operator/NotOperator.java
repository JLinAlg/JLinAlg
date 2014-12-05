package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * logical negation of FieldElement
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the objects on which is operated
 */
public class NotOperator<RE extends IRingElement<RE>>
		implements MonadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	@SuppressWarnings("unchecked")
	private static final MonadicOperator<? extends IRingElement<?>> INSTANCE = new NotOperator();

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
	public NotOperator()
	{
	}

	@Override
	public RE apply(RE x)
	{
		return (x.isZero() ? x.getFactory().one() : x.getFactory().zero());
	}
}
