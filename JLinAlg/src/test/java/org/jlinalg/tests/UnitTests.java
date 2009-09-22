package org.jlinalg.tests;

import org.jlinalg.testutil.Suite;
import org.junit.runner.RunWith;

/**
 * Perform all unit tests.
 * 
 * @author Georg Thimm
 */

@RunWith(Suite.class)
@Suite.SuiteClasses(value = {}, include = "org.jlinalg.*Test", exclude = ".*(BlackBox|\\$|Suite|Demo).*")
public class UnitTests
{

}
