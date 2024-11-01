echo %TIME%
dir /s /b *.java > sources.txt
"C:\Program Files\OpenJDK\jdk-22.0.1\bin\javac.exe" -g -Xlint:all -Werror -d build @sources.txt
"C:\Program Files\OpenJDK\jdk-22.0.1\bin\java.exe" -cp build Main %1
