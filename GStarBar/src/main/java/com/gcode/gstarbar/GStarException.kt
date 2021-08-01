package com.gcode.gstarbar

/**
 *作者:created by HP on 2021/8/1 17:53
 *邮箱:sakurajimamai2020@qq.com
 */

/**
 * 星星选择数量错误
 * @property message String?
 * @constructor
 */
class GStarRatingNumberException(override val message: String?):Throwable()

/**
 * 星星图片错误
 * @property message String?
 * @constructor
 */
class GStarBitmapException(override val message: String?):Throwable()

/**
 * 星星相关尺寸设置的错误
 * @property message String?
 * @constructor
 */
class GStarBitmapSizeException(override val message: String?):Throwable()