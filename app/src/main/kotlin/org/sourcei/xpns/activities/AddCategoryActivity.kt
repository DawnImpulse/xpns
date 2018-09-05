package org.sourcei.xpns.activities

import android.app.Activity
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.graphics.Palette
import android.view.View
import androidx.core.os.bundleOf
import androidx.core.widget.toast
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_category.*
import org.json.JSONObject
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.ColorHandler
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.interfaces.Callback
import org.sourcei.xpns.models.CategoryModel
import org.sourcei.xpns.pojo.IconPojo
import org.sourcei.xpns.sheets.ModalSheetCatName
import org.sourcei.xpns.utils.C

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-04 by Saksham
 * @note Updates :
 */
class AddCategoryActivity : AppCompatActivity(), View.OnClickListener, Callback {
    private lateinit var icon: IconPojo
    private lateinit var nameSheet: ModalSheetCatName
    private lateinit var model: CategoryModel
    private var color = 0

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        nameSheet = ModalSheetCatName()
        model = CategoryModel(lifecycle, this)
        addCImage.setOnClickListener(this)
        addCName.setOnClickListener(this)
        addCDone.setOnClickListener(this)
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            addCImage.id ->
                startActivityForResult(Intent(this, IconsActivity::class.java), C.ICON_REQUEST_CODE)
            addCName.id -> {
                if (addCName.text.toString() != "NAME")
                    nameSheet.arguments = bundleOf(Pair(C.NAME, addCName.text.toString()))
                nameSheet.show(supportFragmentManager, nameSheet.tag)
            }
            addCDone.id -> {
                model.insert(
                        addCName.text.toString().trim(),
                        null,
                        icon,
                        C.EXPENSE,
                        ColorHandler.intColorToString(color)
                )
                toast("done")
            }
        }
    }

    // activity result from icons activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == C.ICON_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                icon = Gson().fromJson(data!!.getStringExtra(C.ICON), IconPojo::class.java)
                ImageHandler.getImageAsBitmap(lifecycle, this, icon.urls!!.url64) {
                    addCImage.setImageBitmap(it)
                    color = ColorHandler.getNonDarkColor(Palette.from(it).generate(), this)
                    setColor()
                }
            }
        }
    }

    // custom callback for name sheet
    override fun call(any: Any) {
        any as JSONObject
        when (any.get(C.TYPE)) {
            C.NAME -> {
                addCName.text = any.getString(C.NAME)
            }
        }
    }

    // set color
    private fun setColor() {
        var circle = addCCircle.background.current as GradientDrawable
        //var done = addCDone.background.current as GradientDrawable

        circle.setColor(color)
        //done.setColor(color)
        addCName.setTextColor(color)
    }
}
