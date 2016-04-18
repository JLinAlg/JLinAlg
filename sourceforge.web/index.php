<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>JLinAlg - Open Source And Easy-to-Use Java-library For Linear Algebra</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<meta name="description" content="JLinAlg - Open Source And Easy-to-Use Java Library For Linear Algebra">
<link href="common.css" rel="stylesheet" type="text/css">
</head>
<body>
<h1>JLinAlg</h1>
<br>
<div align="center"><img src="logo-small.gif" alt="Logo"></div>
<br>
<br>
<div align="center"> 
JLinAlg is an open source and easy to use Java library for linear algebra.<br>
It is licensed under the <a href="http://www.gnu.org/copyleft/lgpl.html"><strong>LGPL</strong></a> (changed from GPL on December, 10th 2014).
</div>
<br>
<br>
<Center>
<table width="100%" border="0" bgcolor="#FFF0E0" cellpadding="0" cellspacing="0">
	<tr>
      	<td height="1" colspan="5" bgcolor="#808080">
	  	</td>
	</tr>
	<tr>
      	<td width="1" height="20" bgcolor="#808080">
		</td>
		<td></td>
		<td></td>
		<td></td>
		<td width="1" height="20" bgcolor="#808080">
		</td>		
	</tr>
	<tr>		
		<td width="1" bgcolor="#808080">
		</td>
		<td width="10"></td>
		<td><Center> 
[<a href="#Features">Features</a>] [<a href="#GettingStarted">Getting Started</a>] [<a href="#Download">Download</a>] [<a href="#Documentation">Documentation</a>] [<a href="#Examples">Examples</a>] [<a href="#Members">Project Members</a>] [<a href="#Links">Links</a>]
		</Center>
		</td>
		<td width="10">
		</td>
		<td width="1" bgcolor="#808080">
		</td>
	</tr>
	<tr>
		<td width="1" height="20" bgcolor="#808080">
		</td>
		<td></td>
		<td></td>
		<td></td>
		<td width="1" height="20" bgcolor="#808080">
		</td>		
	</tr>
	<tr>
		<td height="1" colspan="5" bgcolor="#808080">
		</td>
	</tr>
</table>
</Center>

<br>
<a name="Features"></a>
 
<h2>Features</h2>

<ul>
  <li>Basic matrix and vector operations like scalar product and matrix multiplication. 
     Also: Fast matrix multiplication using the Strassen- and the Strassen-Winograd algorithms.</li>
  <li>Matrices and vectors can be filled with arbitrary field elements like  
     rational numbers, complex numbers or even prime fields.</li>
	
  <li>Compute the inverse or the determinant of a matrix.</li>
  <li>Gauss- and Gauss-Jordan algorithm are available.</li>
  <li>Calculate a solution or the solution space of a linear equation system.</li>
  <li>Calculate the eigenvalues of real matrices.</li>
	
  <li>All operations can be done without rounding errors 
  	 (except for numeric data types like DoubleWrapper).</li>
  <li>Element-wise mathematical and logical operations on vectors and matrices.</li>
  <li>L1 and L2 vector norms.</li>
	
  <li>Canonical vector and matrix factory methods: identity matrix, 
  	all ones, all zeros, uniformly distributed noise, 
    Gaussian (normally distributed) noise.</li>
  <li>Application of user-defined functions to all elements of a matrix 
	 or vector (or pairs of matrices and vectors).</li>

</ul>

<a name="GettingStarted"></a>
 
 
<h2>Getting Started</h2>
<ol>
<li>Go to the <a href="#Download">Download</a> section and get the latest version of JLinAlg.</li>
<li>Set up your environment (We recommend to use <a href="http://www.eclipse.org">Eclipse</a> in any of the following two cases):
   <ul>
      <li>If you want to use JLinAlg as a library, use jlinalg-v0.xx.jar contained in the Zip-file. 
          In Eclipse you can just add it to your project by going to Project -> Properties -> 
          java build path -> libraries. There you can use "Add Jars" (drag and drop jlinalg-0.xx.jar 
          to your project first) or "Add External Jars" and select the location of jlinalg-0.xx.jar</li>
      <li>In case you want to have a look at the source code, you can find it inside the Zip-file 
          under "JLinAlg/src".
          In Eclipse you can just go to File -> Import -> Existing Projects into Workspace. 
          Now select the directory containing the downloaded Zip-file and the project JLinAlg-v0.xx 
          should appear and be checked. Now click "finish". 
      </li>
   </ul>
</li>
<li>Get to know JLinAlg. Have a look at the <a href="#Examples">examples</a> and the <a href="#Documentation">documentation</a> (both also included in the Zip-File).</li>
<li>Do something cool with it.</li>
<li>Use our <a href="https://sourceforge.net/forum/?group_id=71387">forums</a> at Sourceforge and tell us about it!</li>
</ol>
<a name="Download"></a>
 
 
<h2>Download</h2>
<ul>
  <li><a href="http://sourceforge.net/projects/jlinalg/files/jlinalg_0.7.zip/download">JLinAlg-v0.7</a>: 
    The complete source code including Eclipse project files, the API as a jar-file and javadoc in one zip-file.</li>
	
  <li><a href="https://sourceforge.net/project/showfiles.php?group_id=71387">Earlier 
    versions</a>: Other releases at Sourceforge.</li>
</ul>
<a name="Documentation"></a>
 
 
<h2>Documentation</h2>
<ul>
  	<li><a href="/javadocs">JLinalg API-Documentation</a> (generated Javadoc): Quite 
    thorough documentation of every method and constructor.</li>
  	<li>Browse the history of our <a href="http://sourceforge.net/p/jlinalg/code/HEAD/tree/">SVN repository</a> or, even further back, the one in our old <a href="http://jlinalg.cvs.sourceforge.net/jlinalg/">CVS repository</a>.</li>
</ul>
<a name="Examples"></a>
 
 
<h2>Examples</h2>
<ul>
  <li>The class F2 represents an element of the modulo 2 field F2 and a factory as inner class. 
   F2 is a field,
   just like the rational numbers. The examples in  <a href="code/F2Demo.html">F2Demo.java</a> illustrate how to create elements in F2 using the 
    factory, how to create matrices, and to operate on them. The file <a href="code/F2.html">F2.java</a> shows how to implement a type and a factory.</li>
  <li><a href="code/LinearEquationSystemDemo.html">LinearEquationSystemDemo.java</a>: 
  	This example shows how to 
    calculate a solution or its solution space.</li>
  <li><a href="code/MatrixOperationsDemo.html">MatrixOperationsDemo.java</a>: How to calculate them for 
    matrices over arbitrary fields.</li>
  <li><a href="code/EigenvaluesDemo.html">EigenvaluesDemo.java</a>: This example 
    shows how JLinAlg can do eigenvalue computation 
    with real matrices (matrices with DoubleWrapper entries).</li>
  <li><a href="code/FieldPDemo.html">FieldPDemo.java</a>: This demonstration of the class FieldP shows that in vector spaces over 
    finite fields there can be linear dependent vectors which are all orthogonal to 
    each other.</li>
  <li><a href="code/Xor.html">Xor.java</a>: Exclusive-Or neural net problem using JLinAlg.</li>
  <li><a href="code/HilbertMatrixDemo.html">HilbertMatrixDemo.java</a>: This demo shows how the squared distance between the real solution of an equation system to the one that is found when using floating point arithmetic, grows exponentially when the dimension of the Hilbert matrix increases.</li>
  <li><a href="code/ArbitraryPrecisionDemo.html">ArbitraryPrecisionDemo.java</a>: Demonstration that shows how quickly floating point numbers can cause rounding errors and how you can avoid these using arbitrary precision (Rational).
</ul>

<a name="Members"></a>	
<h2>Project Members</h2>
<ul><li><a href="http://www.keilhauer.eu">Andreas Keilhauer</a>: Freelance software engineer and founder of JLinAlg, Germany</li>
	<li><a href="http://www.cs.wlu.edu/~levy">Simon D. Levy</a>: Assoc. Professor, Washington and Lee University, USA</li>
	<li><a href="http://pp.info.uni-karlsruhe.de/personhp/andreas_lochbihler.php">Andreas Lochbihler</a>: Postdoc at the ETH Zurich, Switzerland</li>
	<li><a href="mailto:oekmen@in.tum.de">Safak &Ouml;kmen</a>: Computer Scientist, TU Munich, Germany.
	<li><a href="http://www.adam.ntu.edu.sg/~mgeorg">Georg Lothar Thimm</a>: Asst. Professor, Nanyang Technological University, Singapore</li>
	<li>Christian W&uuml;rzebesser: former Research Associate, EAWAG, Zurich, Switzerland</li>
	<li>... and perhaps you, too. Use our <a href="https://sourceforge.net/forum/?group_id=71387">forums</a> at Sourceforge and contact us!</li>
</ul>
<a name="Links"></a>
 
 
<h2>Links</h2>
<ul>
  <li> <a href="https://sourceforge.net/projects/jlinalg/">JLinAlg 
    at Sourceforge</a>: Project website at Sourceforge</li>
  <li> <a href="http://www.whatsoftwarecando.org/category/jlinalg">Articles about JLinAlg</a> on <a href="http://www.whatsoftwarecando.org">WhatSoftwareCanDo.org</a></li>
  <li> <a href="http://www.keilhauer.eu/ProPra">Regression 2003</a>: Program for regression analysis (uses JLinAlg)</li>
  <li> <a href="http://snarli.sourceforge.net">SNARLI</a>: Java-library 
    for neural networks (uses JLinALg)</li>
    <li><a href="http://www.adam.ntu.edu.sg/~mgeorg/">Crystal Net tools</a> analyse auto- and isomorphisms of infinite nets (representing often crystals ;-) and needs vectors and matrices with rational numbers for this.
  <li> <a href="http://krum.rz.uni-mannheim.de/jas/">JAS</a>: The Java Algebra System (JAS) is an object oriented, type safe and multi-threaded approach to computer algebra. It also has an <a href="http://krum.rz.uni-mannheim.de/jas/download.html">adapter for JLinAlg</a>.
</ul>
<br>
<hr>
<br>
This project is hosted on...<br>
<A href="http://sourceforge.net"><IMG src="http://sourceforge.net/sflogo.php?group_id=71387&amp;type=5" alt="SourceForge.net Logo" width="210" height="62" border="0"></A><br><br>

<Strong>Last modification:</Strong> <?php echo(date("d.m.Y, H:i ", filemtime("index.php")));?><br>
<br>
  <p>
    <a href="http://validator.w3.org/check?uri=referer"><img border="0" src="http://www.w3.org/Icons/valid-html401" alt="Valid HTML 4.01 Transitional" height="31" width="88"></a>
  </p>
  <br><br>
</body>
</html>
