package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * class to return IRingElement.one() or IRingElement.zero(), depending on
 * result of IRingElement less-than comparison
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 * @param <RE>
 *            the type of the elements to be compared.
 */
@SuppressWarnings( {
		"deprecation", "unchecked"
})
public class LessThanComparator<RE extends IRingElement<RE>>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final FEComparator<? extends IRingElement<?>> INSTANCE = new LessThanComparator();

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
	public LessThanComparator()
	{
	}

	@Override
	public boolean compare(RE a, RE b)
	{
		return a.lt(b);
	}
}
