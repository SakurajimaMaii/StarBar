package com.gcode.starbar

// Author: Vast Gui
// Email: sakurajimamai2020@qq.com
// Date: 2021/7/28
// Description:
// Documentation:

/**
 * @property message String?
 * @constructor
 */
open class StarBarException(override val message: String?):Throwable()

/**
 * Will be thrown when the param is illegal.
 * @property message String?
 * @constructor
 */
class StarBarIllegalParamException(override val message: String?): StarBarException(message)

/**
 * Will be thrown when starbar bitmap has wrong.
 * @property message String?
 * @constructor
 */
class StarBarBitmapException(override val message: String?): StarBarException(message)