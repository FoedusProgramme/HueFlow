package org.foedusprogramme.hueflowdemo

import android.content.Context
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import org.foedusprogramme.hueflow.core.DynamicColorContextWrapper
import org.foedusprogramme.hueflow.hueFlowHijack

class MainActivity : AppCompatActivity() {
    override fun attachBaseContext(newBase: Context) {
        super.attachBaseContext(DynamicColorContextWrapper(newBase))
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        hueFlowHijack()
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
    }
}