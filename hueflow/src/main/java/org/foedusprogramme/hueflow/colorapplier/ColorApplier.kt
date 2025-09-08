package org.foedusprogramme.hueflow.colorapplier

import androidx.annotation.ColorInt
import org.foedusprogramme.hueflow.palette.ColorToken

interface ColorApplier {
    val viewId: Int
    val attribute: String
    val token: ColorToken
    fun applyColor(@ColorInt color: Int)
}