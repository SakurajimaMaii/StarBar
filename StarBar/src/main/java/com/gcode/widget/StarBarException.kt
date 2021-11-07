package com.gcode.widget

/**
 * @author Vast Gui
 */

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