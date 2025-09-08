package org.foedusprogramme.hueflowdemo

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import org.foedusprogramme.hueflow.core.HueFlow
import org.foedusprogramme.hueflow.hueFlowHijack

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        hueFlowHijack()

        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        findViewById<MaterialButton>(R.id.red_hue).setOnClickListener {
            HueFlow.changePalette(HueFlow.redPalette)
        }

        findViewById<MaterialButton>(R.id.blue_hue).setOnClickListener {
            HueFlow.changePalette(HueFlow.bluePalette)
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}