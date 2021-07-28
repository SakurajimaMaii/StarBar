package com.caption.starbarexample;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.gcode.gstarbar.GStarBarView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GStarBarView mStarbar = (GStarBarView) findViewById(R.id.sbv_starbar);

        //拿到当前星星数量
        mStarbar.getStarRating();
    }
}
