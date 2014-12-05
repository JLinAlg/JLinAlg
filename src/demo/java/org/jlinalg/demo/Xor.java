package org.jlinalg.demo;

import org.jlinalg.LinAlgFactory;
import org.jlinalg.Matrix;
import org.jlinalg.Vector;
import org.jlinalg.doublewrapper.DoubleWrapper;
import org.jlinalg.operator.MonadicOperator;

/**
 * Example showing Exclusive-Or neural net problem using JLinAlg. Example can be
 * easily modified by changing values of inpat, tgpat.
 * 
 * @author Simon D. Levy
 */
public class Xor
{

	/**
	 * inpat holds the input part of the training patterns
	 */
	@SuppressWarnings("boxing")
	private static Double[][] inpat = {
			{
					0., 0.
			}, {
					0., 1.
			}, {
					1., 0.
			}, {
					1., 1.
			}
	};

	/**
	 * tgpat holds the target part of the training patterns
	 */
	@SuppressWarnings("boxing")
	private static Double[][] tgpat = {
			{
				0.
			}, {
				1.
			}, {
				1.
			}, {
				0.
			}
	};

	/**
	 * how many epochs the training should last
	 */
	private static final int NEPOCH = 10000;

	/**
	 * learning rate
	 */
	private static final double ETA = 0.1;

	/**
	 * the momentum
	 */
	private static final double MU = 0.9;

	/**
	 * the number of hidden units
	 */
	private static final int NHID = 3;

	/**
	 * used to create vectors and arrays of {@link DoubleWrapper}s
	 */
	private LinAlgFactory<DoubleWrapper> df;

	/**
	 * the number of inputs
	 */
	int ninp;

	/**
	 * the number of outputs
	 */
	int nout;

	/**
	 * the number of patterns
	 */
	int npat;

	/**
	 * the weights between the input and hidden layer
	 */
	private Matrix<DoubleWrapper> wih; // input->hidden weights

	/**
	 * the weights between the hidden and output layer
	 */
	private Matrix<DoubleWrapper> who; // hidden->output weights

	/**
	 * the biases used in the hidden layer
	 */
	private Vector<DoubleWrapper> bh; // bias on hidden

	/**
	 * the biases used in the output layer
	 */
	private Vector<DoubleWrapper> bo; // bias on output

	/**
	 * Create an instance of this class and ininitialise some arrays.
	 */
	public Xor()
	{
		df = new LinAlgFactory<DoubleWrapper>(DoubleWrapper.FACTORY);

		// this allows us to generalize to new problems
		ninp = inpat[0].length;
		nout = tgpat[0].length;
		npat = inpat.length;

		// weights are initially random
		wih = df.gaussianNoise(ninp, NHID);
		who = df.gaussianNoise(NHID, nout);

		// biases are initially random
		bh = df.gaussianNoise(NHID);
		bo = df.gaussianNoise(nout);
	}

	/**
	 * train the network the {@link #NEPOCH} times on all training patterns.
	 */
	public void train()
	{

		// space savers
		SigmoidOperator sgop = new SigmoidOperator();
		SigdervOperator sdop = new SigdervOperator();
		DoubleWrapper eta = new DoubleWrapper(ETA);
		DoubleWrapper mu = new DoubleWrapper(MU);
		DoubleWrapper npatd = new DoubleWrapper(npat);
		DoubleWrapper errd = new DoubleWrapper(npat * nout);

		// initialize momentum terms for weight, bias changes
		Matrix<DoubleWrapper> dwih1 = df.zeros(ninp, NHID);
		Matrix<DoubleWrapper> dwho1 = df.zeros(NHID, nout);
		Vector<DoubleWrapper> dbh1 = df.zeros(NHID);
		Vector<DoubleWrapper> dbo1 = df.zeros(nout);

		// train for specified number of epochs
		for (int i = 0; i < NEPOCH; ++i) {

			// initialize weight, bias changes
			Matrix<DoubleWrapper> dwih = df.zeros(ninp, NHID);
			Matrix<DoubleWrapper> dwho = df.zeros(NHID, nout);
			Vector<DoubleWrapper> dbh = df.zeros(NHID);
			Vector<DoubleWrapper> dbo = df.zeros(nout);

			// initialize squared error
			Vector<DoubleWrapper> sqrerr = df.zeros(nout);

			// loop over patterns
			for (int j = 0; j < npat; ++j) {

				Vector<DoubleWrapper> ai = new Vector<DoubleWrapper>(inpat[j],
						DoubleWrapper.FACTORY);

				// run forward pass
				Vector<DoubleWrapper> ah = ai.multiply(wih).add(bh).apply(sgop);
				Vector<DoubleWrapper> ao = ah.multiply(who).add(bo).apply(sgop);

				// compute output error/delta from target
				Vector<DoubleWrapper> eo = new Vector<DoubleWrapper>(tgpat[j],
						DoubleWrapper.FACTORY).subtract(ao);
				Vector<DoubleWrapper> dlo = eo.arrayMultiply(ao.apply(sdop));

				// compute hidden error/delta by back-prop from output delta
				Vector<DoubleWrapper> eh = dlo.multiply(who.transpose());
				Vector<DoubleWrapper> dlh = eh.arrayMultiply(ah.apply(sdop));

				// accumulate weight- and bias- changes using the Delta Rule
				dwih = dwih.add(ai.transposeAndMultiply(dlh));
				dwho = dwho.add(ah.transposeAndMultiply(dlo));
				dbh = dbh.add(dlh);
				dbo = dbo.add(dlo);

				// accumulate squared error
				sqrerr = sqrerr.add(eo.arrayMultiply(eo));
			}

			// update weight and biases
			wih = wih.add(dwih.divide(npatd).multiply(eta)).add(
					dwih1.divide(npatd).multiply(mu));
			who = who.add(dwho.divide(npatd).multiply(eta)).add(
					dwho1.divide(npatd).multiply(mu));
			bh = bh.add(dbh.divide(npatd).multiply(eta)).add(
					dbh1.divide(npatd).multiply(mu));
			bo = bo.add(dbo.divide(npatd).multiply(eta)).add(
					dbo1.divide(npatd).multiply(mu));

			// recall weight, bias changes for momentum on next epoch
			dwih1 = dwih;
			dwho1 = dwho;
			dbh1 = dbh;
			dbo1 = dbo;

			// report RMS error first, last, every 1000 epochs
			if (i == 0 || i == NEPOCH - 1 || ((i + 1) % 1000) == 0) {
				System.err
						.println("EPOCH: "
								+ (i + 1)
								+ "\tRMS ERROR: "
								+ Math.sqrt(((sqrerr.sum()).divide(errd))
										.doubleValue()));
			}
		}
	}

	/**
	 * test the performance of the neural net on the training patterns.
	 */
	public void test()
	{
		SigmoidOperator sgop = new SigmoidOperator();

		for (int j = 0; j < npat; ++j) {

			Vector<DoubleWrapper> ai = new Vector<DoubleWrapper>(inpat[j],
					DoubleWrapper.FACTORY);
			Vector<DoubleWrapper> tg = new Vector<DoubleWrapper>(tgpat[j],
					DoubleWrapper.FACTORY);

			// run forward pass
			Vector<DoubleWrapper> ah = ai.multiply(wih).add(bh).apply(sgop);
			Vector<DoubleWrapper> ao = ah.multiply(who).add(bo).apply(sgop);

			// report actual, target output
			System.out.println(ao.toString().substring(2, 10) + "  " + tg);
		}
	}

	/**
	 * operator defining the sigmoid squashing function
	 */
	private class SigmoidOperator
			implements MonadicOperator<DoubleWrapper>
	{
		@Override
		public DoubleWrapper apply(DoubleWrapper x)
		{
			double dx = x.getValue();
			return new DoubleWrapper(1 / (1 + Math.exp(-dx)));
		}
	}

	/**
	 * operator defining the first derivative of the sigmoid w.r.t. activation
	 */
	private class SigdervOperator
			implements MonadicOperator<DoubleWrapper>
	{
		@Override
		public DoubleWrapper apply(DoubleWrapper x)
		{
			double dx = (x).getValue();
			return new DoubleWrapper(dx * (1 - dx));
		}
	}

	/**
	 * Start the demo.
	 * 
	 * @param argv
	 *            ignored
	 */
	public static void main(String[] argv)
	{

		Xor xor = new Xor();
		xor.train();
		xor.test();
	}
}
