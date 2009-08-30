package org.jlinalg;

/**
 * class to return RingElement.one() or RingElement.zero(), depending on result
 * of RingElement greater-than comparison
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the objects to be compared.
 */
@SuppressWarnings("deprecation")
class GreaterThanComparator<RE extends IRingElement>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final FEComparator<IRingElement> INSTANCE = new GreaterThanComparator<IRingElement>();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static FEComparator<IRingElement> getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The constructor should only be called to create the singleton instance
	 * (see {@link #getInstance()}.
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
