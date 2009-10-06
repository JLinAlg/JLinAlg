package org.jlinalg.operator;

import java.util.Arrays;
import java.util.List;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.bigdecimalwrapper.BigDecimalWrapperFactory;
import org.jlinalg.complex.Complex;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.f2.F2;
import org.jlinalg.fastrational.FastRational;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.jlinalg.polynomial.PolynomialFactory;
import org.jlinalg.rational.Rational;
import org.jlinalg.testutil.TestBaseFixture;
import org.junit.runners.Parameterized.Parameters;

/**
 * The base for all operator tests.
 * 
 * @author Georg Thimm
 */
public class OperatorTestBase<RE extends IRingElement<RE>>
		extends TestBaseFixture<RE>
{

	final static String[] s_1to5 = new String[] {
			"1", "2", "3", "4", "5"
	};
	final static String[] s_m2to2 = new String[] {
			"-2", "-1", "0", "1", "1"
	};

	final IRingElementFactory<RE> factory;

	/**
	 * Constructor: used by the test suite runner.
	 */
	public OperatorTestBase(IRingElementFactory<RE> factory)
	{
		this.factory = factory;
	}

	/**
	 * @return the parameters for the fixtures
	 */
	@Parameters
	public static List<Object[]> data()
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
				}
		};
		return Arrays.asList(data_);
	}

	@Override
	public IRingElementFactory<RE> getFactory()
	{
		return factory;
	}
}
