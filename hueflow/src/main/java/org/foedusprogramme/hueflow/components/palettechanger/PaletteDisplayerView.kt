package org.foedusprogramme.hueflow.components.palettechanger

import android.animation.ValueAnimator
import android.app.Activity
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import org.foedusprogramme.hueflow.ANIMATION_LENGTH
import org.foedusprogramme.hueflow.R
import org.foedusprogramme.hueflow.colorapplier.ColorApplier
import org.foedusprogramme.hueflow.colorapplier.ColorRegistry
import org.foedusprogramme.hueflow.core.HueFlow
import org.foedusprogramme.hueflow.equalsByValue
import org.foedusprogramme.hueflow.palette.ColorPalette
import org.foedusprogramme.hueflow.palette.ColorToken

class PaletteDisplayerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
) : View(context, attrs) {

    private val defaultSize = resources.getDimensionPixelSize(R.dimen.palette_displayer_size)
    private val cornerRadiusInner = resources.getDimensionPixelSize(R.dimen.palette_displayer_inner_radius).toFloat()
    private val strokeRadius = resources.getDimensionPixelSize(R.dimen.palette_displayer_stroke_width).toFloat()
    private val paletteRadius = resources.getDimensionPixelSize(R.dimen.palette_displayer_display_radius).toFloat()

    private val borderPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.STROKE
        strokeWidth = strokeRadius
    }

    private val containerPaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
        style = Paint.Style.FILL
    }

    private val palettePaint = Paint().apply {
        flags = Paint.ANTI_ALIAS_FLAG
    }

    private var palette = HueFlow.currentPalette
    private var borderTransform = 0F

    init {
        setOnClickListener {
            if (!HueFlow.currentPalette.equalsByValue(palette)) {
                HueFlow.changePalette(palette)
            }
        }
    }

    private var borderAnimator: ValueAnimator? = null

    fun setActiveBorderAnimated(active: Boolean) {
        if (borderAnimator?.isRunning == true) return
        borderAnimator = ValueAnimator.ofFloat(
            if (active) 0F else 1F,
            if (active) 1F else 0F
        ).apply {
            duration = ANIMATION_LENGTH
            addUpdateListener {
                borderTransform = animatedValue as Float
                Log.d("TAG", "border: $borderTransform")
                invalidate()
            }
            start()
        }
    }

    fun setActiveBorder(active: Boolean) {
        borderTransform = if (active) 1F else 0F
        invalidate()
    }

    fun setBorderTint(color: Int) {
        borderPaint.color = color
        invalidate()
    }

    fun setContainerTint(color: Int) {
        containerPaint.color = color
        invalidate()
    }

    fun setup(palette: ColorPalette) {
        this.palette = palette
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)

        val desiredWidth = defaultSize + paddingLeft + paddingRight
        val desiredHeight = defaultSize + paddingTop + paddingBottom

        val measuredWidth = when (widthMode) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(widthMeasureSpec)
            MeasureSpec.AT_MOST -> minOf(desiredWidth, MeasureSpec.getSize(widthMeasureSpec))
            MeasureSpec.UNSPECIFIED -> desiredWidth
            else -> desiredWidth
        }

        val measuredHeight = when (heightMode) {
            MeasureSpec.EXACTLY -> MeasureSpec.getSize(heightMeasureSpec)
            MeasureSpec.AT_MOST -> minOf(desiredHeight, MeasureSpec.getSize(heightMeasureSpec))
            MeasureSpec.UNSPECIFIED -> desiredHeight
            else -> desiredHeight
        }

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        drawContainer(canvas)
        drawPalette(canvas)
        drawStroke(canvas)
    }

    private fun drawStroke(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()
        canvas.saveLayerAlpha(
            0f, 0f, width, height,
            (borderTransform * 255).toInt()
        )
        canvas.drawRoundRect(
            strokeRadius, strokeRadius, width - strokeRadius, height - strokeRadius,
            cornerRadiusInner, cornerRadiusInner,
            borderPaint
        )
        canvas.restore()
    }

    private fun drawContainer(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()
        canvas.drawRoundRect(
            0F, 0F, width, height,
            cornerRadiusInner, cornerRadiusInner,
            containerPaint
        )
    }

    private fun drawPalette(canvas: Canvas) {
        val width = width.toFloat()
        val height = height.toFloat()
        val radius = paletteRadius

        val centerX = width / 2
        val centerY = height / 2

        val rectF = RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius)

        palettePaint.color = palette.primary
        canvas.drawArc(rectF, -180f, 180f, true, palettePaint)

        palettePaint.color = palette.tertiaryFixedDim
        canvas.drawArc(rectF, 0f, 90f, true, palettePaint)

        palettePaint.color = palette.secondaryContainer
        canvas.drawArc(rectF, 90f, 90f, true, palettePaint)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        (context as Activity).let { activity ->
            ColorRegistry.register(activity, object : ColorApplier {
                override val viewId: Int = id
                override val attribute: String = "containerTint"
                override val token: ColorToken = ColorToken.SurfaceContainerLow

                override fun applyColor(color: Int) {
                    setContainerTint(color)
                }
            })

            ColorRegistry.register(activity, object : ColorApplier {
                override val viewId: Int = id
                override val attribute: String = "borderTint"
                override val token: ColorToken = ColorToken.Primary

                override fun applyColor(color: Int) {
                    setBorderTint(color)
                }
            })
        }
    }

}