package org.sourcei.xpns.sheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.bottom_sheet_cat_name.*
import org.json.JSONObject
import org.sourcei.xpns.R
import org.sourcei.xpns.interfaces.Callback
import org.sourcei.xpns.utils.C

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-04 by Saksham
 * @tnote Updates :
 */
class ModalSheetCatName : RoundedBottomSheet(), View.OnClickListener {
    private lateinit var callback: Callback

    // on create
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_cat_name, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            var name = it!!.getString(C.NAME)
            if (name != "NAME")
                sheetCNE.setText(name)
        }

        sheetCND.setOnClickListener(this)
    }

    // on attached fragment
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        callback = context as Callback
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            sheetCND.id -> {
                var obj = JSONObject()
                obj.put(C.TYPE, C.NAME)
                obj.put(C.NAME, sheetCNE.text.toString())
                callback.call(obj)
                dismiss()
            }
        }
    }
}