package com.gcode.widget

/**
 *作者:created by SakurajimaMaii on 2021/7/24 19:19
 *邮箱:sakurajimamai2020@qq.com
 */
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.gcode.gstarbar.R
import kotlin.math.max


/**
 * 显示星星评论数控件
 * Created by CaptionDeng on 2016/8/30.
 */
//@JvmOverloads constructor意在解决在java文件中调用GStarBar产生
//java.lang.NoSuchMethodException: com.gcode.widget.StarBarView.<init>的错误
class StarBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
):View(context, attrs, defStyle) {

    companion object {
        //星星水平排列
        const val HORIZONTAL = 0

        //星星垂直排列
        const val VERTICAL = 1

        const val viewTag = "StarBarView"
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

    //用于控件尺寸的设定
    //参考链接 https://blog.csdn.net/u011228356/article/details/44620263
    private val density = context.resources.displayMetrics.density

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        //绘制实心进度
        val solidStarNum = starRating.toInt()
        //绘制实心的起点位置
        var solidStartPoint = 0
        when (starOrientation) {
            HORIZONTAL -> for (i in 1..solidStarNum) {
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
        //多出的实心部分起点
        val extraSolidStarPoint = hollowStartPoint
        //虚心数量
        val hollowStarNum = starMaxNumber - solidStarNum
        when (starOrientation){
            HORIZONTAL-> for (j in 1..hollowStarNum) {
                canvas.drawBitmap(starNormalBitmap, hollowStartPoint.toFloat(), 0f, paint)
                hollowStartPoint += starSpaceWidth + starNormalBitmap.width
            }
                VERTICAL-> for (j in 1..hollowStarNum) {
                    canvas.drawBitmap(starNormalBitmap, 0f, hollowStartPoint.toFloat(), paint)
                    hollowStartPoint += starSpaceWidth + starNormalBitmap.width
                }
            }
        //多出的实心长度
        when(starOrientation){
            HORIZONTAL->{
                val extraSolidLength = ((starRating - solidStarNum) * starNormalBitmap.width).toInt()
                val rectSrc = Rect(0, 0, extraSolidLength, starNormalBitmap.height)
                val dstF = Rect(
                    extraSolidStarPoint,
                    0,
                    extraSolidStarPoint + extraSolidLength,
                    starNormalBitmap.height
                )
                canvas.drawBitmap(starSelectedBitmap, rectSrc, dstF, paint)
            }
            VERTICAL->{
                val extraSolidLength = ((starRating - solidStarNum) * starNormalBitmap.height).toInt()
                val rectSrc = Rect(0, 0, starNormalBitmap.width, extraSolidLength)
                val dstF = Rect(
                    0,
                    extraSolidStarPoint,
                    starNormalBitmap.width,
                    extraSolidStarPoint + extraSolidLength
                )
                canvas.drawBitmap(starSelectedBitmap, rectSrc, dstF, paint)
            }
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when(starSelectMethod){
            //无法进行操作
            StarBarSelectMethod.ProhibitedOperation.value->{
                return super.onTouchEvent(event)
            }
            StarBarSelectMethod.ClickOperation.value->{
                if(event.action == MotionEvent.ACTION_DOWN){
                    if (starOrientation == HORIZONTAL) {
                        val totalWidth = (starMaxNumber * (starWidth + starSpaceWidth)).toFloat()
                        if (event.x <= totalWidth) {
                            val newStarRating =
                                (event.x.toInt() / (starHeight + starSpaceWidth) + 1).toFloat()
                            setStarRating(newStarRating)
                        }
                    } else {
                        val totalHeight = (starMaxNumber * (starHeight + starSpaceWidth)).toFloat()
                        if (event.y <= totalHeight) {
                            val newStarRating = (event.y.toInt() / (starHeight + starSpaceWidth) + 1).toFloat()
                            setStarRating(newStarRating)
                        }
                    }
                }
                return super.onTouchEvent(event)
            }
            StarBarSelectMethod.SlidingOperation.value->{
                if (starOrientation == HORIZONTAL) {
                    val totalWidth = (starMaxNumber * (starWidth + starSpaceWidth)).toFloat()
                    if (event.x <= totalWidth) {
                        val newStarRating = event.x / (starWidth + starSpaceWidth) + 1
                        //保证至少选择一颗星星
                        Log.d(
                            viewTag,
                            "${starOrientation}选中的x横坐标${event.x},星星宽度加间隔${starWidth + starSpaceWidth},选中的星星控件数量$newStarRating"
                        )
                        setStarRating(max(newStarRating,1f))
                    }
                } else {
                    val totalHeight = (starMaxNumber * (starHeight + starSpaceWidth)).toFloat()
                    if (event.y <= totalHeight) {
                        val newStarRating = event.y / (starHeight + starSpaceWidth) + 1
                        Log.d(viewTag, "选中的星星控件数量$newStarRating")
                        setStarRating(max(newStarRating, 1f))
                    }
                }
                return true
            }
            else -> return super.onTouchEvent(event)
        }
    }

    /**
     * 设置星星选中数量
     * @param starRating Float
     * @throws StarBarIllegalParamException
     */
    @Throws(StarBarIllegalParamException::class)
    fun setStarRating(starRating: Float){
        when {
            starRating<1f -> {
                throw StarBarIllegalParamException("The least number of stars selected is one")
            }
            starRating.toInt()>starMaxNumber -> {
                throw StarBarIllegalParamException("The number of selected stars exceeds the maximum number limit")
            }
            else -> {
                this.starRating = starRating
                invalidate()
            }
        }
    }

    /**
     * 获取星星选中数量
     * @return Float
     */
    fun getStarRating() = starRating

    /**
     * 设置星星选中方式
     * @param methodBar StarBarSelectMethod
     */
    fun setStarSelectMethod(methodBar: StarBarSelectMethod){
        this.starSelectMethod = methodBar.value
    }

    /**
     * 获取星星选中方式
     * @return String
     */
    fun getStarSelectMethod() = when(starSelectMethod){
        StarBarSelectMethod.ProhibitedOperation.value->"ProhibitedOperation"
        StarBarSelectMethod.ClickOperation.value->"ClickOperation"
        StarBarSelectMethod.SlidingOperation.value->"SlidingOperation"
        else->""
    }

    /**
     * 设置想要的星星尺寸 注意这里的单位是dp
     * @param starWidth Int
     * @param starHeight Int
     * @throws StarBarIllegalParamException
     */
    @Throws(StarBarIllegalParamException::class)
    fun setStarBitMapSize(starWidth:Int,starHeight:Int){
        if(starWidth<0||starHeight<0){
            throw StarBarIllegalParamException("There is a problem with the set length and width values")
        }
        //将int转换为对应的dp
        this.starWidth = (starWidth*density).toInt()
        this.starHeight = (starHeight*density).toInt()
        starSelectedBitmap = getZoomBitmap(starSelectedBitmap)
        starNormalBitmap = getZoomBitmap(starNormalBitmap)
        invalidate()
    }

    /**
     * 获取星星尺寸 注意这里获取到的数值单位是dp
     * @return starSize
     */
    fun getStarBitMapSize() =
        StarSize((starWidth / density).toInt(), (starHeight / density).toInt())

    /**
     * 获取星星图片的宽度 单位dp
     * @return Int
     */
    fun getStarBitMapWidth() = (starWidth / density).toInt()

    /**
     * 获取星星图片的高度 单位dp
     * @return Int
     */
    fun getStarBitMapHeight() = (starWidth / density).toInt()

    /**
     * 设置星星之间的间距 单位是dp
     * @param starSpaceWidth Int
     * @throws StarBarIllegalParamException
     */
    @Throws(StarBarIllegalParamException::class)
    fun setStarSpaceWidth(starSpaceWidth: Int) {
        if (starSpaceWidth < 0) {
            throw StarBarIllegalParamException("The star spacing setting must be a non-negative number")
        }
        this.starSpaceWidth = (starSpaceWidth*density).toInt()
        invalidate()
    }

    /**
     * 获取星星之间的间距 单位是dp
     * @return Int
     */
    fun getStarSpaceWidth() = (starSpaceWidth/density).toInt()

    /**
     * 设置星星数量
     * @param starMaxNumber Int
     * @throws StarBarIllegalParamException
     */
    @Throws(StarBarIllegalParamException::class)
    fun setStarMaxNumber(starMaxNumber: Int) {
        if(starMaxNumber<0){
            throw StarBarIllegalParamException("The maximum number of stars should not be negative")
        }
        this.starMaxNumber = starMaxNumber
        invalidate()
    }

    /**
     * 获取星星数量
     * @return Int
     */
    fun getStarMaxNumber() = starMaxNumber

    /**
     * 设置星星选中后的图片
     * @param bitmap Bitmap
     */
    fun setStarSelectedBitmap(bitmap: Bitmap){
        starSelectedBitmap = getZoomBitmap(bitmap)
    }

    /**
     * Drawable资源id来设置星星选中图片
     * @param drawableId Int
     */
    @Throws(StarBarBitmapException::class)
    fun setStarSelectedBitmap(drawableId: Int){
        if(BitmapFactory.decodeResource(context.resources, drawableId)==null){
            throw StarBarBitmapException("Drawable resource conversion BitMap failed")
        }else{
            starSelectedBitmap = getZoomBitmap(BitmapFactory.decodeResource(context.resources, drawableId))
        }
    }

    /**
     * 获取星星选中的图片
     * @return Bitmap
     */
    fun getStarSelectedBitmap() = starSelectedBitmap

    /**
     * 设置星星未选中后的图片
     * @param bitmap Bitmap
     */
    fun setStarNormalBitmap(bitmap: Bitmap){
        starNormalBitmap = getZoomBitmap(bitmap)
    }

    /**
     * Drawable资源id来设置星星为选中图片
     * @param drawableId Int
     */
    @Throws(StarBarBitmapException::class)
    fun setStarNormalBitmap(drawableId: Int){
        if(BitmapFactory.decodeResource(context.resources, drawableId)==null){
            throw StarBarBitmapException("Drawable resource conversion BitMap failed")
        }else{
            starNormalBitmap = getZoomBitmap(BitmapFactory.decodeResource(context.resources, drawableId))
        }
    }

    /**
     * 获取星星未选中的图片
     * @return Bitmap
     */
    fun getStarNormalBitmap() = starNormalBitmap

    /**
     * 获取缩放图片
     * @param bitmap
     * @return
     */
    private fun getZoomBitmap(bitmap: Bitmap): Bitmap {
        if (starWidth == 0 || starHeight == 0) {
            return bitmap
        }
        // 计算缩放比例 想要的大小/图片原本的宽高
        val scaleWidth = starWidth.toFloat() / bitmap.width
        val scaleHeight = starHeight.toFloat() / bitmap.height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        when (starOrientation) {
            //判断是横向还是纵向，测量长度
            HORIZONTAL -> setMeasuredDimension(
                measureLong(widthMeasureSpec),
                measureShort(heightMeasureSpec)
            )
            VERTICAL -> setMeasuredDimension(
                measureShort(widthMeasureSpec),
                measureLong(heightMeasureSpec)
            )
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

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.GStarBarView, defStyle, 0)
        starSpaceWidth = a.getDimensionPixelSize(R.styleable.GStarBarView_star_space_width, 0)
        starWidth = a.getDimensionPixelSize(R.styleable.GStarBarView_star_width, 0)
        starHeight = a.getDimensionPixelSize(R.styleable.GStarBarView_star_height, 0)
        starMaxNumber = a.getInt(R.styleable.GStarBarView_star_max, 0)
        starRating = a.getFloat(R.styleable.GStarBarView_star_rating, 0f)
        //这里防止设定的资源解析失败而返回null,故给了一个默认值
        starSelectedBitmap = if(BitmapFactory.decodeResource(context.resources, a.getResourceId(R.styleable.GStarBarView_star_selected, 0))==null) {
            getZoomBitmap(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_star_yellow_selected
                )
            )
        }else{
            getZoomBitmap(BitmapFactory.decodeResource(context.resources, a.getResourceId(R.styleable.GStarBarView_star_selected, 0)))
        }
        starNormalBitmap = if(BitmapFactory.decodeResource(context.resources, a.getResourceId(R.styleable.GStarBarView_star_normal, 0))==null) {
            getZoomBitmap(
                BitmapFactory.decodeResource(
                    context.resources,
                    R.mipmap.ic_star_yellow_normal
                )
            )
        }else{
            getZoomBitmap(BitmapFactory.decodeResource(context.resources, a.getResourceId(R.styleable.GStarBarView_star_normal, 0)))
        }
        starOrientation = a.getInt(R.styleable.GStarBarView_star_orientation, HORIZONTAL)
        starSelectMethod = a.getInt(R.styleable.GStarBarView_star_select_method,0)
        a.recycle()
    }
}