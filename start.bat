@echo off
rem xcopy F:\work\workspace\MDApp_Android .\project\MDApp_Android\ /s /e /y
rem xcopy F:\work\workspace\lib_ActionBarSherlockLibrary .\project\lib_ActionBarSherlockLibrary\ /s /e /y
rem xcopy F:\work\workspace\lib_SlidingMenuLibrary .\project\lib_SlidingMenuLibrary\ /s /e /y
rem xcopy F:\work\workspace\lib_ViewPagerIndicatorlibrary .\project\lib_ViewPagerIndicatorlibrary\ /s /e /y
rem xcopy F:\work\workspace\AndroidAsync .\project\AndroidAsync\ /s /e /y

rem echo 当前盘符：%~d0
rem echo 当前盘符和路径：%~dp0project\MDApp_Android
rem echo 当前批处理全路径：%~f0
rem echo 当前盘符和路径的短文件名格式：%~sdp0
rem echo 当前CMD默认目录：%cd%




rem 执行java文件修改渠道名

cd java

set a=jifengwang_market_anzhiwang_anzhuo_360_wandoujia_appchina_nduoa_mumayi_qq_163_xiaomi

call myjava.bat AntPackage.java %~dp0project\MDApp_Android %a%

pause