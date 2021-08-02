package com.gcode.starbarproj

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.gcode.gstarbar.GStarBarView

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val mStarbar = findViewById<View>(R.id.sbv_starbar_2) as GStarBarView

        //拿到当前星星数量

        //拿到当前星星数量
        mStarbar.getStarRating()
        mStarbar.setStarRating(10f)


        val (width,height) = mStarbar.getStarBitMapSize()
    }
}