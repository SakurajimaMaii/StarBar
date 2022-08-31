package com.gcode.starbar

import androidx.annotation.IntDef

// Author: Vast Gui
// Email: sakurajimamai2020@qq.com
// Date: 2021/7/28
// Description:
// Documentation:

object StarBarSelectMethod {
    // Unable
    const val Unable = 0

    // Click
    const val Click = 1

    // Sliding
    const val Sliding = 2

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(Unable, Click, Sliding)
    annotation class SelectMethod
}