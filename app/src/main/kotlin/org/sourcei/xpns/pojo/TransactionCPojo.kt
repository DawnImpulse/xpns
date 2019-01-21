package org.sourcei.xpns.pojo

import androidx.room.Embedded


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-05 by Saksham
 * @tnote Updates :
 */

data class TransactionCPojo(
        @Embedded
        var obj: TransactionPojo,
        @Embedded
        var cat: CategoryPojo
)