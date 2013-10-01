clean:
	-rm -rf build
	-mkdir build

timetunnel: clean common mastermindpackage
	javac -d build -cp build src/neobots/timetunnel/*.java

typingterror: clean common
	javac -d build -cp build src/neobots/typingterror/*.java

nationalbank: clean common
	javac -d build -cp build src/neobots/nationalbank/*.java

mastermindpackage:
	javac -d build src/mastermind/*.java

common:
	javac -d build src/neobots/common/*.java
