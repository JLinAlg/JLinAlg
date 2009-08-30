package org.jlinalg;

/**
 * logical OR of two FieldElements
 * 
 * @author Simon Levy, Andreas Keilhauer, Georg Thimm
 */
@SuppressWarnings("deprecation")
class OrOperator<RE extends IRingElement>
		implements DyadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final DyadicOperator<IRingElement> INSTANCE = new OrOperator<IRingElement>();

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
	public OrOperator()
	{
	}

	@SuppressWarnings("unchecked")
	public <ARG extends IRingElement> RE apply(ARG x, ARG y)
	{
		return (RE) ((x.isZero() && y.isZero()) ? x.getFactory().zero() : x
				.getFactory().one());
	}
}
