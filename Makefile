cls:
	javac ./ArbreB/src/*.java -d ./classes

runFirst:
	cd ./classes ; java mainFirst

runSecond:
	cd ./classes ; java mainSecond

runThird:
	cd ./classes ; java mainThird

doc:
	javadoc ./ArbreB/*.java -d ../doc

clean:
	rm -r doc
	rm -r classes
