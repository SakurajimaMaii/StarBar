/*
 * Copyright VastGui 2022
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.gcode.starbar

import androidx.annotation.IntDef

// Author: Vast Gui
// Email: sakurajimamai2020@qq.com
// Date: 2022/8/31 12:43
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