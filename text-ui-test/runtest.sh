#!/usr/bin/env bash

# create bin directory if it doesn't exist
if [ ! -d "../bin" ]
then
    mkdir ../bin
fi

# delete output from previous run
if [ -e "./ACTUAL1.TXT" ]
then
    rm ACTUAL*.TXT
fi

# compile the code into the bin folder, terminates if error occurred
if ! javac -cp ../src/main/java -Xlint:none -d ../bin ../src/main/java/inuChan/*.java ../src/main/java/inuChan/tasks/*.java ../src/main/java/inuChan/command/*.java ../src/main/java/inuChan/command/commandexceptions/*.java
then
    echo "********** BUILD FAILURE **********"
    exit 1
fi

# run the program, feed commands from input4.txt file and redirect the output to the ACTUAL3.TXT
java -classpath ../bin inuChan.InuChan < input1.txt > ACTUAL1.TXT
java -classpath ../bin inuChan.InuChan < input2.txt > ACTUAL2.TXT
java -classpath ../bin inuChan.InuChan < input3.txt > ACTUAL3.TXT
java -classpath ../bin inuChan.InuChan < input4.txt > ACTUAL4.TXT

# convert to UNIX format
cp EXPECTED1.TXT EXPECTED-UNIX1.TXT
#dos2unix ACTUAL1.TXT EXPECTED-UNIX1.TXT
cp EXPECTED2.TXT EXPECTED-UNIX2.TXT
#dos2unix ACTUAL2.TXT EXPECTED-UNIX2.TXT
cp EXPECTED3.TXT EXPECTED-UNIX3.TXT
#dos2unix ACTUAL3.TXT EXPECTED-UNIX3.TXT
cp EXPECTED4.TXT EXPECTED-UNIX4.TXT
#dos2unix ACTUAL4.TXT EXPECTED-UNIX4.TXT

# compare the output to the expected output
diff ACTUAL1.TXT EXPECTED-UNIX1.TXT
if [ $? -eq 0 ]
then
    echo "Test 1 result: PASSED"
else
    echo "Test 1  result: FAILED"
fi

diff ACTUAL2.TXT EXPECTED-UNIX2.TXT
if [ $? -eq 0 ]
then
    echo "Test 2 result: PASSED"
else
    echo "Test 2 result: FAILED"
fi

diff ACTUAL3.TXT EXPECTED-UNIX3.TXT
if [ $? -eq 0 ]
then
    echo "Test 3 result: PASSED"
else
    echo "Test 3 result: FAILED"
fi

diff ACTUAL4.TXT EXPECTED-UNIX4.TXT
if [ $? -eq 0 ]
then
    echo "Test 4 result: PASSED"
    exit 0
else
    echo "Test 4 result: FAILED"
    exit 1
fi
