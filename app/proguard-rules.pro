




# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/zhangshaowen/Library/Android/sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
# Bugly混淆规则
-dontwarn com.tencent.bugly.**
-keep public class com.tencent.bugly.**{*;}
-keep  class  android.support.**{*;}





#############################################
#
# 对于一些基本指令的添加
#
#############################################
# 代码混淆压缩比，在0~7之间，默认为5，一般不做修改
-optimizationpasses 5

# 混合时不使用大小写混合，混合后的类名为小写
-dontusemixedcaseclassnames

# 指定不去忽略非公共库的类
-dontskipnonpubliclibraryclasses

# 这句话能够使我们的项目混淆后产生映射文件
# 包含有类名->混淆后类名的映射关系
-verbose

# 指定不去忽略非公共库的类成员
-dontskipnonpubliclibraryclassmembers

# 不做预校验，preverify是proguard的四个步骤之一，android不需要preverify，去掉这一步能够加快混淆速度。
-dontpreverify

# 保留Annotation不混淆
-keepattributes *Annotation*,InnerClasses

# 避免混淆泛型
-keepattributes Signature

# 抛出异常时保留代码行号
-keepattributes SourceFile,LineNumberTable

# 指定混淆是采用的算法，后面的参数是一个过滤器
# 这个过滤器是谷歌推荐的算法，一般不做更改
-optimizations !code/simplification/cast,!field/*,!class/merging/*


#############################################
#
# Android开发中一些需要保留的公共部分
#
#############################################

# 保留我们使用的四大组件，自定义的Application等等这些类不被混淆
# 因为这些子类都有可能被外部调用
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Appliction
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.view.View
-keep public class com.android.vending.licensing.ILicensingService


# 保留support下的所有类及其内部类
-keep class android.support.** {*;}

# 保留继承的
-keep public class * extends android.support.v4.**
-keep public class * extends android.support.v7.**
-keep public class * extends android.support.annotation.**

# 保留R下面的资源
-keep class **.R$* {*;}

# 保留本地native方法不被混淆
-keepclasseswithmembernames class * {
    native <methods>;
}

# 保留在Activity中的方法参数是view的方法，
# 这样以来我们在layout中写的onClick就不会被影响
-keepclassmembers class * extends android.app.Activity{
    public void *(android.view.View);
}

# 保留枚举类不被混淆
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(Java.lang.String);
}

# 保留我们自定义控件（继承自View）不被混淆
-keep public class * extends android.view.View{
    *** get*();
    void set*(***);
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
}

# 保留Parcelable序列化类不被混淆
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

# 保留Serializable序列化的类不被混淆
-keepclassmembers class * implements Java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

# 对于带有回调函数的onXXEvent、**On*Listener的，不能被混淆
-keepclassmembers class * {
    void *(**On*Event);
    void *(**On*Listener);
}

# webView处理，项目中没有使用到webView忽略即可
-keepclassmembers class fqcn.of.JavaScript.interface.for.webview {
    public *;
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.WebView, java.lang.String, android.graphics.Bitmap);
    public boolean *(android.webkit.WebView, java.lang.String);
}
-keepclassmembers class * extends android.webkit.webViewClient {
    public void *(android.webkit.webView, jav.lang.String);
}

# 移除Log类打印各个等级日志的代码，打正式包的时候可以做为禁log使用，这里可以作为禁止log打印的功能使用
# 记得proguard-android.txt中一定不要加-dontoptimize才起作用
# 另外的一种实现方案是通过BuildConfig.DEBUG的变量来控制
#-assumenosideeffects class android.util.Log {
#    public static int v(...);
#    public static int i(...);
#    public static int w(...);
#    public static int d(...);
#    public static int e(...);
#}

#############################################
#
# 项目中特殊处理部分
#
#############################################

#-----------处理反射类---------------



#-----------处理js交互---------------



#-----------处理实体类---------------



#-----------处理第三方依赖库---------
#手动启用support keep注解
#http://tools.android.com/tech-docs/support-annotations
-dontskipnonpubliclibraryclassmembers
-printconfiguration
-keep,allowobfuscation @interface android.support.annotation.Keep

-keep @android.support.annotation.Keep class *
-keepclassmembers class * {
    @android.support.annotation.Keep *;
}


#-----------通过注解同意过滤处理---------
# keep annotated by NotProguard
#-keep @com.hldj.hmyg.util.NotProguard class * {*;}
#-keep class * {
#@com.hldj.hmyg.util.NotProguard <fields>;
#}
#-keep
##-keepclassmembers class * {
#-keepclassmembers class * {
#@com.hldj.hmyg.util.NotProguard <methods>;
#}
#-----------通过注解同意过滤处理---------


# Gson
#-keepattributes Signature-keepattributes *Annotation*
-keep class sun.misc.Unsafe { *;}
-keep class com.google.gson.stream.** { *;}
-keepattributes EnclosingMethod

-keepattributes Signature
-keep class sun.misc.Unsafe { *; }
# 使用Gson时需要配置Gson的解析对象及变量都不混淆。不然Gson会找不到变量。
# 将下面替换成自己的实体类
-keep class com.hldj.hmyg.bean.** { *;}
-keep class com.hldj.hmyg.buy.bean.** { *;}
-keep class com.hldj.hmyg.buyer.M.** { *;}
-keep class com.hldj.hmyg.M.** { *;}
-keep class com.hldj.hmyg.model.** { *;}
-keep class com.hldj.hmyg.saler.bean.** { *;}
-keep class com.hldj.hmyg.saler.M.** { *;}
-keep class com.autoscrollview.adapter.** { *;}
-keep class com.hldj.hmyg.Ui.friend.bean.** {*;}


-keepattributes Exceptions,InnerClasses,...
-keep class com.hldj.hmyg.SellectActivity2$* { * ; }
-keep class com.hldj.hmyg.saler.AdressManagerActivity$* { * ; }
-keep class com.hldj.hmyg.widget.ComonShareDialogFragment* { * ; }


#-keep class com.autoscrollview.adapter.ImagePagerAdapter { * ; }
#-keep class com.hldj.hmyg.AActivity_3_0* { * ; }
#$TypeBean

#-keepattributes Exceptions,InnerClasses,...
#-keep class com.hldj.hmyg.saler.AdressManagerActivity$AdressQueryBean { * ; }
#-keepattributes Exceptions,InnerClasses,...
#-keep class com.hldj.hmyg.saler.AdressManagerActivity$AdressQueryBean {  public <fields>; }
#-keep class com.hldj.hmyg.Ui.storeChild.StoreHomeFragment$QueryBean {
#         public <fields>;
# }


#-keepattributes Exceptions,InnerClasses,...
#-keep class com.xxx.A{ *; }
#-keep class com.xxx.A$B { *; }
#-keep class com.xxx.A$C { *; }




# 极光推送
-dontoptimize
-dontpreverify
-dontwarn cn.jpush.**
-keep class cn.jpush.** { *;}
-keep class com.hldj.hmyg.MyReceiver{ *;}



# Retrolambda
-dontwarn java.lang.invoke.*


# RxJava RxAndroid
-dontwarn sun.misc.**
-keepclassmembers class rx.internal.util.unsafe.*ArrayQueue*Field* {
    long producerIndex;
    long consumerIndex;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueProducerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode producerNode;
}
-keepclassmembers class rx.internal.util.unsafe.BaseLinkedQueueConsumerNodeRef {
    rx.internal.util.atomic.LinkedQueueNode consumerNode;
}

-keep class com.hldj.hmyg.base.rxbus.** { *;}



-keep public class * extends com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper
-keep public class * extends com.j256.ormlite.android.apptools.OpenHelperManager

#2、greenDao混淆
# # -------------------------------------------
# #  ######## greenDao混淆  ##########
# # -------------------------------------------


# # -------------------------------------------
# #  ######## greenDao混淆  ##########
# # -------------------------------------------
#-libraryjars libs/XX     （“XX”是jar包名）


#greendao3.2.0,此是针对3.2.0，如果是之前的，可能需要更换下包名
-ignorewarnings
-keep class org.greenrobot.greendao.**{*;}
-keepclassmembers class * extends org.greenrobot.greendao.AbstractDao {
public static java.lang.String TABLENAME;
}
-keep class **$Properties

-keep  class **.rx{*;}
-keep  class com.hldj.hmyg.DaoBean.SaveJson{*;}

-keep class data.db.dao.*$Properties {
    public static <fields>;
}
-keepclassmembers class data.db.dao.** {
    public static final <fields>;
  }



#4、sharesdk混淆
## ----------------------------------
##      sharesdk
## ----------------------------------
-keep class cn.sharesdk.**{*;}
-keep class com.sina.**{*;}
-keep class **.R$* {*;}
-keep class **.R{*;}
-keep class com.mob.**{*;}
-dontwarn com.mob.**
-dontwarn cn.sharesdk.**
-dontwarn **.R$*



-dontwarn com.amap.api.**
-keep class com.amap.api.location.{*;}
-keep class com.amap.api.**{*;}
-dontwarn com.amap.api.**
-dontwarn com.a.a.**
-dontwarn com.autonavi.**
-keep class com.amap.api.**  {*;}
-keep class com.autonavi.**  {*;}
-keep class com.a.a.**  {*;}


#8、内部类或者内部接口类的混淆配置
# # -------------------------------------------
# #  ######## 内部类混淆配置  ##########
# # -------------------------------------------
-keep class com.manjay.housebox.activity.CityListActivity$*{
        <fields>;
        <methods>;
}
-keepclassmembers class com.manjay.housebox.activity.CityListActivity$*{*;}

-keep class com.manjay.housebox.map.MapActivity$*{
        <fields>;
        <methods>;
}
-keepclassmembers class com.manjay.housebox.map.MapActivity$*{*;}


# /**如果项目用到jar的接口  此方不加，会有问题**/
 -keep interface com.kenai.jbosh.** {*; }
 -keep interface com.novell.sasl.client.** {*; }
 -keep interface de.measite.smack.** {*; }
 -keep interface org.** {*; }





 -keep class com.mabeijianxi.smallvideorecord2.jniinterface.** {*; }
 -keep class com.esay.ffmtool.** {*; }






