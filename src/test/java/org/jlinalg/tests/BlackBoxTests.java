package org.jlinalg.tests;

import org.jlinalg.testutil.Suite;
import org.junit.runner.RunWith;

/**
 * Perform the more expensive tests (e.g. those using random numbers).
 * 
 * @author Georg Thimm
 */
@RunWith(Suite.class)
@Suite.SuiteClasses(include = "org.jlinalg.*BlackBox.*Test")
public class BlackBoxTests
{

}
