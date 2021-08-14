# StarBar

## 前言

> 该项目开发参考GHdeng/StarBarExample项目,原项目地址[GHdeng/StarBarExample](https://github.com/GHdeng/StarBarExample)

## 项目最新版本以及对应的jdk 版本

![current version](https://jitpack.io/v/SakurajimaMaii/StarBar.svg) 
![jdk version](https://img.shields.io/badge/jdk%20version-11-%23c0392b)

## 项目效果图

<img src="https://github.com/SakurajimaMaii/GStarBar/blob/master/resources/gstarbar.gif" width = "380" height = "800" alt="图片名称" align=center />

## 如何使用

### 1. 添加依赖

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

```groovy
dependencies {
    'com.github.SakurajimaMaii:StarBar:dev-2.1.0'
}
```

### 2. xml布局

```xml
<com.gcode.widget.StarBar
    android:id="@+id/sbv_starbar_2"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    app:star_space_width="1dp"
    app:star_height="25dp"
    app:star_normal="@mipmap/ic_star_yellow_normal"
    app:star_select_method="star_sliding_operation"
    app:star_max="5"
    app:star_orientation="star_horizontal"
    app:star_rating="2.5"
    app:star_selected="@mipmap/ic_star_yellow_selected"
    app:star_width="25dp"
    android:layout_marginTop="66dp" />
```

### 3. 代码使用

```kotlin
mStarbar.apply {
    getStarRating()
    setStarSelectedBitmap(R.drawable.ic_star_dark_blue_selected)
    setStarNormalBitmap(R.drawable.ic_star_dark_blue_normal)
    setStarMaxNumber(5)
    setStarBitMapSize(40,40)
    setStarSpaceWidth(20)
    setStarSelectMethod(StarBarSelectMethod.SlidingOperation)
    try {
        setStarRating(3.6f)
    }catch (e: StarBarException){
        e.printStackTrace()
    }
}
```

## 后话

关于属性一类的或者方法的问题代码里面写的都很详细了,就不再解释了,详情参考源码
