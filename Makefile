all: compile

compile:
	javac *.java

execute:
	java calculator

clean:
	rm -f *.class *~