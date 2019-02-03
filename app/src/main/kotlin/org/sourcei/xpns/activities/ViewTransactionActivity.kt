package org.sourcei.xpns.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.google.gson.Gson
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog
import kotlinx.android.synthetic.main.activity_add_transaction.*
import kotlinx.android.synthetic.main.activity_view_transaction.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.DateHandler
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.interfaces.Callback
import org.sourcei.xpns.models.TransactionModel
import org.sourcei.xpns.pojo.CategoryPojo
import org.sourcei.xpns.pojo.TransactionCPojo
import org.sourcei.xpns.sheets.ModalSheetNote
import org.sourcei.xpns.sheets.ModalSheetTAmount
import org.sourcei.xpns.utils.*

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-02-02 by Saksham
 * @note Updates :
 */
class ViewTransactionActivity : AppCompatActivity(), View.OnClickListener, Callback,
        DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {
    private lateinit var transaction: TransactionCPojo
    private lateinit var model: TransactionModel
    private lateinit var category: CategoryPojo
    private lateinit var wallet: String
    private lateinit var noteSheet: ModalSheetNote
    private lateinit var amountSheet: ModalSheetTAmount
    private var categoryChanged = false
    private var noteChanged = false
    private var walletChanged = false
    private var dateChanged = false
    private var amountChanged = false

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_transaction)

        model = TransactionModel(lifecycle, this)
        noteSheet = ModalSheetNote()
        amountSheet = ModalSheetTAmount()
        transaction = Gson().fromJson(intent.getStringExtra(C.TRANSACTION), TransactionCPojo::class.java)
        setDetails()

        viewTAmount.setOnClickListener(this)
        viewTNoteL.setOnClickListener(this)
        viewTCatL.setOnClickListener(this)
        viewTNoteL.setOnClickListener(this)
        viewTDateL.setOnClickListener(this)
        viewTTimeL.setOnClickListener(this)
        viewTDone.setOnClickListener(this)
        viewTClose.setOnClickListener(this)
    }

    // on click
    override fun onClick(v: View?) {
        v.let {
            when (v?.id) {
                viewTAmount.id -> {
                    if (!viewTAmount.text.toString().contentEquals("$ 0.00"))
                        amountSheet.arguments = bundleOf(
                                Pair(
                                        C.AMOUNT,
                                        viewTAmount.text.toString().replace("$", "").trim().toDouble()
                                )
                        )
                    amountSheet.show(supportFragmentManager, amountSheet.tag)
                }
                viewTNoteL.id -> {
                    if (viewTNote.text.toString() != "NOTE")
                        noteSheet.arguments = bundleOf(Pair(C.NAME, viewTNote.text.toString()))
                    noteSheet.show(supportFragmentManager, noteSheet.tag)
                }
                viewTCatL.id -> {
                    openActivityForResult(CategoryActivity::class.java, C.CATEGORY_SELECT_CODE) {
                        putBoolean(C.SELECT, true)
                        putBoolean(C.SHOW_CHILD, true)
                        putBoolean(C.FAB, true)
                    }
                }
                viewTNoteL.id -> {
                    if (addTNote.text.toString() != "NOTE")
                        noteSheet.arguments = bundleOf(Pair(C.NAME, viewTNote.text.toString()))
                    noteSheet.show(supportFragmentManager, noteSheet.tag)
                }
                viewTDateL.id -> {
                    val date = DatePickerDialog.newInstance(this)
                    date.version = DatePickerDialog.Version.VERSION_2
                    date.show(fragmentManager, date.tag)
                }
                viewTTimeL.id -> {
                    val time = TimePickerDialog.newInstance(
                            this,
                            false
                    )
                    time.version = TimePickerDialog.Version.VERSION_2
                    time.show(fragmentManager, time.tag)
                }
                viewTDone.id -> {
                    if (noteChanged)
                        transaction.obj.tnote = viewTNote.text.toString()
                    if (categoryChanged)
                        transaction.obj.tcid = category.cid
                    if (walletChanged)
                        transaction.obj.twallet = wallet
                    if (dateChanged) {
                        val date = DateHandler.convertAddTLayout(viewTDate.text.toString())
                        val time = viewTTime.text.toString()
                        transaction.obj.tdate = DateHandler.fullToDate(date, time)
                    }
                    if (amountChanged)
                        transaction.obj.tamount = viewTAmount.text.toString().replace("$", "").trim().toDouble()

                    model.editItem(transaction.obj)
                    toast("transaction updated")
                    EventBus.getDefault().post(Event(jsonOf(Pair(C.TYPE, C.NEW_TRANSACTION))))
                    finish()
                }
                viewTClose.id -> finish()
            }
        }
    }

    // result of select category
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == C.CATEGORY_SELECT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                categoryChanged = true
                category = Gson().fromJson(data!!.getStringExtra(C.CATEGORY), CategoryPojo::class.java)
                viewTCatName.text = category.cname
                setColor(Color.parseColor(category.ccolor))
                ImageHandler.setImageInView(lifecycle, viewTCatIcon, category.cicon.iurls!!.url64)
            }
        }
    }

    // on date set
    override fun onDateSet(view: DatePickerDialog?, y: Int, m: Int, d: Int) {
        dateChanged = true
        viewTDate.text = DateHandler.convertAddTLayout(y, m, d)
    }

    // on time set
    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        dateChanged = true
        viewTTime.text = "${F.addZero(hourOfDay.toString())}:${F.addZero(minute.toString())}"
    }

    // custom callback
    override fun call(any: Any) {
        any as JSONObject
        when (any.get(C.TYPE)) {
            C.AMOUNT -> {
                amountChanged = true
                if (any.getDouble(C.AMOUNT) == 0.0)
                    viewTAmount.text = "$ 0.00"
                else
                    viewTAmount.text = "$ ${any.getDouble(C.AMOUNT)}"
            }
            C.NOTE -> {
                noteChanged = true
                val text = any.getString(C.NOTE)
                if (text.isNotEmpty())
                    viewTNote.text = text
                else
                    viewTNote.text = "NOTE"
            }
        }
    }

    //set details
    private fun setDetails() {
        val color = Color.parseColor(transaction.cat.ccolor)
        setColor(color)
        if (transaction.obj.tnote == null || transaction.obj.tnote!!.isEmpty())
            viewTNote.text = "NOTE"

        viewTAmount.text = "$ ${transaction.obj.tamount}"
        viewTCatName.text = transaction.cat.cname
        viewTNote.text = transaction.obj.tnote
        viewTDate.text = DateHandler.viewableFromObject(transaction.obj.tdate)
        viewTTime.text = DateHandler.viewableTimeFromObject(transaction.obj.tdate)
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
        /*viewTFab.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        viewTFabI.drawable.setColorFilter(ColorHandler.getContrastColor(color), PorterDuff.Mode.SRC_ATOP)*/

        setStatusBarColor(color)
    }
}
