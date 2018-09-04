package org.sourcei.xpns.repository

import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import org.sourcei.xpns.pojo.IconPojo
import org.sourcei.xpns.source.RealtimeSource
import org.sourcei.xpns.utils.C

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-04 by Saksham
 * @note Updates :
 */
object RealtimeRepository {

    /**
     * get latest icons
     */
    fun getLatestIcons(callback: (Any?, Any?) -> Unit) {
        val ref = FirebaseDatabase.getInstance().reference
                .child(C.ICONS)
                .child(C.LATEST)
                .orderByKey()

        RealtimeSource.getDataOnce(ref) { e, r ->
            if(e!=null)
                callback(e,null)
            else{
                var data = ArrayList<IconPojo>()
                for(snapshot in (r as DataSnapshot).children)
                    data.add(snapshot.getValue(IconPojo::class.java)!!)
                callback(null,data)
            }
        }

    }
}