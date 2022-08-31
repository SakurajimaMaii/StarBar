package com.gcode.starbarproj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.gcode.starbar.StarBarException
import com.gcode.starbar.StarBar
import com.gcode.starbar.StarBarSelectMethod

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val mStarbar = findViewById<View>(R.id.sbv_starbar_2) as StarBar

        //拿到当前星星数量

        //拿到当前星星数量
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

        val (width,height) = mStarbar.getStarBitMapSize()
        Log.d("TestActivity","$width $height ${mStarbar.starSelectMethod}")
    }
}