package org.sourcei.xpns.database.realtime

import com.google.firebase.database.*

/**
 * @info - Firebase realtime data source
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-03 by Saksham
 * @tnote Updates :
 */
object RealtimeSource {

    /**
     * Get data from realtime database once
     * @param reference
     * @param callback
     */
    fun getDataOnce(reference: DatabaseReference, callback: (Any?, Any?) -> Unit) {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                callback(null, p0)
            }

            override fun onCancelled(p0: DatabaseError) {
                callback(p0.toString(), null)
            }
        })
    }


    /**
     * Get data from realtime database once (Query)
     * @param reference
     * @param callback
     */
    fun getDataOnce(reference: Query, callback: (Any?, Any?) -> Unit) {
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(p0: DataSnapshot) {
                callback(null, p0)
            }

            override fun onCancelled(p0: DatabaseError) {
                callback(p0.toString(), null)
            }
        })
    }
}