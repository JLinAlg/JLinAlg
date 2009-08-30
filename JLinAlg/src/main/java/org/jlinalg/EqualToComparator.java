package org.jlinalg;

/**
 * Class to return IRingElement.one() or IRingElement.zero(), depending on
 * result of IRingElement equal-to comparison.
 * 
 * @author ???, Georg Thimm
 */
@SuppressWarnings("deprecation")
class EqualToComparator<RE extends IRingElement>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final FEComparator<IRingElement> INSTANCE = new EqualToComparator<IRingElement>();

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
	public EqualToComparator()
	{
	}

	@Override
	public boolean compare(RE a, RE b)
	{
		return a.equals(b);
	}
}
