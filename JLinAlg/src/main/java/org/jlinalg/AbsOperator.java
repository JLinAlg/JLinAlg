package org.jlinalg;

/**
 * an operator for the absolute value computation for IRingElements
 * 
 * @author ???, Georg Thimm
 * @param <RE>
 *            a type extending on {@link IRingElement}.
 */
@SuppressWarnings("deprecation")
class AbsOperator<RE extends IRingElement>
		implements MonadicOperator<RE>
{
	/**
	 * The singleton for this class.
	 */
	private static final MonadicOperator<IRingElement> INSTANCE = new AbsOperator<IRingElement>();

	/**
	 * @return the singleton instance for this operator.
	 */
	public static MonadicOperator<IRingElement> getInstance()
	{
		return INSTANCE;
	}

	/**
	 * The constructor should only be called to create the singleton instance
	 * (see {@link #getInstance()}.
	 */
	@Deprecated
	public AbsOperator()
	{
	}

	@SuppressWarnings("unchecked")
	public <ARG extends IRingElement> RE apply(ARG x)
	{
		return (RE) (x.lt(x.getFactory().zero()) ? x.negate() : x);
	}

}
