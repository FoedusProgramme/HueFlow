package org.foedusprogramme.hueflow.colorapplier.implementation

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.os.Build
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.view.doOnNextLayout
import com.google.android.material.textfield.TextInputLayout
import org.foedusprogramme.hueflow.NAMESPACE_APP
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
import org.foedusprogramme.hueflow.colorapplier.factory.ViewColorApplierBuilder
import org.foedusprogramme.hueflow.colorapplier.factory.ViewColorAppliersFactory
import org.foedusprogramme.hueflow.palette.ColorToken

class TextInputLayoutColorAppliersFactory : ViewColorAppliersFactory<TextInputLayout> {
    override val viewClass = TextInputLayout::class.java

    override fun getMutableAttributes(view: TextInputLayout): List<Pair<String, String>> {
        return listOf(
            "boxBackgroundColor" to NAMESPACE_APP,
            "boxStrokeColor" to NAMESPACE_APP,
            "boxStrokeErrorColor" to NAMESPACE_APP,
            "hintTextColor" to NAMESPACE_APP,
            "startIconTint" to NAMESPACE_APP,
            "endIconTint" to NAMESPACE_APP,
            "errorTextColor" to NAMESPACE_APP,
            "helperTextColor" to NAMESPACE_APP,
            "counterTextColor" to NAMESPACE_APP,
            "counterOverflowTextColor" to NAMESPACE_APP,
            "prefixTextColor" to NAMESPACE_APP,
            "suffixTextColor" to NAMESPACE_APP,
            "cursorColor" to NAMESPACE_APP
        )
    }

    override fun build(
        view: TextInputLayout,
        attrToToken: Map<String, ColorToken>
    ): List<ColorApplier> {
        return ViewColorApplierBuilder(view).apply {
            attrToToken["boxBackgroundColor"]?.let { token ->
                attr("boxBackgroundColor", token) { color ->
                    view.boxBackgroundColor = color
                }
            }
            attrToToken["boxStrokeColor"]?.let { token ->
                attr("boxStrokeColor", token) { color ->
                    view.boxStrokeColor = color
                }
            }
            attrToToken["boxStrokeErrorColor"]?.let { token ->
                attr("boxStrokeErrorColor", token) { color ->
                    view.boxStrokeErrorColor = ColorStateList.valueOf(color)
                }
            }
            attrToToken["hintTextColor"]?.let { token ->
                attr("hintTextColor", token) { color ->
                    view.hintTextColor = ColorStateList.valueOf(color)
                }
            }
            attrToToken["startIconTint"]?.let { token ->
                attr("startIconTint", token) { color ->
                    view.setStartIconTintList(ColorStateList.valueOf(color))
                }
            }
            attrToToken["endIconTint"]?.let { token ->
                attr("endIconTint", token) { color ->
                    view.setEndIconTintList(ColorStateList.valueOf(color))
                }
            }
            attrToToken["errorTextColor"]?.let { token ->
                attr("errorTextColor", token) { color ->
                    view.setErrorTextColor(ColorStateList.valueOf(color))
                }
            }
            attrToToken["helperTextColor"]?.let { token ->
                attr("helperTextColor", token) { color ->
                    view.setHelperTextColor(ColorStateList.valueOf(color))
                }
            }
            attrToToken["counterTextColor"]?.let { token ->
                attr("counterTextColor", token) { color ->
                    view.counterTextColor = ColorStateList.valueOf(color)
                }
            }
            attrToToken["counterOverflowTextColor"]?.let { token ->
                attr("counterOverflowTextColor", token) { color ->
                    view.counterOverflowTextColor = ColorStateList.valueOf(color)
                }
            }
            attrToToken["prefixTextColor"]?.let { token ->
                attr("prefixTextColor", token) { color ->
                    view.setPrefixTextColor(ColorStateList.valueOf(color))
                }
            }
            attrToToken["suffixTextColor"]?.let { token ->
                attr("suffixTextColor", token) { color ->
                    view.setSuffixTextColor(ColorStateList.valueOf(color))
                }
            }
            attrToToken["cursorColor"]?.let { token ->
                attr("cursorColor", token) {color ->
                    Log.d("TAG", "cursorColor")
                    view.doOnNextLayout {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                            view.cursorColor = ColorStateList.valueOf(color)
                        } else {
                            view.editText?.setCursorColor(color)
                        }
                    }
                }
            }
        }.build()
    }

    fun EditText.setCursorColor(@ColorInt color: Int) {
        try {
            val fCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
            fCursorDrawableRes.isAccessible = true
            val drawableResId = fCursorDrawableRes.getInt(this)

            val drawables = arrayOf(
                ContextCompat.getDrawable(context, drawableResId)?.mutate()?.apply {
                    setColorFilter(color, PorterDuff.Mode.SRC_IN)
                },
                ContextCompat.getDrawable(context, drawableResId)?.mutate()?.apply {
                    setColorFilter(color, PorterDuff.Mode.SRC_IN)
                }
            )

            val fEditor = TextView::class.java.getDeclaredField("mEditor")
            fEditor.isAccessible = true
            val editor = fEditor.get(this)

            val fCursorDrawable = editor.javaClass.getDeclaredField("mCursorDrawable")
            fCursorDrawable.isAccessible = true
            fCursorDrawable.set(editor, drawables)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}
