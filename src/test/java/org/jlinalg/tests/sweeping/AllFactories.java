/*
 * This file is part of JLinAlg (<http://jlinalg.sourceforge.net/>).
 * 
 * JLinAlg is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation, either version 3 of
 * the License, or (at your option) any later version.
 * 
 * JLinAlg is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with JLinALg. If not, see <http://www.gnu.org/licenses/>.
 */
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
import org.jlinalg.rationalFunction.RationalFunctionFactory;

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
				}, {
					RationalFunctionFactory.getFactory(Rational.FACTORY)
				}, {
					RationalFunctionFactory.getFactory(DoubleWrapper.FACTORY)
				}
		};
		return Arrays.asList(data_);
	}
}
