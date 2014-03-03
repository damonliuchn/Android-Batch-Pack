@echo off

rem ###############get java name,c is the java name;
set b=%~nx1
set c=%b:~0,-5%

rem ############### excute java
javac -encoding UTF-8 %1
java %c% %2 %3 %4 %5 %6 %7 %8 %9
