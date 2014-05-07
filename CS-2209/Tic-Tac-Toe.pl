/*First block creates the tic tac toe board*/
ordered_line(1,2,3).	
ordered_line(4,5,6).
ordered_line(7,8,9).	
ordered_line(1,4,7).
ordered_line(2,5,8).	
ordered_line(3,6,9).
ordered_line(1,5,9).	
ordered_line(3,5,7).
line(A,B,C) :- ordered_line(A,B,C).
line(A,B,C) :- ordered_line(A,C,B).
line(A,B,C) :- ordered_line(B,A,C).
line(A,B,C) :- ordered_line(B,C,A).
line(A,B,C) :- ordered_line(C,A,B).
line(A,B,C) :- ordered_line(C,B,A).

/*making a move*/
move(A) :- good(A), empty(A).

/*check if spot is empty or full*/
full(A) :- x(A).
full(A) :- o(A).
empty(A) :- not(full(A)).

/*what is an intelligent move*/
good(A) :- win(A).
good(A) :- block_win(A).
good(A) :- split(A).
good(A) :- block_autowin(A).
good(A) :- block_split(A).
good(A) :- build(A).
/*defaulting when no intelligent moves)*/
good(5).
good(1).
good(3).
good(7).
good(9).
good(2).
good(4).
good(6).
good(8).

/*intelligent move definition*/
win(A) :- x(B), x(C), line(A,B,C).
block_win(A) :- o(B), o(C), line(A,B,C).
split(A) :- x(B), x(C), different(B,C), line(A,B,D), line(A,C,E), empty(D), empty(E).
same(A,A).
different(A,B) :- not(same(A,B)).
block_split(A) :- o(B), o(C), different(B,C), line(A,B,D), line(A,C,E), empty(D), empty(E).
build(A) :- x(B), line(A,B,C), empty(C).

/*decided to include all the nots therefore will only work in that very specific circumstance*/
block_autowin(2) :- o(1),x(5),o(9),not(x(2)),not(x(3)),not(x(4)),not(x(6)),not(x(7)),not(x(8)), not(o(2)),not(o(3)),not(o(4)),not(o(6)),not(o(7)),not(o(8)).
block_autowin(2) :- o(3),x(5),o(6),not(x(2)),not(x(1)),not(x(4)),not(x(9)),not(x(7)),not(x(8)), not(o(2)),not(o(1)),not(o(4)),not(o(9)),not(o(7)),not(o(8)).

