## Service进程保活
##### 服务杀不死实现原理:
* 1：Android一个app可以启动2个进程
* 2：进程A与进程B互相守护
* 3：进程A时刻盯着进程B，进程B也时刻盯着进程A
* 4：进程之间互相盯着，需要用到IPC进程间通讯也就是AIDL
* 5：注意配置远程服务为一个新的进程并命名为：
android：process=“进程名”
* 6：进程A服务绑定进程B服务，进程B服务绑定进程A服务
不杀不死杀不死
* 7：手机开机和电量等系统广播自动激活Service
![icon](http://img.blog.csdn.net/20170921181542696?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQvZmFuZW5xaWFu/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)

