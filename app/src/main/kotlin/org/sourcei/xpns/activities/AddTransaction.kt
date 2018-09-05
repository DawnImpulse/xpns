package org.sourcei.xpns.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.toast
import kotlinx.android.synthetic.main.activity_add_transaction.*
import org.json.JSONObject
import org.sourcei.xpns.R
import org.sourcei.xpns.interfaces.Callback
import org.sourcei.xpns.models.TransactionModel
import org.sourcei.xpns.sheets.ModalSheetTAmount
import org.sourcei.xpns.utils.C

class AddTransaction : AppCompatActivity(), View.OnClickListener, Callback {
    private lateinit var model: TransactionModel
    private lateinit var amountSheet: ModalSheetTAmount

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        model = TransactionModel(lifecycle, this)
        amountSheet = ModalSheetTAmount()

        addTCatL.setOnClickListener(this)
        addTAmount.setOnClickListener(this)
        addTDone.setOnClickListener(this)
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            addTAmount.id -> {
                if (!addTAmount.text.toString().contentEquals("$ 0.00"))
                    amountSheet.arguments = bundleOf(Pair(C.AMOUNT,
                            addTAmount.text.toString().replace("$", "").trim().toDouble()))
                amountSheet.show(supportFragmentManager, amountSheet.tag)
            }

            addTCatL.id -> {

            }

            addTDone.id -> {
                model.insert(
                        "100",
                        "7bd15289-b0cd-11e8-9282-1d7af35aec42",
                        "2018-09-05",
                        "00:00",
                        "This is IT !!"
                )
                toast("Done")
                finish()
            }
        }
    }

    // custom callback
    override fun call(any: Any) {
        any as JSONObject
        when (any.get(C.TYPE)) {
            C.AMOUNT -> {
                if (any.getDouble(C.AMOUNT) == 0.0)
                    addTAmount.text = "$ 0.00"
                else
                    addTAmount.text = "$ ${any.getDouble(C.AMOUNT)}"
            }
        }
    }
}
