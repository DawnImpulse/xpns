package org.sourcei.xpns.sheets

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import org.sourcei.xpns.R
import org.sourcei.xpns.interfaces.Callback

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-13 by Saksham
 * @tnote Updates :
 */
class ModalSheetNav : RoundedBottomSheet(), View.OnClickListener {
    private lateinit var callback: Callback

    // on create
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_nav, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {

        }
    }
}