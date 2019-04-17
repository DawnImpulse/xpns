package org.sourcei.xpns.database.realtime.models

import org.sourcei.xpns.database.realtime.RealtimeRepository
import org.sourcei.xpns.ui.activities.ModularActivity
import org.sourcei.xpns.utils.events.Post
import org.sourcei.xpns.utils.others.Lifecycle

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-04 by Saksham
 * @tnote Updates :
 */
class IconsModel(private val activity: ModularActivity) {

    /**
     * get latest icons
     * @param callback
     */
    fun getLatestIcons(callback: (Any?, Any?) -> Unit) {
        RealtimeRepository.getLatestIcons { e, r ->
            // send on UI (on start)
            Lifecycle.onStart(activity) {
                Post.allIcons(e, r)
            }
        }
    }
}