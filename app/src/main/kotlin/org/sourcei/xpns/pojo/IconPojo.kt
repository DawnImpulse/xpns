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
        val id: String = "",
        val name: String = "",
        val urls: IconUrls? = null,
        val tags: List<String>? = null,
        val author: String? = null,
        val authorLink: String? = null,
        val authorDp: String? = null
)

data class IconUrls(
        val url64: String = "",
        val url128: String = "",
        val url256: String = "",
        val url512: String = ""
)