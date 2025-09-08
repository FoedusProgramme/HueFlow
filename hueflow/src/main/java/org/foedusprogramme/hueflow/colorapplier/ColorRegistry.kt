package org.foedusprogramme.hueflow.colorapplier

import android.app.Activity
import android.util.Log
import org.foedusprogramme.hueflow.core.HueFlow
import java.util.concurrent.ConcurrentHashMap

object ColorRegistry {
    private val registry = ConcurrentHashMap<Int, MutableList<ColorApplier>>()

    fun register(activity: Activity, applier: ColorApplier) {
        register(activity, listOf(applier))
    }

    fun register(activity: Activity, appliers: List<ColorApplier>) {
        val list = registry.getOrPut(activity.taskId) { mutableListOf() }

        for (newApplier in appliers) {
            val index = list.indexOfFirst { it.viewId == newApplier.viewId && it.attribute == newApplier.attribute }
            if (index >= 0) {
                list[index] = newApplier
                Log.d(TAG, "Updated ColorApplier for ${newApplier.viewId}: ${newApplier.attribute}")
            } else {
                list.add(newApplier)
                Log.d(TAG, "Added ColorApplier for ${newApplier.viewId}: ${newApplier.attribute}")
            }
            HueFlow.updatePaletteUponRegistration(newApplier)
        }
    }

    fun unregister(activity: Activity) {
        registry.remove(activity.taskId)
    }

    fun getAppliers(activity: Activity): List<ColorApplier> =
        registry[activity.taskId] ?: emptyList()

    fun getAllAppliers(): List<ColorApplier> =
        registry.values.flatten()

    const val TAG = "ColorRegistry"
}
