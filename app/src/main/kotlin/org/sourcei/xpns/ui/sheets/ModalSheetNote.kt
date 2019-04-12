package org.sourcei.xpns.ui.sheets

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.os.SystemClock
import android.view.*
import android.view.inputmethod.EditorInfo
import kotlinx.android.synthetic.main.bottom_sheet_note.*
import org.json.JSONObject
import org.sourcei.xpns.R
import org.sourcei.xpns.utils.interfaces.Callback
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.views.MyEditText


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2019-01-22 by Saksham
 * @tnote Updates :
 */
class ModalSheetNote : RoundedBottomSheet(), View.OnClickListener {
    private lateinit var callback: Callback

    // on create
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_note, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            val note = it.getString(C.NOTE)
            if (note != "NOTE")
                sheetNoteText.setText(note)
        }

        sheetNoteText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_GO) {
                val obj = JSONObject()
                obj.put(C.TYPE, C.NOTE)
                obj.put(C.NOTE, sheetNoteText.text.toString())
                callback.call(obj)
                dismiss()
            }
            false
        }
        sheetNoteText.setKeyImeChangeListener(object : MyEditText.KeyImeChange {
            override fun onKeyIme(keyCode: Int, event: KeyEvent) {
                if (KeyEvent.KEYCODE_BACK == event.keyCode) {
                    dismiss()
                }
            }
        })

        Handler().postDelayed({
            sheetNoteText.dispatchTouchEvent(
                MotionEvent.obtain(
                    SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(),
                    MotionEvent.ACTION_DOWN,
                    0f,
                    0f,
                    0
                )
            )
            sheetNoteText.dispatchTouchEvent(
                MotionEvent.obtain(
                    SystemClock.uptimeMillis(),
                    SystemClock.uptimeMillis(),
                    MotionEvent.ACTION_UP,
                    0f,
                    0f,
                    0
                )
            )
        }, 100)
    }

    // on attached fragment
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        callback = context as Callback
    }

    // on click
    override fun onClick(v: View) {

    }
}