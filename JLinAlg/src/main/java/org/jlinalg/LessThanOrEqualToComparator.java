package org.jlinalg;

/**
 * class to return IRingElement.one() or IRingElement.zero(), depending on
 * result of IRingElement less than or equal to comparison.
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the objects to be compared.
 */
@SuppressWarnings("deprecation")
class LessThanOrEqualToComparator<RE extends IRingElement>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final FEComparator<IRingElement> INSTANCE = new LessThanOrEqualToComparator<IRingElement>();

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
	public LessThanOrEqualToComparator()
	{
	}

	@Override
	public boolean compare(RE a, RE b)
	{
		return a.le(b);
	}
}
