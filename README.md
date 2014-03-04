# Android-Batch-Pack

------

## 什么是 Android-Batch-Pack

Android-Batch-Pack 是实现批量打包apk的工具，适用于包含了第三方library project的project，并且不影响project的代码混淆，每次打包自动修改用于统计的渠道标签（如友盟统计的channel tag）。目前只是用于windows平台。


------

##安卓批量打包工具用法：

###1、安装jdk、ant，配置环境变量

###2、在项目中生成build.xml

* 1、进入命令行模式，并切换到项目目录，执行如下命令为ADT创建的项目添加ant build支持：（library project中也需要生产）
android update project -p . -t  "android-17"
* 2、build脚本默认target是help，所以会显示如上信息，修改target为debug或release就可以像无ant时一样编译、生成以及调试了。（library project中不需要）
* 3、使用release时  在local.properties添加签名信息（library project中不需要）
key.store=hujiang.p12
key.alias=xxx
key.store.password=xxx
key.alias.password=xxx

###3、修改项目的AndroidManifest.xml
&lt;!--ant-package-tag-start-umeng-->&lt;meta-data android:value="xiaomi" android:name="UMENG_CHANNEL"/>&lt;!--ant-package-tag-end-umeng--> 
目的是让工具识别到你的友盟标签

###4、将您的项目包括library project 都拷贝到 ant-package\project 下（自己建一个project文件夹）

###5、设置start.bat 里面的 渠道名 和 您的project文件夹名

set a=jifengwang_market_anzhiwang  这里的渠道名用下划线分割

call myjava.bat AntPackage.java %~dp0project\xx %a%  把xx改成你的文件夹名

###6、运行start.bat
------
##欢迎批评指正

博客：http://blog.csdn.net/tomblog
邮箱：MasonLiuChn@gmail.com




