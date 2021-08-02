# GStarBar

## 前言

该项目开发参考GHdeng/StarBarExample项目,原项目地址[GHdeng/StarBarExample](https://github.com/GHdeng/StarBarExample)

![current version](https://jitpack.io/v/SakurajimaMaii/GStarBar.svg) 
![jdk version](https://img.shields.io/badge/jdk%20version-11-%23c0392b)

在开发电商项目中经常都会遇到一些星级评分控件的需求，有需求就必然有开发。废话不多说，没图没真相，上图：
![image](https://raw.githubusercontent.com/GHdeng/StarBarExample/master/resources/StarBarExample.gif)

## 确定需求：

* 可以控制星星之间的边距
* 自定义选中图片和未选中图片,可以设置**Drawable**对象和**BitMap**对象
* 摆放纵向或者横向
* 可选择选中数量

## 基本绘制流程：

### 1. 自定义属性

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <declare-styleable name="GStarBarView">
        <!--设置星星间的间隔-->
        <attr name="star_space_width" format="dimension" />
        <!--星星宽度-->
        <attr name="star_width" format="dimension" />
        <!--星星高度-->
        <attr name="star_height" format="dimension" />
        <!--最大数量-->
        <attr name="star_max" format="integer" />
        <!--选中数量-->
        <attr name="star_rating" format="float" />
        <!--未选中图片-->
        <attr name="star_normal" format="reference" />
        <!--选中图片-->
        <attr name="star_selected" format="reference" />
        <!--是否可以滑动改变选中数量-->
        <attr name="star_select_method" format="enum">
            <enum name="star_prohibited_operation" value="0" />
            <enum name="star_click_operation" value="1" />
            <enum name="star_sliding_operation" value="2" />
        </attr>
        <!--排列方向-->
        <attr name="star_orientation" format="enum">
            <enum name="star_vertical" value="1" />
            <enum name="star_horizontal" value="0" />
        </attr>
    </declare-styleable>
</resources>
```

### 2. 构造函数中获取自定义属性值

```kotlin
companion object {
    //星星水平排列
    const val HORIZONTAL = 0

    //星星垂直排列
    const val VERTICAL = 1

    const val viewTag = "GStarBarView"
}

data class StarSize(val width:Int,val height:Int)

//星星图片
private var starSelectedBitmap: Bitmap //实心图片
private var starNormalBitmap: Bitmap //空心图片

//数值为dp转换为int后的数值
private var starSpaceWidth: Int//星星间隔
private var starWidth: Int //星星宽度
private var starHeight: Int //星星高度

private var starMaxNumber: Int //最大的数量
private var starRating: Float //选中的数量
private val paint: Paint = Paint()
private var starSelectMethod: Int//星星选择方式
private val starOrientation: Int //星星排列方向

init {
    val a = context.obtainStyledAttributes(attrs, R.styleable.GStarBarView, defStyle, 0)
    starSpaceWidth = a.getDimensionPixelSize(R.styleable.GStarBarView_star_space_width, 0)
    starWidth = a.getDimensionPixelSize(R.styleable.GStarBarView_star_width, 0)
    starHeight = a.getDimensionPixelSize(R.styleable.GStarBarView_star_height, 0)
    starMaxNumber = a.getInt(R.styleable.GStarBarView_star_max, 0)
    starRating = a.getFloat(R.styleable.GStarBarView_star_rating, 0f)
    //这里防止设定的资源解析失败而返回null,故给了一个默认值
    starSelectedBitmap = if(BitmapFactory.decodeResource(context.resources, a.getResourceId(R.styleable.GStarBarView_star_selected, 0))==null){
        getZoomBitmap(BitmapFactory.decodeResource(context.resources,R.mipmap.ic_star_yellow_selected))
    }else{
        getZoomBitmap(BitmapFactory.decodeResource(context.resources, a.getResourceId(R.styleable.GStarBarView_star_selected, 0)))
    }
    starNormalBitmap = if(BitmapFactory.decodeResource(context.resources, a.getResourceId(R.styleable.GStarBarView_star_normal, 0))==null){
        getZoomBitmap(BitmapFactory.decodeResource(context.resources,R.mipmap.ic_star_yellow_normal))
    }else{
        getZoomBitmap(BitmapFactory.decodeResource(context.resources, a.getResourceId(R.styleable.GStarBarView_star_normal, 0)))
    }
    starOrientation = a.getInt(R.styleable.GStarBarView_star_orientation, HORIZONTAL)
    starSelectMethod = a.getInt(R.styleable.GStarBarView_star_select_method,0)
    a.recycle()
}
```

### 4. onMeasure函数测量子控件大小，然后设置当前控件大小

```kotlin
override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
    when (starOrientation) {
        //判断是横向还是纵向，测量长度
        HORIZONTAL->setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec))
        VERTICAL->setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec))
    }
}

private fun measureLong(measureSpec: Int): Int {
    var result: Int
    val specMode = MeasureSpec.getMode(measureSpec)
    val specSize = MeasureSpec.getSize(measureSpec)
    if (specMode == MeasureSpec.EXACTLY) {
        result = specSize
    } else {
        result = (paddingLeft + paddingRight + (starSpaceWidth + starWidth) * starMaxNumber)
        if (specMode == MeasureSpec.AT_MOST) {
            result = result.coerceAtMost(specSize)
        }
    }
    return result
}

private fun measureShort(measureSpec: Int): Int {
    var result: Int
    val specMode = MeasureSpec.getMode(measureSpec)
    val specSize = MeasureSpec.getSize(measureSpec)
    if (specMode == MeasureSpec.EXACTLY) {
        result = specSize
    } else {
        result = (starHeight + paddingTop + paddingBottom)
        if (specMode == MeasureSpec.AT_MOST) {
            result = result.coerceAtMost(specSize)
        }
    }
    return result
}
```

### 5. 确定好数据后开始描绘bitmap到Canvas上

```kotlin
override fun onDraw(canvas: Canvas) {
    //绘制实心进度
    val solidStarNum = starRating.toInt()
    //绘制实心的起点位置
    var solidStartPoint = 0
    when(starOrientation){
        HORIZONTAL-> for (i in 1..solidStarNum) {
            canvas.drawBitmap(starSelectedBitmap, solidStartPoint.toFloat(), 0f, paint)
            solidStartPoint += starSpaceWidth + starSelectedBitmap.width
        }
        VERTICAL-> for (i in 1..solidStarNum) {
            canvas.drawBitmap(starSelectedBitmap, 0f, solidStartPoint.toFloat(), paint)
            solidStartPoint += starSpaceWidth + starSelectedBitmap.height
        }
    }
    //虚心开始位置
    var hollowStartPoint = solidStartPoint
    when(starOrientation){
        HORIZONTAL-> for (i in 1..(starMaxNumber-solidStarNum)) {
            canvas.drawBitmap(starNormalBitmap, hollowStartPoint.toFloat(), 0f, paint)
            hollowStartPoint += starSpaceWidth + starNormalBitmap.width
        }
        VERTICAL-> for (i in 1..(starMaxNumber-solidStarNum)) {
            canvas.drawBitmap(starNormalBitmap, 0f, hollowStartPoint.toFloat(), paint)
            hollowStartPoint += starSpaceWidth + starNormalBitmap.width
        }
    }
}
```

### 6. 最后通过onTouchEvent方法去监听事件

```kotlin
@SuppressLint("ClickableViewAccessibility")
override fun onTouchEvent(event: MotionEvent): Boolean {
    when(starSelectMethod){
        //无法进行操作
        GStarSelectMethod.ProhibitedOperation.value->{
            return super.onTouchEvent(event)
        }
        GStarSelectMethod.ClickOperation.value->{
            if(event.action == MotionEvent.ACTION_DOWN){
                if (starOrientation == HORIZONTAL) {
                    val totalWidth = (starMaxNumber * (starWidth + starSpaceWidth)).toFloat()
                    if (event.x <= totalWidth) {
                        val newStarRating = (event.x.toInt() / (starWidth + starSpaceWidth) + 1).toFloat()
                        Log.d(viewTag,"选中的星星控件数量$newStarRating")
                        setStarRating(newStarRating)
                    }
                } else {
                    val totalHeight = (starMaxNumber * (starHeight + starSpaceWidth)).toFloat()
                    if (event.y <= totalHeight) {
                        val newStarRating = (event.y.toInt() / (starHeight + starSpaceWidth) + 1).toFloat()
                        Log.d(viewTag,"选中的星星控件数量$newStarRating")
                        setStarRating(newStarRating)
                    }
                }
            }
            return super.onTouchEvent(event)
        }
        GStarSelectMethod.SlidingOperation.value->{
            if (starOrientation == HORIZONTAL) {
                val totalWidth = (starMaxNumber * (starWidth + starSpaceWidth)).toFloat()
                if (event.x <= totalWidth) {
                    val newStarRating = (event.x.toInt() / (starWidth + starSpaceWidth)+1).toFloat()
                    //保证至少选择一颗星星
                    setStarRating(max(newStarRating,1f))
                }
            } else {
                val totalHeight = (starMaxNumber * (starHeight + starSpaceWidth)).toFloat()
                if (event.y <= totalHeight) {
                    val newStarRating = (event.y.toInt() / (starHeight + starSpaceWidth)+1).toFloat()
                    setStarRating(max(newStarRating,1f))
                }
            }
            return true
        }
        else -> return super.onTouchEvent(event)
    }
}
```

## 如何使用

### xml布局

```xml
<com.gcode.gstarbar.GStarBarView
    android:id="@+id/sbv_starbar"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:layout_marginTop="66dp"
    app:star_height="25dp"
    app:star_max="5"
    app:star_normal="@mipmap/ic_star_yellow_normal"
    app:star_orientation="star_horizontal"
    app:star_rating="2"
    app:star_select_method="star_sliding_operation"
    app:star_selected="@mipmap/ic_star_yellow_selected"
    app:star_space_width="1dp"
    app:star_width="25dp" />
```

### 代码添加

```kotlin
//拿到当前星星数量
mStarbar.getStarRating();
```
