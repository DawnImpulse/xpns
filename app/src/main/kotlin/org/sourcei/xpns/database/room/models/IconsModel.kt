package org.sourcei.xpns.database.room.models

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import org.sourcei.xpns.database.realtime.RealtimeRepository

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