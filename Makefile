cls:
	echo "Compiling..."
	javac ./src/*.java -d ./class

runFirst:
	cd class && java mainFirst

runSecond:
	cd class && java mainSecond

runThird:
	cd class && java mainThird

doc:
	echo "Generating javadoc..."
	mkdir doc
	javadoc ./src/*.java -d ./doc

clean:
	echo "Cleaning..."
	rmdir /S /Q doc
	rmdir /S /Q class
	echo "Done."
