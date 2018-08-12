package com.dawnimpulse.xpns.pojo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

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
data class Transactions(
        @PrimaryKey var id: Int
)