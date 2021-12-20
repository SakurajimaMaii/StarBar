<h1 align="center">StarBar</h1>

<p align="center">A starbar widget for android</p>

<p align="center">
<img src="https://img.shields.io/badge/compile%20sdk%20version-31-blue"/>
<img src="https://img.shields.io/badge/min%20sdk%20version-23-yellowgreen"/>
<img src="https://img.shields.io/badge/target%20sdk%20version-31-orange"/>
<img src="https://img.shields.io/badge/jdk%20version-11-%2300b894"/>
<img src="https://jitpack.io/v/SakurajimaMaii/StarBar.svg">
</p>

<div align="center">English | <a href="https://github.com/SakurajimaMaii/StarBar/blob/master/README_CN.md">简体中文</a></div>

## Preview

<div align="center"><img src="https://github.com/SakurajimaMaii/GStarBar/blob/master/resources/gstarbar.gif" width = "380" height = "800" alt="example.gif"/></div>

## How to

### 1. Add gradle

Add it in your root build.gradle at the end of repositories:

```groovy
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```

Add the dependency

```gradle
dependencies {
     implementation 'com.github.SakurajimaMaii:StarBar:0.0.5'
}
```

### 2. Set in XML

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

### Here is a Kotlin way

```kotlin
mStarbar.apply {
      getStarRating()
      setStarSelectedBitmap(R.drawable.ic_star_dark_blue_selected)
      setStarNormalBitmap(R.drawable.ic_star_dark_blue_normal)
      setStarMaxNumber(5)
      setStarBitMapSize(40,40)
      setStarIntervalWidth(20)
      setStarSelectMethod(StarBarSelectMethod.Sliding)
      try {
          setStarRating(3.6f)
      }catch (e: StarBarException){
          e.printStackTrace()
      }
  }
```

## Afterword

The detail about attributes or methods is very clear in code comment, so I won't explain it anymore. Please refer to the source code for details.
