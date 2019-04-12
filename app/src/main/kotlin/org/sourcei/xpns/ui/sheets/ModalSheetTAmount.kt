package org.sourcei.xpns.ui.sheets

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.bottom_sheet_t_amount.*
import me.grantland.widget.AutofitTextView
import org.json.JSONObject
import org.sourcei.xpns.R
import org.sourcei.xpns.utils.interfaces.Callback
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.F
import org.sourcei.xpns.utils.extensions.toast

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-05 by Saksham
 * @tnote Updates :
 */
class ModalSheetTAmount : RoundedBottomSheet(), View.OnClickListener {

    private lateinit var callback: Callback

    // on create
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.bottom_sheet_t_amount, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            sheetTA.text = it.getDouble(C.AMOUNT).toString()
        }

        sheetT0.setOnClickListener(this)
        sheetT00.setOnClickListener(this)
        sheetT1.setOnClickListener(this)
        sheetT2.setOnClickListener(this)
        sheetT3.setOnClickListener(this)
        sheetT4.setOnClickListener(this)
        sheetT5.setOnClickListener(this)
        sheetT6.setOnClickListener(this)
        sheetT7.setOnClickListener(this)
        sheetT8.setOnClickListener(this)
        sheetT9.setOnClickListener(this)
        sheetTD.setOnClickListener(this)
        sheetTB.setOnClickListener(this)

        sheetTB.setOnLongClickListener {
            sheetTA.text = "0"
            true
        }
    }

    // on attached fragment
    override fun onAttach(context: Context?) {
        super.onAttach(context)

        callback = context as Callback
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            sheetT0.id -> amountChange(0, sheetTA)
            sheetT1.id -> amountChange(1, sheetTA)
            sheetT2.id -> amountChange(2, sheetTA)
            sheetT3.id -> amountChange(3, sheetTA)
            sheetT4.id -> amountChange(4, sheetTA)
            sheetT5.id -> amountChange(5, sheetTA)
            sheetT6.id -> amountChange(6, sheetTA)
            sheetT7.id -> amountChange(7, sheetTA)
            sheetT8.id -> amountChange(8, sheetTA)
            sheetT9.id -> amountChange(9, sheetTA)
            sheetT00.id -> {
                if (sheetTA.text.contains("."))
                    context!!.toast("can't add 2 decimals")
                else
                    sheetTA.text = "${sheetTA.text}."
            }
            sheetTB.id -> {
                if (sheetTA.text.toString().length == 1)
                    sheetTA.text = "0"
                else
                    sheetTA.text = sheetTA.text.dropLast(1)
            }
            sheetTD.id -> {
                var amount = 0.0
                if (sheetTA.text.isNotEmpty())
                    amount = F.roundOff2Decimal(sheetTA.text.toString().toDouble())
                var json = JSONObject()
                json.put(C.TYPE, C.AMOUNT)
                json.put(C.AMOUNT, amount)
                callback.call(json)
                dismiss()
            }

        }
    }

    // change tamount on screen
    private fun amountChange(num: Int, layout: AutofitTextView) {
        if (layout.text.toString() == "0")
            layout.text = num.toString()
        else
            layout.text = "${layout.text}$num"

    }
}