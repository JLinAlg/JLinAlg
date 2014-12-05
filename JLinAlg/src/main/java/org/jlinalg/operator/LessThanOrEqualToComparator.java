package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * class to return IRingElement.one() or IRingElement.zero(), depending on
 * result of IRingElement less than or equal to comparison.
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the objects to be compared.
 */
public class LessThanOrEqualToComparator<RE extends IRingElement<RE>>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	@SuppressWarnings("unchecked")
	private static final FEComparator<? extends IRingElement<?>> INSTANCE = new LessThanOrEqualToComparator();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static FEComparator<? extends IRingElement<?>> getInstance()
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
	public LessThanOrEqualToComparator()
	{
	}

	@Override
	public boolean compare(RE a, RE b)
	{
		return a.le(b);
	}
}
