package com.meetntrain.moviesapp.common.activity

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import com.meetntrain.moviesapp.common.activity.ActivityListener

/**
 * gets the activity listener class injected in
 */
class ActivityRetriever(val activityListener: ActivityListener) {
    val layoutInflater: LayoutInflater = LayoutInflater.from(activityListener.currentActivity)
    val context: Context = activityListener.context
    fun getActivity(): Activity? = activityListener.currentActivity
}