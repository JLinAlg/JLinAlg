package org.jlinalg.field_p;

import java.util.Comparator;

class FieldPFactoryComparator<T>
		implements Comparator<Comparable<? super T>>
{
	/**
	 * If o1 and o2 are of the same class, return the value for a normal
	 * comparison. Otherwise, assume that one object is an instance of {@code
	 * java.lang.Long}, the other of {@code java.util.BigInteger} and that
	 * instances of the later have always the larger values
	 */
	@SuppressWarnings("unchecked")
	@Override
	public int compare(Comparable<? super T> o1, Comparable<? super T> o2)
	{
		if (o1.getClass().equals(o2.getClass())) return o1.compareTo((T) o2);
		if (o1 instanceof Long) return -1;
		return 1;
	}

}
