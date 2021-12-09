<h1 align="center">StarBar</h1>

<p align="center">一款自定义安卓星星评分控件</p>

<p align="center">
<img src="https://img.shields.io/badge/compile%20sdk%20version-31-blue"/>
<img src="https://img.shields.io/badge/min%20sdk%20version-23-yellowgreen"/>
<img src="https://img.shields.io/badge/target%20sdk%20version-31-orange"/>
<img src="https://img.shields.io/badge/jdk%20version-11-%2300b894"/>
<img src="https://jitpack.io/v/SakurajimaMaii/StarBar.svg">
</p>

<div align="center"><a href="https://github.com/SakurajimaMaii/StarBar/blob/master/README.md">English</a> | 简体中文 </div>

## 💫 特性

- 😀 支持三种操作 `Unable 不可操作` `Click 整数点击` `Sliding 滑动`
- 😆 支持两种布局方式 `水平放置` `垂直放置`
- 😚 支持自定义星星选中和未选中图片
- 😎 支持自定义星星数量
- 😯 支持自定义星星图片尺寸

<div align="center"><img src="https://img-blog.csdnimg.cn/7e5c8446968646e5876ffc39213f6eee.gif" width = "40%" alt="图片名称" /></div>

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
    implementation 'com.github.SakurajimaMaii:StarBar:0.0.5'
}
```

### 2. xml布局

```xml
<com.gcode.widget.StarBarView
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

该项目相关源码地址[SakurajimaMaii/StarBar](https://github.com/SakurajimaMaii/StarBar),欢迎**fork**和**star**,如果你对该项目存在疑问,可以在**issue**内提出疑问方便我解答。
> 该项目开发参考GHdeng/StarBarExample项目,原项目地址[GHdeng/StarBarExample](https://github.com/GHdeng/StarBarExample)

关于属性一类的或者方法的问题代码里面写的都很详细了,就不再解释了,详情参考源码。