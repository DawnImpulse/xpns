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
import org.json.JSONObject
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.DateHandler
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.interfaces.Callback
import org.sourcei.xpns.models.TransactionModel
import org.sourcei.xpns.pojo.CategoryPojo
import org.sourcei.xpns.sheets.ModalSheetNote
import org.sourcei.xpns.sheets.ModalSheetTAmount
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.F
import org.sourcei.xpns.utils.openActivityForResult
import org.sourcei.xpns.utils.toast
import java.util.*


class AddTransaction : AppCompatActivity(), View.OnClickListener, Callback,
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var model: TransactionModel
    private lateinit var amountSheet: ModalSheetTAmount
    private lateinit var noteSheet: ModalSheetNote
    private lateinit var category: CategoryPojo
    private lateinit var calendar: Calendar

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_transaction)

        model = TransactionModel(lifecycle, this)
        amountSheet = ModalSheetTAmount()
        noteSheet = ModalSheetNote()

        calendar = Calendar.getInstance()
        addTDate.text = DateHandler.convertAddTLayout(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )
        addTTime.text =
                "${F.addZero(calendar.get(Calendar.HOUR_OF_DAY).toString())}:${F.addZero(calendar.get(Calendar.MINUTE).toString())}"

        addTCatL.setOnClickListener(this)
        addTAmount.setOnClickListener(this)
        addTDoneL.setOnClickListener(this)
        addTNoteL.setOnClickListener(this)
        addTDateL.setOnClickListener(this)
        addTTimeL.setOnClickListener(this)
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            addTAmount.id -> {
                if (!addTAmount.text.toString().contentEquals("$ 0.00"))
                    amountSheet.arguments = bundleOf(
                        Pair(
                            C.AMOUNT,
                            addTAmount.text.toString().replace("$", "").trim().toDouble()
                        )
                    )
                amountSheet.show(supportFragmentManager, amountSheet.tag)
            }
            addTCatL.id -> {
                openActivityForResult(CategoryActivity::class.java, C.CATEGORY_SELECT_CODE) {
                    putBoolean(C.SELECT, true)
                    putBoolean(C.SHOW_CHILD, true)
                    putBoolean(C.FAB, true)
                }
            }
            addTNoteL.id -> {
                if (addTNote.text.toString() != "NOTE")
                    noteSheet.arguments = bundleOf(Pair(C.NAME, addTNote.text.toString()))
                noteSheet.show(supportFragmentManager, noteSheet.tag)
            }
            addTDateL.id -> {
                val date = DatePickerDialog.newInstance(this)
                date.version = DatePickerDialog.Version.VERSION_2
                date.show(fragmentManager, date.tag)
            }
            addTTimeL.id -> {
                val time = TimePickerDialog.newInstance(
                    this,
                    false
                )
                time.version = TimePickerDialog.Version.VERSION_2
                time.show(fragmentManager, time.tag)
            }
            addTDoneL.id -> {
                model.insert(
                    addTAmount.text.toString().replace("$", "").trim(),
                    category.cid,
                    DateHandler.convertAddTLayout(addTDate.text.toString()),
                    addTTime.text.toString(),
                    if (addTNote.text.toString() == "NOTE") null else addTNote.text.toString()
                )
                toast("Done")
                finish()
            }
            addTClose.id -> finish()
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
            C.NOTE -> {
                val text = any.getString(C.NOTE)
                if (text.isNotEmpty())
                    addTNote.text = text
                else
                    addTNote.text = "NOTE"
            }
        }
    }

    // result of select category
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == C.CATEGORY_SELECT_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                category = Gson().fromJson(data!!.getStringExtra(C.CATEGORY), CategoryPojo::class.java)
                addTCatName.text = category.cname
                setColor(Color.parseColor(category.ccolor))
                ImageHandler.setImageInView(lifecycle, addTCatIcon, category.cicon.iurls!!.url64)
            }
        }
    }

    // on date set
    override fun onDateSet(view: DatePickerDialog?, y: Int, m: Int, d: Int) {
        addTDate.text = DateHandler.convertAddTLayout(y, m, d)
    }

    // on time set
    override fun onTimeSet(view: TimePickerDialog?, hourOfDay: Int, minute: Int, second: Int) {
        addTTime.text = "${F.addZero(hourOfDay.toString())}:${F.addZero(minute.toString())}"
    }

    // set colors
    private fun setColor(color: Int) {
        addTAmount.setTextColor(color)
        addTDate.setTextColor(color)
        addTTime.setTextColor(color)
        addTWalletName.setTextColor(color)
        addTDone.setTextColor(color)
        addTCatName.setTextColor(color)

        addTDoneI.drawable.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        addTCView.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    }
}
