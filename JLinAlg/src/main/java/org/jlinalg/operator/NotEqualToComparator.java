package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * class to return FieldElement.one() or FieldElement.zero(), depending on
 * result of FieldElement not-equal-to comparison
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the objects to be compared.
 */
@SuppressWarnings("deprecation")
public class NotEqualToComparator<RE extends IRingElement<RE>>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	@SuppressWarnings("unchecked")
	private static final FEComparator<? extends IRingElement<?>> INSTANCE = new NotEqualToComparator();

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
	public NotEqualToComparator()
	{
	}

	@Override
	public boolean compare(RE a, RE b)
	{
		return !a.equals(b);
	}
}
