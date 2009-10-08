package org.jlinalg.field_p;

/**
 * This class implements the operations in Fp where p &lt;
 * PRIME_SEPARATION_BOUNDARY. All computations can be done using long variables.
 * IMPORTANT: This class and its subclasses make use of the concept of
 * immutability of objects. If you make changes or subclass these classes,
 * ensure immutability or overwrite the methods affected.
 * 
 * @author Lochbihler Andreas, Georg Thimm
 */
class FieldPLong
		extends FieldP<FieldPLong>
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** The least non-negative representant of the equivalence class */
	protected long value;

	/**
	 * a cache for the inverse.
	 */
	private FieldPLong inverse;

	/**
	 * Returns a new element of the field Fp. The element is the equivalence
	 * class containing value. This method should only be called by the factory
	 * {@link FieldPAbstractFactory}
	 * 
	 * @param value
	 *            The new element is the equivalence class of value. This value
	 *            is assumed to be in the range of 0..(p-1).
	 * @param factory
	 *            the factory producing elements in Fp (typically the caller of
	 *            this constructor).
	 */
	FieldPLong(long value, FieldPAbstractFactory<FieldPLong> factory)
	{
		super(factory);
		this.value = value;
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode()
	{
		return (int) (value ^ (value << (Long.SIZE / 2)));
	}

	/**
	 * Computes the additive inverse
	 * 
	 * @return The additive inverse
	 */
	@Override
	public FieldPLong negate()
	{
		return factory.get(-value);
	}

	/**
	 * Calculates the sum of this element and another one from the same field.
	 * 
	 * @param val
	 *            The other element to add
	 * @return The sum of both elements
	 * @throws IllegalArgumentException
	 *             Thrown if you are trying to add elements from different
	 *             fields Fp.
	 */
	@Override
	public FieldPLong add(FieldPLong val) throws IllegalArgumentException
	{
		if (val.factory == this.factory) {
			return factory.get(this.value + val.value);
		}

		throw new IllegalArgumentException(val
				+ " is from a different Fp than " + this
				+ "! You cannot add them.");
	}

	/**
	 * Calculates the product of this element and another one from the same
	 * field.
	 * 
	 * @param val
	 *            The other element to multiply
	 * @return The product of both elements
	 * @throws IllegalArgumentException
	 *             Thrown if you are trying to multiply elements from different
	 *             fields Fp.
	 */
	@Override
	public FieldPLong multiply(FieldPLong val) throws IllegalArgumentException
	{

		if (val.factory == this.factory) {
			return factory.get(this.value * val.value);
		}
		throw new IllegalArgumentException(val
				+ " is from a different Fp than " + this
				+ "! You cannot multiply them.");
	}

	/**
	 * Returns a string representation of the element. The string representation
	 * consists of the value of the smallest non- negative representant of the
	 * equivalence class in 10-adic, the letter m and the number of elements in
	 * the field in 10-adic.
	 * 
	 * @return The string representation of the element.
	 */
	@Override
	public String toString()
	{
		return value + "m" + ((FieldPLongFactory) factory).p;
	}

	/**
	 * Compares this element with another element of the same field Fp. Note:
	 * This order does not respect addition or multiplication!
	 * 
	 * @param par
	 *            The element to compare to
	 * @return -1, if this is less, 0, if they are equal, 1, if this is bigger
	 */
	@Override
	public int compareTo(FieldPLong par)
	{
		if (this.factory == par.factory) {
			long diff = this.value - par.value;
			return (diff > 0 ? 1 : (diff < 0 ? -1 : 0));
		}
		throw new IllegalArgumentException(par
				+ " is from a differend field than " + this
				+ "! You cannot compare them");
	}

	@Override
	public FieldPLong invert()
	{
		if (inverse == null) {
			inverse = ((FieldPLongFactory) factory).computeInverse(value);
			inverse.inverse = this;
		}
		return inverse;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jlinalg.FieldP#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o)
	{
		if (o == null) return false;
		FieldPLong f = (FieldPLong) o;
		if (f.factory != factory) {
			throw new IllegalArgumentException(
					"Cannot compare elements in different fields");
		}
		return f.value == value;
	}
}
