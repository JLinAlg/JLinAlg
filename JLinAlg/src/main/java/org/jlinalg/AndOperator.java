package org.jlinalg;

/**
 * the logical AND operator for IRingElements
 * 
 * @author ???, Georg Thimm
 * @param <RE>
 */
@SuppressWarnings("deprecation")
class AndOperator<RE extends IRingElement>
		implements DyadicOperator<RE>
{

	/**
	 * The singleton for this class.
	 */
	private static final DyadicOperator<IRingElement> INSTANCE = new AndOperator<IRingElement>();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static DyadicOperator<IRingElement> getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The constructor should only be called to create the singleton instance
	 * (see {@link #getInstance()}.
	 */
	@Deprecated
	public AndOperator()
	{
	}

	@SuppressWarnings("unchecked")
	public <ARG extends IRingElement> RE apply(ARG x, ARG y)
	{
		return (RE) ((x.isZero() || y.isZero()) ? x.getFactory().zero() : x
				.getFactory().one());
	}
}
