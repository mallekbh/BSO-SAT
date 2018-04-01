This is a JAVA implementation of the BSO (Bees swarm optimisation) algorithm for solving the SAT problem.

Let V = {v1 , v2 , ... , vn } be a set of Boolean variables
	a literal is a Boolean variable that appears with or without the negation operator.
	a clause is a Boolean disjunction of literals.

the problem SAT is defined by the following (instance, question) pair:
	Instance: m clauses over n Boolean variables (a CNF Boolean formula)
	Question: is there an instanciation or interpretation of variables for which all the
	clauses are true simultaneously?

V = {v1 , v2 , v3 , v4 }, C = {c1 , c2 , c3 } such that:
	c 1 = v1 + v2 + !v4
	c 2 = v2 + !v3 + v4
	c 3 = !v1 + !v2 + v3

Where '!' sign denotes the negation operator and '+' the disjunctive operator. One possible
solution is the instanciation {T, T, T, T} respectively assigned to {v1, v2, v3, v4}.
