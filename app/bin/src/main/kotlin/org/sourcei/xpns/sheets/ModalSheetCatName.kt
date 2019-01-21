package org.sourcei.xpns.sheets

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.bottom_sheet_cat_name.*
import org.json.JSONObject
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
        return inflater.inflate(org.sourcei.xpns.R.layout.bottom_sheet_cat_name, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val name = it.getString(C.NAME)
            if (name != "NAME")
                sheetCNE.setText(name)
        }

        sheetCND.setOnClickListener(this)
        sheetCNE.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO){
                val obj = JSONObject()
                obj.put(C.TYPE, C.NAME)
                obj.put(C.NAME, sheetCNE.text.toString())
                callback.call(obj)
                dismiss()
            }
            false
        }

        Handler().postDelayed({
            sheetCNE.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_DOWN, 0f, 0f, 0))
            sheetCNE.dispatchTouchEvent(MotionEvent.obtain(SystemClock.uptimeMillis(), SystemClock.uptimeMillis(), MotionEvent.ACTION_UP, 0f, 0f, 0))
        }, 100)
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
                val obj = JSONObject()
                obj.put(C.TYPE, C.NAME)
                obj.put(C.NAME, sheetCNE.text.toString())
                callback.call(obj)
                dismiss()
            }
        }
    }
}