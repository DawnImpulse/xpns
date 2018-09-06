package org.sourcei.xpns.models

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import org.sourcei.xpns.repository.RealtimeRepository

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-04 by Saksham
 * @tnote Updates :
 */
class IconsModel(private val lifecycle: Lifecycle) {

    /**
     * get latest icons
     * @param callback
     */
    fun getLatestIcons(callback: (Any?, Any?) -> Unit) {
        RealtimeRepository.getLatestIcons() { e, r ->
            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart() {
                    callback(e, r)
                }
            })
        }
    }
}