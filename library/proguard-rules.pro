# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /Users/niyueming/Library/Android/sdk/tools/proguard/proguard-android.txt
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

-dontskipnonpubliclibraryclassmembers
-dontshrink
-dontoptimize
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-optimizationpasses 5
-dontusemixedcaseclassnames
-keepattributes Exceptions,InnerClasses,Signature,*Annotation*,SourceFile,LineNumberTable
-dontpreverify
-verbose

-keep class * extends android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keep class * extends android.os.Parcelable {
    <fields>;
    <methods>;
}

-keep class * extends java.io.Serializable {
    <fields>;
    <methods>;
}

-keep class **.R {
    <fields>;
    <methods>;
}

-keep public class **.R$* {
    public static final int *;
}

# Also keep - Enumerations. Keep the special static methods that are required in
# enumeration classes.
-keepclassmembers enum  * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}

# Keep names - Native method names. Keep all native class/method names.
-keepclasseswithmembers,allowshrinking class * {
    native <methods>;
}

#okhttp
-dontwarn okhttp3.**
-keep class okhttp3.**{*;}


#okio
-dontwarn okio.**
-keep class okio.**{*;}