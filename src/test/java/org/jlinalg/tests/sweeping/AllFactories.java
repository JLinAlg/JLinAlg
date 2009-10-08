package org.jlinalg.tests.sweeping;

import java.util.Arrays;
import java.util.List;

import org.jlinalg.bigdecimalwrapper.BigDecimalWrapperFactory;
import org.jlinalg.complex.Complex;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.f2.F2;
import org.jlinalg.fastrational.FastRational;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.jlinalg.polynomial.PolynomialFactory;
import org.jlinalg.rational.Rational;

public class AllFactories
{
	/**
	 * @return a list of factories to be considered in unit tests sweeping over
	 *         all factories.
	 */
	public static List<Object[]> factories()
	{
		Object[][] data_ = new Object[][] {
				{
					Rational.FACTORY
				}, {
					FastRational.FACTORY
				}, {
					Complex.FACTORY
				}, {
					FieldPFactoryMap.getFactory(new Long(43L))
				}, {
					F2.FACTORY
				}, {
					DoubleWrapper.FACTORY
				}, {
					new BigDecimalWrapperFactory(30)
				}, {
					PolynomialFactory.getFactory(Rational.FACTORY)
				}, {
					PolynomialFactory.getFactory(DoubleWrapper.FACTORY)
				}
		};
		return Arrays.asList(data_);
	}
}
