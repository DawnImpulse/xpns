package org.sourcei.xpns.utils.others

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-13 by Saksham
 * @tnote Updates :
 */
object Config {
    var DbName = "Xpns"
    var WALLET: String = "" // current wallet name
    var balance = Observe(0.0) // current wallet available balance
    var currency = "$" // current wallet currency symbol
}