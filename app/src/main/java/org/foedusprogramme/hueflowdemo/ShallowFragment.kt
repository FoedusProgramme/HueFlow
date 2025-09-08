package org.foedusprogramme.hueflowdemo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.button.MaterialButton
import org.foedusprogramme.hueflow.core.HueFlow

class ShallowFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_shallow, container, false)
        rootView.findViewById<MaterialButton>(R.id.red_hue).setOnClickListener {
            HueFlow.changePalette(HueFlow.redPalette)
        }

        rootView.findViewById<MaterialButton>(R.id.blue_hue).setOnClickListener {
            HueFlow.changePalette(HueFlow.bluePalette)
        }
        return rootView
    }
}