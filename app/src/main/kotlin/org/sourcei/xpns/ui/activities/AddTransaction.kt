package org.sourcei.xpns.ui.activities

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
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import org.sourcei.xpns.R
import org.sourcei.xpns.utils.handlers.DateHandler
import org.sourcei.xpns.utils.handlers.ImageHandler
import org.sourcei.xpns.utils.interfaces.Callback
import org.sourcei.xpns.database.room.models.TransactionModel
import org.sourcei.xpns.database.room.objects.CategoryObject
import org.sourcei.xpns.ui.sheets.ModalSheetNote
import org.sourcei.xpns.ui.sheets.ModalSheetTAmount
import org.sourcei.xpns.utils.*
import org.sourcei.xpns.utils.extensions.jsonOf
import org.sourcei.xpns.utils.extensions.openActivityForResult
import org.sourcei.xpns.utils.extensions.setStatusBarColor
import org.sourcei.xpns.utils.extensions.toast
import org.sourcei.xpns.utils.others.Config
import java.util.*


class AddTransaction : AppCompatActivity(), View.OnClickListener, Callback,
    DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private lateinit var model: TransactionModel
    private lateinit var amountSheet: ModalSheetTAmount
    private lateinit var noteSheet: ModalSheetNote
    private lateinit var calendar: Calendar
    private var category: CategoryObject? = null

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
            "${F.addZero(calendar.get(Calendar.HOUR_OF_DAY).toString())}:${F.addZero(
                calendar.get(
                    Calendar.MINUTE
                ).toString()
            )}"

        addTCatL.setOnClickListener(this)
        addTAmount.setOnClickListener(this)
        addTNoteL.setOnClickListener(this)
        addTDateL.setOnClickListener(this)
        addTTimeL.setOnClickListener(this)
        addTClose.setOnClickListener(this)
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            addTAmount.id -> {
                if (!addTAmount.text.toString().contentEquals("$ 0.00"))
                    amountSheet.arguments = bundleOf(
                        Pair(
                            C.AMOUNT,
                            addTAmount.text.toString().trim().toDouble()
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
                    addTAmount.text.toString().trim(),
                    category!!.cid,
                    DateHandler.convertAddTLayout(addTDate.text.toString()),
                    addTTime.text.toString(),
                    if (addTNote.text.toString() == "NOTE") null else addTNote.text.toString(),
                    Config.WALLET
                )
                toast("Done")
                EventBus.getDefault().post(Event(
                    jsonOf(
                        Pair(
                            C.TYPE,
                            C.NEW_TRANSACTION
                        )
                    )
                ))
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
                    addTAmount.text = "0.00"
                else
                    addTAmount.text = "${any.getDouble(C.AMOUNT)}"
                enableDone()
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
                category =
                    Gson().fromJson(data!!.getStringExtra(C.CATEGORY), CategoryObject::class.java)
                addTCatName.text = category!!.cname
                setColor(Color.parseColor(category!!.ccolor))
                ImageHandler.setImageInView(lifecycle, addTCatIcon, category!!.cicon.iurls!!.url64)
                enableDone()
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
        setStatusBarColor(color)
    }

    // enable done
    private fun enableDone() {
        fun disable() {
            addTDoneL.alpha = 0.3.toFloat()
            addTDoneL.setOnClickListener(null)
        }

        if (category != null) {
            if (addTAmount.text.toString() != "0.00") {
                addTDoneL.alpha = 1.toFloat()
                addTDoneL.setOnClickListener(this)
            } else
                disable()
        } else
            disable()

    }
}
