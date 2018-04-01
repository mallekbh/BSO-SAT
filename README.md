<p>This is a <b>JAVA</b> implementation of the <b>BSO</b> (Bees swarm optimisation) algorithm for solving the <b>SAT</b> problem.</p>

<p>Let V = {v1 , v2 , ... , vn } be a set of Boolean variables</p>
<ul>
	<li>A <b>literal</b> is a Boolean variable that appears with or without the negation operator.</li>
	<li>A <b>clause</b> is a Boolean disjunction of literals.</li>
</ul>
	
	

<p>the problem SAT is defined by the following (instance, question) pair:</p>
<ul>
	<li><b>Instance:</b> m clauses over n Boolean variables (a CNF Boolean formula)</li>
	<li><b>Question:</b> is there an instanciation or interpretation of variables for which all the
	clauses are true simultaneously?</li>
</ul>
	
	

<p>V = {v1 , v2 , v3 , v4 }, C = {c1 , c2 , c3 } such that:</p>
<ul>
	<li>c1 = v1 + v2 + !v4</li>
	<li>c2 = v2 + !v3 + v4</li>
	<li>c3 = !v1 + !v2 + v3</li>
</ul>
	
	
	

<p>Where '!' sign denotes the negation operator and '+' the disjunctive operator. One possible
solution is the instanciation {T, T, T, T} respectively assigned to {v1, v2, v3, v4}.</p>
