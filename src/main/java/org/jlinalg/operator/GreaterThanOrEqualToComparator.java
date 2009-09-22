package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * class to return RingElement.one() or RingElement.zero(), depending on result
 * of RingElement greater than or equal to comparison
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg THimm
 * @param <RE>
 *            the type of the objects to be compared.
 */
@SuppressWarnings("deprecation")
public class GreaterThanOrEqualToComparator<RE extends IRingElement<RE>>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	@SuppressWarnings("unchecked")
	private static final FEComparator<? extends IRingElement<?>> INSTANCE = new GreaterThanOrEqualToComparator();

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
	public GreaterThanOrEqualToComparator()
	{
	}

	@Override
	public boolean compare(RE a, RE b)
	{
		return a.ge(b);
	}
}
