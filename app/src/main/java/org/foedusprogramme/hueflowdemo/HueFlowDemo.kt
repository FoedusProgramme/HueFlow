package org.foedusprogramme.hueflowdemo

import android.app.Application
import org.foedusprogramme.hueflow.core.HueFlow

class HueFlowDemo : Application() {
    override fun onCreate() {
        super.onCreate()
        HueFlow.applyToApplication(this)
    }
}