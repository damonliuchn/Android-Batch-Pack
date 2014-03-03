@echo off
rem ±àÒë´ò°üapk
%~d0

rem cd %~dp0project\MDApp_Android

cd %~dp0project\%1


ant clean release

rem ant -buildfile build.xml
rem ###################################

