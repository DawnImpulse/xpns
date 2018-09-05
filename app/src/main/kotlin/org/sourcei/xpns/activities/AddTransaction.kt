package org.sourcei.xpns.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import androidx.core.widget.toast
import kotlinx.android.synthetic.main.activity_add_transaction.*
import org.sourcei.xpns.R
import org.sourcei.xpns.models.TransactionModel

class AddTransaction : AppCompatActivity(), View.OnClickListener {
    private lateinit var model:TransactionModel

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        model = TransactionModel(lifecycle,this)

        addTCatL.setOnClickListener(this)
        addTAmount.setOnClickListener(this)
        addTDone.setOnClickListener(this)
    }

    // on click
    override fun onClick(v: View) {
        when(v.id){
            addTDone.id ->{
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
}
