package org.foedusprogramme.hueflow.components.palettechanger

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.foedusprogramme.hueflow.R
import org.foedusprogramme.hueflow.core.HueFlow
import org.foedusprogramme.hueflow.core.HueFlowPresets
import org.foedusprogramme.hueflow.equalsByValue
import org.foedusprogramme.hueflow.palette.ColorPalette

class PaletteChangerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private var activeLocation = -1

    init {
        layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        adapter = PaletteAdapter()
    }

    private val paletteList = listOf(
        HueFlowPresets.blueColorPalette(false),
        HueFlowPresets.redColorPalette(false),
        HueFlowPresets.greenColorPalette(false),
        HueFlowPresets.yellowColorPalette(false)
    )

    inner class PaletteAdapter : Adapter<ViewHolder>() {

        private val paletteDisplayerViewMargin = resources.getDimensionPixelSize(R.dimen.palette_displayer_margin)

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = PaletteDisplayerView(context)
            view.id = generateViewId()
            view.layoutParams = MarginLayoutParams(
                LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT
            ).apply {
                marginEnd = paletteDisplayerViewMargin
            }
            return ViewHolder(view)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            Log.d("TAG", "method Called 1")
            val paletteDisplayerView = (holder.itemView as PaletteDisplayerView)
            paletteDisplayerView.setup(paletteList[position])
            val displayerEqualsCurrentPalette = paletteList[position].equalsByValue(HueFlow.currentPalette)
            if (displayerEqualsCurrentPalette) activeLocation = holder.adapterPosition
            paletteDisplayerView.setActiveBorder(displayerEqualsCurrentPalette)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int, payloads: List<Any>) {
            if (payloads.isEmpty()) {
                onBindViewHolder(holder, position)
                return
            }
            Log.d("TAG", "method Called 2")

            val paletteDisplayerView = (holder.itemView as PaletteDisplayerView)

            if (payloads.isNotEmpty()) {
                val payload = payloads[0]
                when (payload) {
                    "changed_active" -> {
                        paletteDisplayerView.setActiveBorderAnimated(true)
                    }
                    "changed_inactive" -> {
                        paletteDisplayerView.setActiveBorderAnimated(false)
                    }
                }
            }
        }

        override fun getItemCount(): Int =
            paletteList.size
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        HueFlow.addOnPaletteUpdateListener(object : HueFlow.OnPaletteUpdateListener {
            override val id: Int = this@PaletteChangerView.id
            override fun onUpdate(oldPalette: ColorPalette, newPalette: ColorPalette) {
                val newLoc = paletteList.indexOfFirst { it.equalsByValue(newPalette) }

                if (newLoc != -1) {
                    adapter?.notifyItemChanged(newLoc, "changed_active")
                }
                adapter?.notifyItemChanged(activeLocation, "changed_inactive")
                activeLocation = newLoc
            }

        })
    }

    override fun onDetachedFromWindow() {
        HueFlow.removeOnPaletteUpdateListener(this@PaletteChangerView.id)
        super.onDetachedFromWindow()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    }
}
