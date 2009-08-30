package org.jlinalg;

/**
 * class to return FieldElement.one() or FieldElement.zero(), depending on
 * result of FieldElement not-equal-to comparison
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the objects to be compared.
 */
@SuppressWarnings("deprecation")
class NotEqualToComparator<RE extends IRingElement>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final FEComparator<IRingElement> INSTANCE = new NotEqualToComparator<IRingElement>();

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
	public NotEqualToComparator()
	{
	}

	@Override
	public boolean compare(RE a, RE b)
	{
		return !a.equals(b);
	}
}
