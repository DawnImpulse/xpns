package org.sourcei.xpns.utils.sheets

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import kotlinx.android.synthetic.main.bottom_sheet_icon.*
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.pojo.IconPojo
import org.sourcei.xpns.utils.C

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-04 by Saksham
 * @note Updates :
 */
class ModalSheetIcon : RoundedBottomSheet(), View.OnClickListener {
    lateinit var icon: IconPojo

    // on create
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_icon, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        icon = Gson().fromJson(arguments!!.getString(C.ICON), IconPojo::class.java)
    }

    // on resume
    override fun onResume() {
        super.onResume()

        ImageHandler.setImageInView(lifecycle, sheetIconI, icon.urls!!.url64)
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            sheetIconSelect.id -> {
                var intent = Intent()
                intent.putExtra(C.ICON, Gson().toJson(icon))
                activity!!.setResult(RESULT_OK, intent)
                activity!!.finish()
            }
        }
    }
}