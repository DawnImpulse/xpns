package org.sourcei.xpns.pojo

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-03 by Saksham
 * @note Updates :
 */
data class IconPojo(
        val name: String = "",
        val url64: String = "",
        val tags: List<String>? = null,
        val author: String? = null,
        val authorLink: String? = null,
        val authorDp: String? = null
)