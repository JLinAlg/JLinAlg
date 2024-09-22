JLinAlg is an open source and easy to use Java library for linear algebra.
It is licensed under the LGPL (changed from GPL on December, 10th 2014).

JLinAlg is an open source and easy to use Java library for linear algebra.  
It is licensed under the [**LGPL**](http://www.gnu.org/copyleft/lgpl.html) (changed from GPL on December, 10th 2014).

  
  

\[[Features](#Features)\] \[[Getting Started](#GettingStarted)\] \[[Download](#Download)\] \[[Documentation](#Documentation)\] \[[Examples](#Examples)\] \[[Project Members](#Members)\] \[[Links](#Links)\]

  

Features
--------

*   RingElements and FieldElements with operations like addition, subtraction, multiplication and - for FieldElements - division.
*   Euclidean division and Euclidean algorithm on any RingElements (Matrix, Polynomials, Rationals, Complex numbers etc.).
*   Basic matrix and vector operations like scalar product and matrix multiplication. Also: Fast matrix multiplication using the Strassen- and the Strassen-Winograd algorithms.
*   Matrices and vectors can be filled with arbitrary field elements like rational numbers, complex numbers or even prime fields.
*   Compute the inverse or the determinant of a matrix.
*   Gauss- and Gauss-Jordan algorithm are available.
*   Calculate a solution or the solution space of a linear equation system.
*   Calculate the eigenvalues of real matrices.
*   Univariate polynomials including polynomial division and calculating the GCD
*   All operations can be done without rounding errors (except for numeric data types like DoubleWrapper).
*   Element-wise mathematical and logical operations on vectors and matrices.
*   L1 and L2 vector norms.
*   Canonical vector and matrix factory methods: identity matrix, all ones, all zeros, uniformly distributed noise, Gaussian (normally distributed) noise.
*   Application of user-defined functions to all elements of a matrix or vector (or pairs of matrices and vectors).

Getting Started
---------------

1.  Go to the [Download](#Download) section and get the latest version of JLinAlg.
2.  Set up your environment (We recommend to use [Eclipse 2020-09](http://www.eclipse.org/) in any of the following two cases):
    *   If you want to use JLinAlg as a library, use jlinalg-v0.xx.jar contained in the Zip-file. In Eclipse you can just add it to your project by going to Project -> Properties -> java build path -> libraries. There you can use "Add Jars" (drag and drop jlinalg-0.xx.jar to your project first) or "Add External Jars" and select the location of jlinalg-0.xx.jar
    *   In case you want to have a look at the source code, you can find it inside the Zip-file under "JLinAlg/src". In Eclipse you can just go to File -> Import -> Existing Projects into Workspace. Now select the directory containing the downloaded Zip-file and the project JLinAlg-v0.xx should appear and be checked. Now click "finish".
3.  Get to know JLinAlg. Have a look at the [examples](#Examples) and the [documentation](#Documentation) (both also included in the Zip-File).
4.  Do something cool with it.

Download
--------

*   [JLinAlg-v0.8](https://sourceforge.net/projects/jlinalg/files/latest/download): The complete source code including Eclipse project files, the API as a jar-file and javadoc in one zip-file.
*   [Earlier versions](https://sourceforge.net/project/showfiles.php?group_id=71387): Other releases at Sourceforge.

Documentation
-------------

*   [JLinalg - structure: packages and classes](https://jlinalg.sourceforge.net/structure.html)
*   [JLinalg API-Documentation](https://jlinalg.sourceforge.net/javadocs) (generated Javadoc): Quite thorough documentation of every method and constructor.
*   Browse the history of in our [Github-Repository](https://github.com/JLinAlg/JLinAlg), [SVN repository](http://sourceforge.net/p/jlinalg/code/HEAD/tree/) or, even further back, the one in our old [CVS repository](http://jlinalg.cvs.sourceforge.net/jlinalg/).

Examples
--------

*   The class F2 represents an element of the modulo 2 field F2 and a factory as inner class. F2 is a field, just like the rational numbers. The examples in [F2Demo.java](https://jlinalg.sourceforge.net/code/F2Demo.html) illustrate how to create elements in F2 using the factory, how to create matrices, and to operate on them. The file [F2.java](https://jlinalg.sourceforge.net/code/F2.html) shows how to implement a type and a factory.
*   [LinearEquationSystemDemo.java](https://jlinalg.sourceforge.net/code/LinearEquationSystemDemo.html): This example shows how to calculate a solution or its solution space.
*   [MatrixOperationsDemo.java](https://jlinalg.sourceforge.net/code/MatrixOperationsDemo.html): How to calculate them for matrices over arbitrary fields.
*   [EigenvaluesDemo.java](https://jlinalg.sourceforge.net/code/EigenvaluesDemo.html): This example shows how JLinAlg can do eigenvalue computation with real matrices (matrices with DoubleWrapper entries).
*   [FieldPDemo.java](https://jlinalg.sourceforge.net/code/FieldPDemo.html): This demonstration of the class FieldP shows that in vector spaces over finite fields there can be linear dependent vectors which are all orthogonal to each other.
*   [Xor.java](https://jlinalg.sourceforge.net/code/Xor.html): Exclusive-Or neural net problem using JLinAlg.
*   [HilbertMatrixDemo.java](https://jlinalg.sourceforge.net/code/HilbertMatrixDemo.html): This demo shows how the squared distance between the real solution of an equation system to the one that is found when using floating point arithmetic, grows exponentially when the dimension of the Hilbert matrix increases.
*   [ArbitraryPrecisionDemo.java](https://jlinalg.sourceforge.net/code/ArbitraryPrecisionDemo.html): Demonstration that shows how quickly floating point numbers can cause rounding errors and how you can avoid these using arbitrary precision (Rational).
*   [PolynomialDemo.java](https://jlinalg.sourceforge.net/code/PolynomialDemo.html): Some operations on polynomials (including calculating the GCD using the Euclidean algorithm).

Project Members
---------------

*   [Andreas Keilhauer](http://www.keilhauer.eu/): Freelance software engineer and founder of JLinAlg, Germany
*   [Simon D. Levy](http://www.cs.wlu.edu/~levy): Assoc. Professor, Washington and Lee University, USA
*   [Andreas Lochbihler](http://pp.info.uni-karlsruhe.de/personhp/andreas_lochbihler.php): Postdoc at the ETH Zurich, Switzerland
*   [Safak Ökmen](mailto:oekmen@in.tum.de): Computer Scientist, TU Munich, Germany.
*   [Georg Thimm](http://www.adam.ntu.edu.sg/~mgeorg): Seniour Software Architect, Germany
*   Christian Würzebesser: former Research Associate, EAWAG, Zurich, Switzerland
*   ... and perhaps you, too. Use our [forums](https://sourceforge.net/forum/?group_id=71387) at Sourceforge and contact us!

Links
-----

*   [Articles about JLinAlg](http://www.whatsoftwarecando.org/category/jlinalg) on [WhatSoftwareCanDo.org](http://www.whatsoftwarecando.org/)
*   [Regression 2003](http://www.keilhauer.eu/ProPra): Program for regression analysis (uses JLinAlg)
*   [SNARLI](http://snarli.sourceforge.net/): Java-library for neural networks (uses JLinALg)
*   [Crystal Net tools](http://www.adam.ntu.edu.sg/~mgeorg/) analyse auto- and isomorphisms of infinite nets (representing often crystals ;-) and needs vectors and matrices with rational numbers for this.
*   [JAS](http://krum.rz.uni-mannheim.de/jas/): The Java Algebra System (JAS) is an object oriented, type safe and multi-threaded approach to computer algebra. It also has an [adapter for JLinAlg](http://krum.rz.uni-mannheim.de/jas/doc/download.html).

