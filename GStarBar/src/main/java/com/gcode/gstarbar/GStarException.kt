package com.gcode.gstarbar

/**
 *作者:created by HP on 2021/8/1 17:53
 *邮箱:sakurajimamai2020@qq.com
 */
/**
 * 所有Exception父类,方便catch
 * @property message String?
 * @constructor
 */
open class GStarException(override val message: String?):Throwable()

/**
 * 星星数量错误
 * @property message String?
 * @constructor
 */
class GStarNumberException(override val message: String?):GStarException(message)

/**
 * 星星图片错误
 * @property message String?
 * @constructor
 */
class GStarBitmapException(override val message: String?):GStarException(message)

/**
 * 星星相关尺寸设置的错误
 * @property message String?
 * @constructor
 */
class GStarBitmapSizeException(override val message: String?):GStarException(message)