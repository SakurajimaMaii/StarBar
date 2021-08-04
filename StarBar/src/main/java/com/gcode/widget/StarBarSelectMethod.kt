package com.gcode.widget

/**
 *作者:created by HP on 2021/8/1 17:17
 *邮箱:sakurajimamai2020@qq.com
 */
enum class StarBarSelectMethod(val value:Int) {
    //无法进行点击修改
    ProhibitedOperation(0),
    //点击选择星星数量
    ClickOperation(1),
    //滑动选择星星数量
    SlidingOperation(2)
}