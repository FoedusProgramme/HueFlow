package org.foedusprogramme.hueflowdemo

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputLayout
import org.foedusprogramme.hueflow.core.HueFlow
import org.foedusprogramme.hueflow.core.HueFlowPresets
import org.foedusprogramme.hueflow.palette.PaletteStyle
import org.foedusprogramme.hueflow.palette.hueFlowColorPalette
import androidx.core.graphics.toColorInt

class ShallowFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_shallow, container, false)
        rootView.findViewById<MaterialButton>(R.id.red_hue).setOnClickListener {
            HueFlow.changePalette(HueFlowPresets.redColorPalette(false))
        }

        rootView.findViewById<MaterialButton>(R.id.blue_hue).setOnClickListener {
            HueFlow.changePalette(HueFlowPresets.blueColorPalette(false))
        }

        rootView.findViewById<MaterialButton>(R.id.apply).setOnClickListener {
            rootView.findViewById<TextInputLayout>(R.id.textField).editText?.text?.let {
                HueFlow.changePalette(hueFlowColorPalette(
                    it.toString().toColorInt(),
                    false,
                    PaletteStyle.TonalSpot
                ))
            }
        }
        return rootView
    }
}