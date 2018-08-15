package com.dawnimpulse.xpns.pojo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.*

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-08-12 by Saksham
 * @note Updates :
 */
@Entity(tableName = "transactions")
data class TransactionPojo(
        @PrimaryKey
        var _id: String,
        var amount: Double,
        var cid: String,
        var syncState: Boolean,
        var date: Date,
        var note: String
)