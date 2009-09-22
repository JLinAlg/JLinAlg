package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * class to return RingElement.one() or RingElement.zero(), depending on result
 * of RingElement greater-than comparison
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the objects to be compared.
 */
@SuppressWarnings("deprecation")
public class GreaterThanComparator<RE extends IRingElement<RE>>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	@SuppressWarnings("unchecked")
	private static final FEComparator<? extends IRingElement<?>> INSTANCE = new GreaterThanComparator();

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
	public GreaterThanComparator()
	{
	}

	@Override
	public boolean compare(RE a, RE b)
	{
		return a.gt(b);
	}
}
