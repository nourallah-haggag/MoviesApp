package com.meetntrain.moviesapp.common.activity

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle

/**
 * kotlin di provide methods to access the application context
 * but there is no way to access the current activity context using koin
 * therefor we will inject this class with koin to get the current activity
 */

class ActivityListener : Application.ActivityLifecycleCallbacks,
    CurrentActivityListener {

    override var currentActivity: Activity? = null
    lateinit var context: Context
    protected var currentActivityStack = mutableListOf<Activity>()

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStarted(activity: Activity) {
        currentActivity = activity
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity == currentActivity) {
            currentActivity = null
        }
        currentActivityStack.remove(activity)
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        currentActivity = activity
        context = activity
        currentActivityStack.add(activity)
    }

    override fun onActivityResumed(activity: Activity) {
        currentActivity = activity
    }

    /**
     * check if the activitty of a give class is running
     * @param activityClass
     * @return true if running
     */
    fun isActivityRunning(activityClass: Class<out Activity>): Boolean {
        for (activity in currentActivityStack) {
            if (activity.javaClass == activityClass)
                return true
        }
        return false
    }

}
