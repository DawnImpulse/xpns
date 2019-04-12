package org.sourcei.xpns.ui.activities

import android.app.Activity
import android.content.Intent
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.toColorInt
import androidx.core.os.bundleOf
import androidx.palette.graphics.Palette
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_view_category.*
import org.greenrobot.eventbus.EventBus
import org.json.JSONObject
import org.sourcei.xpns.R
import org.sourcei.xpns.utils.handlers.ColorHandler
import org.sourcei.xpns.utils.handlers.ImageHandler
import org.sourcei.xpns.utils.interfaces.Callback
import org.sourcei.xpns.database.room.models.CategoryModel
import org.sourcei.xpns.database.room.objects.CategoryObject
import org.sourcei.xpns.database.realtime.objects.IconPojo
import org.sourcei.xpns.ui.sheets.ModalSheetCatName
import org.sourcei.xpns.ui.sheets.ModalSheetType
import org.sourcei.xpns.utils.*
import org.sourcei.xpns.utils.extensions.jsonOf
import org.sourcei.xpns.utils.extensions.openActivityForResult
import org.sourcei.xpns.utils.extensions.toColor
import org.sourcei.xpns.utils.extensions.toast
import org.sourcei.xpns.utils.others.Colors

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-02-04 by Saksham
 * @note Updates :
 */
class ViewCategoryActivity : AppCompatActivity(), View.OnClickListener, Callback {
    private lateinit var nameSheet: ModalSheetCatName
    private lateinit var typeSheet: ModalSheetType
    private lateinit var model: CategoryModel
    private lateinit var category: CategoryObject
    private var type: String? = null
    private var icon: IconPojo? = null
    private var parent: CategoryObject? = null
    private var oParent: CategoryObject? = null
    private var color = 0
    private val SELECT_PARENT = 1

    // on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_category)

        category = Gson().fromJson(intent.getStringExtra(C.CATEGORY), CategoryObject::class.java)
        nameSheet = ModalSheetCatName()
        typeSheet = ModalSheetType()
        model = CategoryModel(lifecycle, this)


        viewCImage.setOnClickListener(this)
        viewCName.setOnClickListener(this)
        viewCDone.setOnClickListener(this)
        viewCType.setOnClickListener(this)
        viewCParentL.setOnClickListener(this)

        setDetails()
    }

    // on click
    override fun onClick(v: View) {
        when (v.id) {
            viewCImage.id ->
                startActivityForResult(Intent(this, IconsActivity::class.java), C.ICON_REQUEST_CODE)
            viewCName.id -> {
                if (viewCName.text.toString() != "NAME")
                    nameSheet.arguments = bundleOf(Pair(C.NAME, viewCName.text.toString()))
                nameSheet.show(supportFragmentManager, nameSheet.tag)
            }
            viewCDone.id -> {
                if (viewCName.text.toString() != "NAME") {
                    if (type != null) {
                        if (parent != null) { // a child category , also update parent one
                            if (category.cisChild) { // if category is a child category i.e. parent exists
                                if (parent!!.cid != category.cParent) { // parent is changed
                                    val uuid = category.cid
                                    if (parent!!.cchildren != null)
                                        parent!!.cchildren!!.add(uuid)
                                    else
                                        parent!!.cchildren = org.sourcei.xpns.utils.extensions.arrayListOf(uuid)

                                    oParent!!.cchildren!!.remove(uuid)
                                    model.editItem(oParent!!)
                                    model.editItem(parent!!)
                                    category.cParent = parent!!.cid
                                }
                            }else{
                                // we are making it child category
                                val uuid = category.cid
                                if (parent!!.cchildren != null)
                                    parent!!.cchildren!!.add(uuid)
                                else
                                    parent!!.cchildren = org.sourcei.xpns.utils.extensions.arrayListOf(uuid)
                                model.editItem(parent!!)
                                category.cisParent = false
                                category.cisChild = true
                                category.cParent = parent!!.cid
                            }
                        }

                        category.cname = viewCName.text.toString().trim()
                        category.cicon = icon!!
                        category.ctype = type!!
                        category.ccolor = color.toColor()

                        model.editItem(category)

                        toast("category updated")
                        EventBus.getDefault().post(
                            Event(
                                jsonOf(
                                    Pair(C.TYPE, C.NEW_CATEGORY),
                                    Pair(C.CATEGORY_TYPE, type!!)
                                )
                            )
                        )
                        finish()
                    } else
                        toast("kindly select category type")
                } else
                    toast("kindly provide a name")
            }
            viewCType.id -> {
                typeSheet.arguments = bundleOf(Pair(C.NAME, viewCName.text.toString()))
                typeSheet.show(supportFragmentManager, typeSheet.tag)
            }
            viewCParentL.id -> {
                openActivityForResult(CategoryActivity::class.java, SELECT_PARENT) {
                    putBoolean(C.SELECT, true)
                    putBoolean(C.SHOW_CHILD, false)
                    putBoolean(C.FAB, false)
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
                    viewCImage.setImageBitmap(it)
                    color = ColorHandler.getNonDarkColor(
                        Palette.from(it).generate(),
                        this
                    )
                    setColor()
                }
            }
        }
        if (requestCode == SELECT_PARENT) {
            if (resultCode == Activity.RESULT_OK) {
                parent =
                    Gson().fromJson(data!!.getStringExtra(C.CATEGORY), CategoryObject::class.java)
                viewCParentT.text = parent!!.cname
                ImageHandler.setImageInView(lifecycle, viewCParentI, parent!!.cicon.iurls!!.url64)
            }
        }
    }

    // custom callback for cname sheet
    override fun call(any: Any) {
        any as JSONObject
        when (any.get(C.TYPE)) {
            C.NAME -> {
                if (any.getString(C.NAME).isEmpty())
                    viewCName.text = "NAME"
                else
                    viewCName.text = any.getString(C.NAME)
            }
            C.TYPE -> {
                type = any.getString(C.NAME)
                viewCType.text = type!!.toUpperCase()
                if (type == C.EXPENSE)
                    viewCType.setTextColor(Colors(this).EXPENSE)
                else
                    viewCType.setTextColor(Colors(this).SAVING)
            }
        }
    }

    // set details
    private fun setDetails() {
        icon = category.cicon
        type = category.ctype

        viewCName.text = category.cname
        viewCType.text = category.ctype.toUpperCase()
        ImageHandler.setIconInView(lifecycle, viewCImage, category.cicon.iurls!!.url64)

        if (category.cisChild)
            model.getItem(category.cParent!!) {
                parent = it
                oParent = it
                viewCParentT.text = it!!.cname
                ImageHandler.setIconInView(lifecycle, viewCParentI, category.cicon.iurls!!.url64)
            }

        color = category.ccolor.toColorInt()
        setColor()
    }

    // set ccolor
    private fun setColor() {
        viewCCView.background.setColorFilter(color, PorterDuff.Mode.SRC_ATOP)
        viewCName.setTextColor(color)

        if (category.ctype == C.EXPENSE)
            viewCType.setTextColor(Colors(this).EXPENSE)
        else
            viewCType.setTextColor(Colors(this).SAVING)
    }
}
