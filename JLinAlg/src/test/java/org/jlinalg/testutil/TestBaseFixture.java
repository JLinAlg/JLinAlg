/**
 * 
 */
package org.jlinalg.testutil;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;

import org.jlinalg.IRingElement;
import org.jlinalg.JLinAlgTypeProperties;
import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;

/**
 * This class encapsulates the fixture for the factory used to create instances
 * of the base type and a {@link LinAlgFactory} instance.
 * <P>
 * Furthermore, it provides convenience methods to examine the annotations of
 * the class of the factory provided by {@link #getFactory()}.
 * 
 * @author Georg Thimm
 */
public abstract class TestBaseFixture<RE extends IRingElement<RE>>
		implements TestBaseInterface<RE>
{

	private LinAlgFactory<RE> linAlgFactory = null;

	/**
	 * @see TestBaseInterface
	 */
	@Override
	final public LinAlgFactory<RE> getLinAlgFactory()
	{
		if (linAlgFactory == null) {
			linAlgFactory = new LinAlgFactory<RE>(getFactory());
		}
		return linAlgFactory;
	}

	/**
	 * Uses the annotation of the class returned by {@link #getFactory()} to
	 * determine whether the data type is exact.
	 * 
	 * @return true if the data type is exact.
	 * @see JLinAlgTypeProperties
	 */
	public boolean dataTypeIsExact()
	{
		JLinAlgTypeProperties annotation = getFactoryAnnotation();
		return annotation.isExact();
	}

	/**
	 * @return the annotation of the factory (or, for compound types, of the
	 *         factory of the base type).
	 */
	private JLinAlgTypeProperties getFactoryAnnotation()

	{
		JLinAlgTypeProperties annotation = getFactory().getClass()
				.getAnnotation(JLinAlgTypeProperties.class);
		assertNotNull(getFactory().getClass().getName(), annotation);
		if (annotation.isCompound()) {
			Object baseFactory;
			try {
				baseFactory = getFactory().getClass().getMethod(
						"getBaseFactory", (Class<?>[]) null).invoke(
						getFactory());
			} catch (Exception e) {
				throw new InternalError(e.toString());
			}
			annotation = baseFactory.getClass().getAnnotation(
					JLinAlgTypeProperties.class);
			assertNotNull(baseFactory.getClass().getName(), annotation);
		}
		return annotation;
	}

	/**
	 * Uses the annotation of the class returned by {@link #getFactory()} to
	 * determine whether the data type has negative values.
	 * 
	 * @return true if the data type has negative values.
	 * @see JLinAlgTypeProperties
	 */
	public boolean dataTypeHasNegativeValues()
	{
		JLinAlgTypeProperties annotation = getFactoryAnnotation();
		return annotation.hasNegativeValues();
	}

	/**
	 * Uses the annotation of the class returned by {@link #getFactory()} to
	 * determine whether the data type is discreet.
	 * 
	 * @return true if the data type is discreet.
	 * @see JLinAlgTypeProperties
	 */
	public boolean dataTypeIsDiscreet()
	{
		JLinAlgTypeProperties annotation = getFactoryAnnotation();
		return annotation.isDiscreet();
	}

	/**
	 * determine whether the method <code>name</code> of the base type is
	 * Depreciated.
	 * 
	 * @return true if it is depreciated.
	 */
	public boolean methodIsDepreciated(Object object, String name,
			Class<?>[] argTypes)
	{
		Method method;
		try {
			method = object.getClass().getMethod(name, argTypes);
			assertNotNull(method);
			Deprecated annotation = method.getAnnotation(Deprecated.class);
			// System.err.println(getFactory().getClass().getName() + " "
			// + method.toGenericString() + " " + annotation);
			return annotation != null;
		} catch (Throwable e) {
			fail("Exception " + e.getClass().getName() + ": " + e.getMessage()
					+ " occured in for " + getFactory().getClass().getName()
					+ "." + name + "()");
		}
		return false; // The compiler wants this, but how can this be reached?
	}

	/**
	 * determine whether the method <code>name</code> is defined for {@code
	 * object}
	 * 
	 * @return true if yes, false otherwise.
	 */
	public boolean hasMethod(Object object, String name, Class<?>[] argTypes)
	{
		try {
			object.getClass().getMethod(name, argTypes);
			return true;
		} catch (Throwable e) {
			return false;
		}
	}

	/**
	 * Compare two matrices for being exactly the same or to have a similarity.
	 * 
	 * @see TestBaseFixture#dataTypeIsExact()
	 * @param v1
	 * @param v2
	 * @param diff_
	 *            the maximal difference per element.
	 */
	void assertSimilar(Matrix<RE> v1, Matrix<RE> v2, String diff_)
	{
		if (dataTypeIsExact())
			assertEquals(v1, v2);
		else {
			RE diff = getFactory().get(diff_);
			for (int r = 1; r <= v1.getRows(); r++) {
				for (int c = 1; c <= v1.getCols(); c++) {
					assertTrue("diffenence between " + v1 + " and " + v2
							+ " is too big", v1.get(r, c)
							.subtract(v2.get(r, c)).abs().le(diff));
				}
			}
		}
	}

	/**
	 * Compare two vectors for being exactly the same or to have a similarity.
	 * 
	 * @see TestBaseFixture#dataTypeIsExact()
	 * @param v1
	 * @param v2
	 * @param diff_
	 *            the maximal difference per element.
	 */
	void assertSimilar(Vector<RE> v1, Vector<RE> v2, String diff_)
	{
		if (dataTypeIsExact())
			assertEquals(v1, v2);
		else {
			RE diff = getFactory().get(diff_);
			for (int i = 1; i <= v1.length(); i++) {
				assertTrue("diffenence between " + v1.toString() + " and "
						+ v2.toString() + " is too big", v1.getEntry(i)
						.subtract(v2.getEntry(i)).abs().le(diff));
			}
		}
	}

	/**
	 * Compare two RE's for being exactly the same or to have a similarity.
	 * 
	 * @see TestBaseFixture#dataTypeIsExact()
	 * @param v1
	 * @param v2
	 * @param diff_
	 *            the maximal difference.
	 */
	protected void assertSimilar(RE v1, RE v2, String diff_)
	{
		if (dataTypeIsExact() || methodIsDepreciated(v1, "abs", null))
			assertEquals("Factory: " + getFactory().getClass().getName(), v1,
					v2);
		else {
			RE diff = getFactory().get(diff_);
			assertTrue("Factory: " + getFactory().getClass().getName()
					+ "; diffenence between " + v1 + " and " + v2
					+ " is too big", v1.subtract(v2).abs().le(diff));
		}
	}
}
