package com.gcode.gstarbar

/**
 *作者:created by HP on 2021/7/24 19:19
 *邮箱:sakurajimamai2020@qq.com
 */
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


/**
 * 显示星星评论数控件
 * Created by CaptionDeng on 2016/8/30.
 */

//@JvmOverloads constructor is intended to solve the error of
//java.lang.NoSuchMethodException: com.gcode.gstarbar.GStarBarView.<init> when calling GStarBar in the java file
class GStarBarView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet?,
    defStyle: Int = 0
) :
    View(context, attrs, defStyle) {
    //实心图片
    private val mSolidBitmap: Bitmap?

    //空心图片
    private val mHollowBitmap: Bitmap?

    //最大的数量
    private var starMaxNumber: Int
    private var starRating: Float
    private val paint: Paint = Paint()
    private val mSpaceWidth //星星间隔
            : Int
    private val mStarWidth //星星宽度
            : Int
    private val mStarHeight //星星高度
            : Int
    private var isIndicator //是否是一个指示器（用户无法进行更改）
            : Boolean
    private val mOrientation: Int

    //Eliminate the error of Avoid object allocations during draw/layout operations (preallocate and reuse instead)
    //caused by declaring rectSrc and dstF objects
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        if (mHollowBitmap == null || mSolidBitmap == null) {
            return
        }
        //绘制实心进度
        val solidStarNum = starRating.toInt()
        //绘制实心的起点位置
        var solidStartPoint = 0
        if (mOrientation == HORIZONTAL) for (i in 1..solidStarNum) {
            canvas.drawBitmap(mSolidBitmap, solidStartPoint.toFloat(), 0f, paint)
            solidStartPoint += mSpaceWidth + mSolidBitmap.width
        } else for (i in 1..solidStarNum) {
            canvas.drawBitmap(mSolidBitmap, 0f, solidStartPoint.toFloat(), paint)
            solidStartPoint += mSpaceWidth + mSolidBitmap.height
        }
        //虚心开始位置
        var hollowStartPoint = solidStartPoint
        //多出的实心部分起点
        val extraSolidStarPoint = hollowStartPoint
        //虚心数量
        val hollowStarNum = starMaxNumber - solidStarNum
        if (mOrientation == HORIZONTAL) for (j in 1..hollowStarNum) {
            canvas.drawBitmap(mHollowBitmap, hollowStartPoint.toFloat(), 0f, paint)
            hollowStartPoint += mSpaceWidth + mHollowBitmap.width
        } else for (j in 1..hollowStarNum) {
            canvas.drawBitmap(mHollowBitmap, 0f, hollowStartPoint.toFloat(), paint)
            hollowStartPoint += mSpaceWidth + mHollowBitmap.width
        }
        //多出的实心长度
        val extraSolidLength = ((starRating - solidStarNum) * mHollowBitmap.width).toInt()
        val rectSrc = Rect(0, 0, extraSolidLength, mHollowBitmap.height)
        val dstF = Rect(
            extraSolidStarPoint,
            0,
            extraSolidStarPoint + extraSolidLength,
            mHollowBitmap.height
        )
        canvas.drawBitmap(mSolidBitmap, rectSrc, dstF, paint)
    }

    override fun performClick(): Boolean {
        return super.performClick()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (!isIndicator) {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> if (mOrientation == HORIZONTAL) {
                    val totalWidth = (starMaxNumber * (mStarWidth + mSpaceWidth)).toFloat()
                    if (event.x <= totalWidth) {
                        val newStarRating =
                            (event.x.toInt() / (mStarWidth + mSpaceWidth) + 1).toFloat()
                        setStarRating(newStarRating)
                    }
                } else {
                    val totalHeight = (starMaxNumber * (mStarHeight + mSpaceWidth)).toFloat()
                    if (event.y <= totalHeight) {
                        val newStarRating =
                            (event.y.toInt() / (mStarHeight + mSpaceWidth) + 1).toFloat()
                        setStarRating(newStarRating)
                    }
                }
                MotionEvent.ACTION_MOVE -> {
                }
                MotionEvent.ACTION_UP -> {
                }
                MotionEvent.ACTION_CANCEL -> {
                }
            }
            performClick()
        }
        return super.onTouchEvent(event)
    }

    /**
     * 设置星星的进度
     *
     * @param starRating
     */
    fun setStarRating(starRating: Float) {
        this.starRating = starRating
        invalidate()
    }

    fun getStarRating(): Float {
        return starRating
    }

    /**
     * 获取缩放图片
     * @param bitmap
     * @return
     */
    fun getZoomBitmap(bitmap: Bitmap): Bitmap {
        if (mStarWidth == 0 || mStarHeight == 0) {
            return bitmap
        }
        // 获得图片的宽高
        val width = bitmap.width
        val height = bitmap.height

        // 设置想要的大小
        val newWidth = mStarWidth
        val newHeight = mStarHeight
        // 计算缩放比例
        val scaleWidth = newWidth.toFloat() / width
        val scaleHeight = newHeight.toFloat() / height
        // 取得想要缩放的matrix参数
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        // 得到新的图片
        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mOrientation == HORIZONTAL) {
            //判断是横向还是纵向，测量长度
            setMeasuredDimension(measureLong(widthMeasureSpec), measureShort(heightMeasureSpec))
        } else {
            setMeasuredDimension(measureShort(widthMeasureSpec), measureLong(heightMeasureSpec))
        }
    }

    private fun measureLong(measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = (paddingLeft + paddingRight + (mSpaceWidth + mStarWidth) * starMaxNumber)
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
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
            result = (mStarHeight + paddingTop + paddingBottom)
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize)
            }
        }
        return result
    }

    fun getStarMaxNumber(): Int {
        return starMaxNumber
    }

    fun setStarMaxNumber(starMaxNumber: Int) {
        this.starMaxNumber = starMaxNumber
        //利用invalidate()；刷新界面
        invalidate()
    }

    companion object {
        //星星水平排列
        const val HORIZONTAL = 0

        //星星垂直排列
        const val VERTICAL = 1
    }

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.GStarBarView, defStyle, 0)
        mSpaceWidth = a.getDimensionPixelSize(R.styleable.GStarBarView_space_width, 0)
        mStarWidth = a.getDimensionPixelSize(R.styleable.GStarBarView_star_width, 0)
        mStarHeight = a.getDimensionPixelSize(R.styleable.GStarBarView_star_height, 0)
        starMaxNumber = a.getInt(R.styleable.GStarBarView_star_max, 0)
        starRating = a.getFloat(R.styleable.GStarBarView_star_rating, 0f)
        mSolidBitmap = getZoomBitmap(
            BitmapFactory.decodeResource(
                context.resources,
                a.getResourceId(R.styleable.GStarBarView_star_solid, 0)
            )
        )
        mHollowBitmap = getZoomBitmap(
            BitmapFactory.decodeResource(
                context.resources,
                a.getResourceId(R.styleable.GStarBarView_star_hollow, 0)
            )
        )
        mOrientation = a.getInt(R.styleable.GStarBarView_star_orientation, HORIZONTAL)
        isIndicator = a.getBoolean(R.styleable.GStarBarView_star_isIndicator, false)
        a.recycle()
    }
}