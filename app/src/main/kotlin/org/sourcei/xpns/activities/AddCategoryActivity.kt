package org.sourcei.xpns.activities

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_add_category.*
import org.json.JSONObject
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.ColorHandler
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.interfaces.Callback
import org.sourcei.xpns.models.CategoryModel
import org.sourcei.xpns.pojo.CategoryPojo
import org.sourcei.xpns.pojo.IconPojo
import org.sourcei.xpns.sheets.ModalSheetCatName
import org.sourcei.xpns.sheets.ModalSheetType
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.Colors
import org.sourcei.xpns.utils.openActivityForResult
import org.sourcei.xpns.utils.toast
import java.util.*

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
    private lateinit var nameSheet: ModalSheetCatName
    private lateinit var typeSheet: ModalSheetType
    private lateinit var model: CategoryModel
    private var color = 0
    private var type: String? = null
    private var icon: IconPojo? = null
    private val SELECT_PARENT = 1
    private var parent: CategoryPojo? = null

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_category)

        nameSheet = ModalSheetCatName()
        typeSheet = ModalSheetType()
        model = CategoryModel(lifecycle, this)
        addCImage.setOnClickListener(this)
        addCName.setOnClickListener(this)
        addCDone.setOnClickListener(this)
        addCType.setOnClickListener(this)
        addCParentL.setOnClickListener(this)
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
                if (icon != null) {
                    if (addCName.text.toString() != "NAME") {
                        if (type != null) {
                            if (parent != null) { // a child category , also update parent one
                                val uuid = UUID.randomUUID().toString()
                                if (parent!!.cchilden != null)
                                    parent!!.cchilden!!.add(uuid)
                                else
                                    parent!!.cchilden = arrayListOf(uuid)
                                model.editItem(parent!!)
                                model.insert(
                                        addCName.text.toString().trim(),
                                        icon!!,
                                        type!!,
                                        ColorHandler.intColorToString(color),
                                        false,
                                        true,
                                        uuid
                                )
                            } else { // a parent category
                                model.insert(
                                        addCName.text.toString().trim(),
                                        icon!!,
                                        type!!,
                                        ColorHandler.intColorToString(color)
                                )
                            }

                            toast("category inserted")
                            finish()
                        } else
                            toast("kindly select category type")
                    } else
                        toast("kindly provide a name")
                } else
                    toast("kindly select an icon")
            }
            addCType.id -> {
                typeSheet.arguments = bundleOf(Pair(C.NAME, addCName.text.toString()))
                typeSheet.show(supportFragmentManager, typeSheet.tag)
            }
            addCParentL.id -> {
                openActivityForResult(CategoryActivity::class.java, SELECT_PARENT) {
                    putBoolean(C.SELECT, true)
                    putBoolean(C.SHOW_CHILD, false)
                }
            }
        }
    }

    // activity result from icons activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == C.ICON_REQUEST_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                icon = Gson().fromJson(data!!.getStringExtra(C.ICON), IconPojo::class.java)
                ImageHandler.getImageAsBitmap(lifecycle, this, icon!!.iurls!!.url64) {
                    addCImage.setImageBitmap(it)
                    color = ColorHandler.getNonDarkColor(androidx.palette.graphics.Palette.from(it).generate(), this)
                    setColor()
                }
            }
        }
        if (requestCode == SELECT_PARENT) {
            if (resultCode == Activity.RESULT_OK) {
                parent = Gson().fromJson(data!!.getStringExtra(C.CATEGORY), CategoryPojo::class.java)
                addCParentT.text = parent!!.cname
                ImageHandler.setImageInView(lifecycle, addCParentI, parent!!.cicon.iurls!!.url64)
            }
        }
    }

    // custom callback for cname sheet
    override fun call(any: Any) {
        any as JSONObject
        when (any.get(C.TYPE)) {
            C.NAME -> {
                if (any.getString(C.NAME).isEmpty())
                    addCName.text = "NAME"
                else
                    addCName.text = any.getString(C.NAME)
            }
            C.TYPE -> {
                type = any.getString(C.NAME)
                addCType.text = type!!.toUpperCase()
                if (type == C.EXPENSE)
                    addCType.setTextColor(Colors(this).EXPENSE)
                else
                    addCType.setTextColor(Colors(this).SAVING)
            }
        }
    }

    // set ccolor
    private fun setColor() {
        //val circle = addCCircle.background.current as GradientDrawable
        //var done = addCDone.background.current as GradientDrawable

        //circle.setColor(color)
        //done.setCcolor(ccolor)
        addCCView.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        addCName.setTextColor(color)
    }
}
