package org.jlinalg.operator;

import org.jlinalg.IRingElement;

/**
 * Class to return IRingElement.one() or IRingElement.zero(), depending on
 * result of IRingElement equal-to comparison.
 * 
 * @author ???, Georg Thimm
 */
@SuppressWarnings( {
		"deprecation", "unchecked"
})
public class EqualToComparator<RE extends IRingElement<RE>>
		extends FEComparator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final FEComparator<? extends IRingElement<?>> INSTANCE = new EqualToComparator();

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
	public EqualToComparator()
	{
	}

	@Override
	public boolean compare(RE a, RE b)
	{
		return a.equals(b);
	}
}
