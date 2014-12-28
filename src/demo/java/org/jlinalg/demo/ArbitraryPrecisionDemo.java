package org.jlinalg.demo;

import org.jlinalg.DivisionByZeroException;
import org.jlinalg.rational.Rational;

/**
 * Example that shows how easily simple floating point arithmetic can go wrong.
 * Only with arbitrary precision your code will realize that you are dividing by
 * zero.
 * 
 * @author Andreas Keilhauer
 */
public class ArbitraryPrecisionDemo
{
	private final static String INTERMEDIATE_CALCULATION_STR = "(3 / 5000) * 5000";
	private final static String FIRST_CALCULATION_STR = "1 / ((3 / 5000) * 5000 + 3)";

	/**
	 * start the demonstration
	 * 
	 * @param argv
	 *            is ignored
	 */
	public static void main(String[] argv)
	{
		demoFloat();
		demoDouble();
		demoArbitraryPrecision();
	}

	private static void demoFloat()
	{
		System.out.println("============================================");
		System.out.println("Demo Float:");
		float intermediateResult = (3.0f / 5000.0f) * 5000.0f;
		System.out.println(INTERMEDIATE_CALCULATION_STR + " = "
				+ intermediateResult + " (only slightly wrong)");
		float wrongResult = 1.0f / (intermediateResult - 3.0f);
		System.out.println(FIRST_CALCULATION_STR + " = " + wrongResult
				+ " (wrong) ");

		System.out.println("116 / 406 = " + (116.0f / 406.0f)
				+ " (not exact and bulky)");
	}

	private static void demoDouble()
	{
		System.out.println("============================================");
		System.out.println("Demo Double: ");
		double intermediateResult = (3.0 / 5000.0) * 5000.0;
		System.out.println(INTERMEDIATE_CALCULATION_STR + " = "
				+ intermediateResult + " (only slightly wrong)");
		double wrongResult = 1.0 / (intermediateResult - 3.0);
		System.out.println(FIRST_CALCULATION_STR + " = " + wrongResult
				+ " (wrong)");

		System.out.println("116 / 406 = " + (116.0 / 406.0)
				+ " (not exact and bulky)");
	}

	private static void demoArbitraryPrecision()
	{
		System.out.println("============================================");
		System.out.println("Demo Rational with arbitrary precision:");
		Rational one = Rational.FACTORY.get(1.0);
		Rational three = Rational.FACTORY.get(3.0);
		Rational fiveThousand = Rational.FACTORY.get(5000.0);
		Rational intermediateResult = (three.divide(fiveThousand))
				.multiply(fiveThousand);
		System.out.println(INTERMEDIATE_CALCULATION_STR + " = "
				+ intermediateResult + " (correct)");
		try {
			// This will throw a DivisionByZeroException
			one.divide(intermediateResult.subtract(three));
		} catch (DivisionByZeroException d) {
			System.out.println(FIRST_CALCULATION_STR
					+ " => Division by zero detected. (correct)");
		}

		Rational oneHundredSixteen = Rational.FACTORY.get(116);
		Rational fourHundredSix = Rational.FACTORY.get(406);
		System.out.println("116 / 406 = "
				+ oneHundredSixteen.divide(fourHundredSix)
				+ " (exact and concise solution)");
	}
}
