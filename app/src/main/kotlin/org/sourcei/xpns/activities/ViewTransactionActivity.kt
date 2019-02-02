package org.sourcei.xpns.activities

import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_view_transaction.*
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.pojo.TransactionCPojo
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.setStatusBarColor

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-02-02 by Saksham
 * @note Updates :
 */
class ViewTransactionActivity : AppCompatActivity() {
    private lateinit var transaction: TransactionCPojo

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_transaction)

        transaction = Gson().fromJson(intent.getStringExtra(C.TRANSACTION), TransactionCPojo::class.java)
        setDetails()
    }

    //set details
    private fun setDetails() {
        val color = Color.parseColor(transaction.cat.ccolor)
        setColor(color)

        viewTAmount.text = "$ ${transaction.obj.tamount}"
        viewTCatName.text = transaction.cat.cname
        viewTNote.text = transaction.obj.tnote
        ImageHandler.setIconInView(lifecycle, viewTCatIcon, transaction.cat.cicon.iurls!!.url64)
    }

    // set color
    private fun setColor(color: Int) {
        viewTAmount.setTextColor(color)
        viewTDate.setTextColor(color)
        viewTTime.setTextColor(color)
        viewTWalletName.setTextColor(color)
        viewTDone.setTextColor(color)
        viewTCatName.setTextColor(color)
        viewTCView.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)

        setStatusBarColor(color)
    }
}
