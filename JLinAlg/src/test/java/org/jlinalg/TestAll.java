package org.jlinalg;

import org.jlinalg.demo.DemoTest;
import org.jlinalg.latex.LatexStreamTest;
import org.jlinalg.optimise.RandomGradientTest;
import org.jlinalg.polynomial.PolynomialFactoryTest;
import org.jlinalg.polynomial.PolynomialOperationsTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * perform all tests.
 * 
 * @author Georg Thimm
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(
{
		AffineLinearSubspaceTest.class, BigDecimalWrapperTest.class,
		BigDecimalWrapperFactoryTest.class, ComplexTest.class, DemoTest.class,
		DeterminantTest.class, DoubleWrapperFactoryTest.class,
		DoubleWrapperTest.class, F2Test.class, FactoriesTest.class,
		PolynomialFactoryTest.class, LatexStreamTest.class,
		LinSysSolverTest.class, MatrixMultiplicationTest.class,
		MatrixInversionTest.class, MatrixMeanTest.class, PolynomialTest.class,
		PolynomialFactoryTest.class, PolynomialOperationsTest.class,
		RandomGradientTest.class, RationalMatrixTest.class, RationalTest.class,
		RationalVectorTest.class, SquareOperatorTest.class,
		TypeConverterTest.class
})
public class TestAll
{

}
