package org.sourcei.xpns.pojo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.sql.Date


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
                    entity = CategoryPojo::class,
                    parentColumns = arrayOf("cid"),
                    childColumns = arrayOf("tcid")
            )
        ],

        indices = [Index("tcid"), Index(value = "tid", unique = true)]

)
data class TransactionPojo(
        @PrimaryKey(autoGenerate = true)
        var taid: Int, //auto id
        var tid: String, //uuid
        var tamount: Double, //tamount for the transaction
        var tcid: String, //category foreign key reference
        var tsyncState: Boolean, //if transaction is synced
        var tdate: Date, //complete tdate object
        var tnote: String? //additional tnote or remarks
)