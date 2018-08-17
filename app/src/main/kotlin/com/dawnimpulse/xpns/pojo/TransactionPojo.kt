package com.dawnimpulse.xpns.pojo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
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
@Entity(
        tableName = "transactions",
        primaryKeys = ["_id"],
        foreignKeys = [
            ForeignKey(
                    entity = CategoryPojo::class,
                    parentColumns = arrayOf("_id"),
                    childColumns = arrayOf("cid")
            )
        ]

)
data class TransactionPojo(
        var _id: String, //unique id time based
        var amount: Double, //amount for the transaction
        var cid: String, //category foreign key reference
        var syncState: Boolean, //if transaction is synced
        var date: Date, //complete date object
        var note: String? //additional note or remarks
)