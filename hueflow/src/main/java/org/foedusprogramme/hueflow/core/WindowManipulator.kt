package org.foedusprogramme.hueflow.core

import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.Window
import org.foedusprogramme.hueflow.colorapplier.ColorRegistry
import org.foedusprogramme.hueflow.colorapplier.implementation.WindowColorApplier
import java.lang.ref.WeakReference
import java.util.WeakHashMap
import kotlin.collections.set

class WindowManipulator {

    private val windowMap = WeakHashMap<Activity, WeakReference<Window>>()

    fun onAcquireActivity(application: Application) {
        application.registerActivityLifecycleCallbacks(object :
            Application.ActivityLifecycleCallbacks {

            override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
                val window = activity.window
                windowMap[activity] = WeakReference(window)

                // We register a color applier for window when an activity
                // is created. Then we should immediately update the theme.
                ColorRegistry.register(activity, WindowColorApplier(window))

                Log.d(TAG, "Activity registered, taskId: ${activity.taskId}")
            }

            override fun onActivityDestroyed(activity: Activity) {
                windowMap.remove(activity)
                ColorRegistry.unregister(activity)

                Log.d(TAG, "Activity destroyed, taskId: ${activity.taskId}")
            }

            override fun onActivityResumed(activity: Activity) {}
            override fun onActivityPaused(activity: Activity) {}
            override fun onActivityStarted(activity: Activity) {}
            override fun onActivityStopped(activity: Activity) {}
            override fun onActivitySaveInstanceState(activity: Activity, bundle: Bundle) {}

        })
    }

    fun getWindows(): List<Window> {
        return windowMap.values.mapNotNull { it.get() }
    }

    companion object {
        const val TAG = "WindowManipulator"
    }

}