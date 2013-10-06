clean:
	-rm -rf build
	-mkdir build

timetunnel: clean common mastermindpackage
	javac -d build -cp build src/neobots/timetunnel/*.java
	cp src/neobots/timetunnel/*.png build/

typingterror: clean common
	javac -d build -cp build src/neobots/typingterror/*.java
	cp src/neobots/typingterror/*.png build/

nationalbank: clean common
	javac -d build -cp build src/neobots/nationalbank/*.java
	cp src/neobots/nationalbank/*.png build/

mastermindpackage:
	javac -d build src/mastermind/*.java

sudokupackage:
	javac -d build src/sudoku/*.java


common:
	javac -d build src/neobots/common/*.java
