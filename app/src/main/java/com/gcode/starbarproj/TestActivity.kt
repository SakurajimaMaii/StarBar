package com.gcode.starbarproj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.gcode.widget.GStarBarView
import com.gcode.widget.GStarException
import com.gcode.widget.GStarSelectMethod

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val mStarbar = findViewById<View>(R.id.sbv_starbar_2) as GStarBarView

        //拿到当前星星数量

        //拿到当前星星数量
        mStarbar.apply {
            getStarRating()
            setStarSelectedBitmap(R.drawable.ic_star_dark_blue_selected)
            setStarNormalBitmap(R.drawable.ic_star_dark_blue_normal)
            setStarMaxNumber(5)
            setStarBitMapSize(40,40)
            setStarSpaceWidth(20)
            setStarSelectMethod(GStarSelectMethod.SlidingOperation)
            try {
                setStarRating(3.6f)
            }catch (e: GStarException){
                e.printStackTrace()
            }
        }

        val (width,height) = mStarbar.getStarBitMapSize()
        Log.d("TestActivity","$width $height ${mStarbar.getStarSelectMethod()}")
    }
}