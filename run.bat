echo %TIME%
dir /s /b *.java > sources.txt
javac -g -Xlint:all -Werror -d build @sources.txt
java -cp build Main %1
