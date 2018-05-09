# hmyg203

`` **_发布打包上线流程_**

- 修改 version ---  并为正式   
  （位置 ---  AndroidManifest.xml    android:versionCode="**"    android:versionName="**"  统一向上 加一）
  
-  修改 api 为正式 @{link = com.hy.utils.GetServerUrl# isTest 改为  false } 
- Gradle  （app） ----  apply from ："tinker" 取消注释

-  @{link=com.hldj.hmyg.SettingActivity# line 107   修改 补丁日志}

- 启动gradle 编译   ：   Gradle projects -> root -> tasks -> build -> assembleRelease  编译基础包

基础包位置 ---  app  >  build >  bakApk    >   （最新时间  整个包）


 
``**_补丁加载流程_**
--
- 
