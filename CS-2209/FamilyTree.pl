/* FACTS */
father(X,Y) :- parent(X,Y), male(X).
mother(X,Y) :- parent(X,Y), female(X).
brothers(X,Y) :- sibling(X,Y),male(X),male(Y),X\=Y.
sibling(X,Y) :- parent(Z,X), parent(Z,Y), X\=Y.
grandson(X,Y) :- parent(Y,Z), parent(Z,X), male(X).
cousin(X,Y) :- parent(Z,X), sibling(Z,A), parent(A,Y).
married(X,Y) :- father(X,Z), mother(Y,Z).
motherinlaw(X,Y) :- married(Z,Y), mother(X,Z).
motherinlaw(X,Y) :- married(Y,Z), mother(X,Z).
descendent(X,Y):- parent(Y,X). 
descendent(X,Y):- parent(P,X),descendent(P,Y). 

 

male(adam).
male(alex).
male(bruce).
male(brian).
male(charles).
male(chris).
male(dunkin).
male(drew).

female(kelly).
female(kelsey).
female(lisa).
female(linda).
female(michelle).
female(marry).
female(nicole).
female(nari).

parent(kelly,adam).
parent(kelly,alex).
parent(bruce,adam).
parent(bruce,alex).
parent(dunkin,kelly).
parent(nicole,kelly).
parent(lisa,bruce).
parent(brian,bruce).
parent(lisa,linda).
parent(brian,linda).
parent(linda,chris).
