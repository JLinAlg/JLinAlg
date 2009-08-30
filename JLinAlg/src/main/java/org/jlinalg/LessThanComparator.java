package org.jlinalg;

/**
 * class to return IRingElement.one() or IRingElement.zero(), depending on
 * result of IRingElement less-than comparison
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the elements to be compared.
 */
@SuppressWarnings("deprecation")
class LessThanComparator<RE extends IRingElement>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final FEComparator<IRingElement> INSTANCE = new LessThanComparator<IRingElement>();

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
	public LessThanComparator()
	{
	}

	@Override
	public boolean compare(RE a, RE b)
	{
		return a.lt(b);
	}
}
