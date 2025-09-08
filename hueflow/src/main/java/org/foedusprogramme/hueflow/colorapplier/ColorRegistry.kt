package org.foedusprogramme.hueflow.colorapplier

import android.app.Activity
import org.foedusprogramme.hueflow.core.HueFlow
import java.util.concurrent.ConcurrentHashMap

object ColorRegistry {
    private val activityRegistry = ConcurrentHashMap<Int, MutableList<ColorApplier>>()
    private val viewRegistry = ConcurrentHashMap<Int, MutableList<ColorApplier>>()

    fun register(activity: Activity, applier: ColorApplier) {
        register(activity, listOf(applier))
    }

    fun register(activity: Activity, appliers: List<ColorApplier>) {
        val list = activityRegistry.getOrPut(activity.taskId) { mutableListOf() }

        for (newApplier in appliers) {
            val index = list.indexOfFirst { it.viewId == newApplier.viewId && it.attribute == newApplier.attribute }
            if (index >= 0) {
                list[index] = newApplier
            } else {
                list.add(newApplier)
            }

            val vList = viewRegistry.getOrPut(newApplier.viewId) { mutableListOf() }
            val vIndex = vList.indexOfFirst { it.attribute == newApplier.attribute }
            if (vIndex >= 0) {
                vList[vIndex] = newApplier
            } else {
                vList.add(newApplier)
            }

            HueFlow.updatePaletteUponRegistration(newApplier)
        }
    }

    fun unregister(activity: Activity) {
        activityRegistry.remove(activity.taskId)
    }

    fun getApplier(viewId: Int, attribute: String): ColorApplier =
        viewRegistry[viewId]
            ?.first { it.attribute == attribute }
            ?: throw NoSuchElementException("No applier found for viewId=$viewId attribute=$attribute")

    fun getAppliers(viewId: Int): List<ColorApplier> =
        viewRegistry[viewId] ?: emptyList()

    fun getAppliers(activity: Activity): List<ColorApplier> =
        activityRegistry[activity.taskId] ?: emptyList()

    fun getAllAppliers(): List<ColorApplier> =
        activityRegistry.values.flatten()

    const val TAG = "ColorRegistry"
}
