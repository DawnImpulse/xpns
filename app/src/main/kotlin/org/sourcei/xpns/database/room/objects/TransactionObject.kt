package org.sourcei.xpns.database.room.objects

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.*


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-12 by Saksham
 * @tnote Updates :
 */
@Entity(
    tableName = "transactions",
    foreignKeys = [
        ForeignKey(
            entity = CategoryObject::class,
            parentColumns = arrayOf("cid"),
            childColumns = arrayOf("tcid")
        )
    ],

    indices = [Index("tcid"), Index(value = ["tid"], unique = true)]

)
data class TransactionPojo(
    @PrimaryKey(autoGenerate = true)
    var taid: Int, //auto id
    var tid: String, //uuid
    var tamount: Double, //tamount for the transaction
    var tcid: String, //category foreign key reference
    var tsyncState: Boolean = false, //if transaction is synced
    var tdate: Date, //complete tdate object
    var tnote: String?, //additional tnote or remarks
    var twallet: String = "" //wallet id (null for main wallet)
)

data class TransactionTotal(
    var total: Double
)