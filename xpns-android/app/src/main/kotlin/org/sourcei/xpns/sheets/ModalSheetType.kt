package org.sourcei.xpns.sheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.bottom_sheet_type.*
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
 * @tnote Created on 2018-09-22 by Saksham
 * @tnote Updates :
 */
class ModalSheetType : RoundedBottomSheet(), View.OnClickListener {
    private lateinit var callback: Callback

    // on create
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_type, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sheetTypeE.setOnClickListener(this)
        sheetTypeS.setOnClickListener(this)
    }

    // on attached fragment
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        callback = context as Callback
    }

    // on click
    override fun onClick(v: View) {
        var obj = JSONObject()
        when (v.id) {
            sheetTypeE.id -> {
                obj.put(C.TYPE, C.TYPE)
                obj.put(C.NAME, C.EXPENSE)
            }
            sheetTypeS.id -> {
                obj.put(C.TYPE, C.TYPE)
                obj.put(C.NAME, C.SAVING)
            }
        }
        callback.call(obj)
        dismiss()
    }
}