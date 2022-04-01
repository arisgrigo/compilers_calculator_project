Makefile commands:
make compile, for making the .class files		(javac *.java)
make execute, for running the program				(java calculator)
make clean, for deleting .class files			(rm -f *.class *~)

Numbers with multiple digits and leading zeroes (like 05, 007, 00019) are not permitted.
The program does not accept inputs that contain a space between the letters (like 5 + 3, instead of 5+3)
The grammar that I implemented resembles LL(1) grammar.
The boolean flag, that is passed in most of the functions, flags if the parsing that is taking place at the moment, is inside(1) or outside(0) of a parenthesis.
