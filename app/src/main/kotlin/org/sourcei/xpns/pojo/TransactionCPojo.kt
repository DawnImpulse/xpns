package org.sourcei.xpns.pojo

import java.sql.Date


/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-05 by Saksham
 * @note Updates :
 */

data class TransactionCPojo(
        var id: String,
        var amount: Double,
        var syncState: Boolean,
        var date: Date,
        var note: String?,

        var icon: IconPojo,
        var type: String,
        var color: String,
        var name: String
)