package org.sourcei.xpns.pojo

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-03 by Saksham
 * @tnote Updates :
 */
data class IconPojo(
        val iid: String = "",
        val iname: String = "",
        val iurls: IconUrls? = null,
        val itags: List<String>? = null,
        val iauthor: String? = null,
        val iauthorLink: String? = null,
        val iauthorDp: String? = null
)

data class IconUrls(
        val url64: String = "",
        val url128: String = "",
        val url256: String = "",
        val url512: String = ""
)