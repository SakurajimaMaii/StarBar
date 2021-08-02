package com.gcode.starbarproj;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

import com.gcode.gstarbar.GStarBarView;
import com.gcode.gstarbar.GStarBitmapSizeException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GStarBarView mStarbar = (GStarBarView) findViewById(R.id.sbv_starbar);

        Button button = (Button) findViewById(R.id.button_2);

        button.setWidth(40);
        button.setHeight(40);

        //拿到当前星星数量
        mStarbar.getStarRating();
//        try {
//            mStarbar.setStarRating(10f);
//        } catch (GStarRatingNumberException e) {
//            e.printStackTrace();
//        }

        try {
            mStarbar.setStarBitMapSize(40,40);
        } catch (GStarBitmapSizeException e) {
            e.printStackTrace();
        }

        GStarBarView.StarSize size = mStarbar.getStarBitMapSize();
    }
}
