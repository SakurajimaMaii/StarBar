package com.gcode.widget;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Vast Gui
 * @email sakurajimamai2020@qq.com
 */
public class StarBarSelectMethod {
    // Unable
    public static final int Unable = 0;
    // Click
    public static final int Click = 1;
    // Sliding
    public static final int Sliding = 2;

    @Retention(RetentionPolicy.SOURCE)
    @IntDef({
            Unable,Click,Sliding
    })
    public @interface SelectMethod { }
}