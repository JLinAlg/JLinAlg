/**
 * 
 */
package org.jlinalg.fieldp;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.Collection;

import org.jlinalg.IRingElement;
import org.jlinalg.IRingElementFactory;
import org.jlinalg.field_p.FieldPFactoryMap;
import org.jlinalg.testutil.RingElementTestBase;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Georg Thimm
 */
@RunWith(value = Parameterized.class)
public class FieldPTest
		extends RingElementTestBase
{
	/**
	 * Two values from which the fixture is created: a small to test
	 * {@link FieldPBig} and {@link FieldPLong} .
	 **/
	@Parameters
	public static Collection<Object[]> data1()
	{
		Object[][] data = {
				{
					"113"
				}, {
					"2932031007403"
				}
		};

		return Arrays.asList(data);
	}

	private final IRingElementFactory<?> factory;

	public FieldPTest(String o)
	{
		factory = FieldPFactoryMap.getFactory(o);
	}

	public void testFieldP_113_Test()
	{
		assertNotNull(factory);
	}

	@Override
	public IRingElementFactory<?> getFactory()
	{
		return factory;
	}

	/**
	 * test the annotation of the type.
	 */
	@Test
	public void testAnnotation()
	{
		assertFalse(getFactory().getClass().getName(),
				dataTypeHasNegativeValues());
		assertTrue(getFactory().getClass().getName(), dataTypeIsDiscreet());
		assertTrue(getFactory().getClass().getName(), dataTypeIsExact());
	}

	/**
	 * test whether {@link IRingElement#abs()} can be executed and returns the
	 * same element.
	 */
	@Override
	@Test
	public void testAbs_base()
	{
		assertSame(getFactory().zero(), getFactory().zero().abs());
		IRingElement<?> e = getFactory().get("7");
		assertSame(e, e.abs());
	}

	@Test
	public void testLt()
	{
		IRingElement v = getFactory().get("44");
		IRingElement<?> w = getFactory().get("77");
		assertTrue(v.lt(w));
	}

	/**
	 * test whether {@link IRingElement#norm()} can be executed and returns the
	 * same element.
	 */
	@Override
	@Test
	public void testNorm_base()
	{
		assertSame(getFactory().zero(), getFactory().zero().norm());
		IRingElement<?> e = getFactory().get("7");
		assertSame(e, e.norm());
	}

	@Override
	@Test
	@Ignore
	public void testInvert_base()
	{
	}

	@Override
	@Test
	@Ignore
	public void testDivide_base()
	{
	}

	/**
	 * test whether the inversion of zero results in an exception
	 */
	@Override
	@Test(expected = org.jlinalg.InvalidOperationException.class)
	public void invertZero_base()
	{
		getFactory().zero().invert();
	}
}
