package org.sourcei.xpns.viewholders

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dawnimpulse.wallup.utils.L
import com.google.gson.Gson
import kotlinx.android.synthetic.main.inflator_category.view.*
import org.sourcei.xpns.R
import org.sourcei.xpns.adapter.ChildCategoryAdapter
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.pojo.CategoryPojo
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.show

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-04 by Saksham
 * @tnote Updates :
 */
class CategoryViewHolder(private val parent: ViewGroup,
                         private val lifecycle: Lifecycle,
                         private val select: Boolean) : RecyclerView.ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.inflator_category, parent, false)) {

    private val NAME = "CategoryViewHolder"
    private val image = itemView.categoryImage
    private val name = itemView.categoryName
    private val color = itemView.categoryColor
    private val layout = itemView.categoryL
    private val context = parent.context
    private val recycler = itemView.categoryRecycler

    // binding data to layout
    fun bindTo(category: CategoryPojo?, showChild: Boolean = true) {
        category?.let {
            val colorL = color.background.current as GradientDrawable

            ImageHandler.setImageInView(lifecycle, image, category.cicon.iurls!!.url64)
            colorL.setColor(Color.parseColor(category.ccolor))
            name.text = category.cname

            L.d(NAME, "$showChild :: ${category.cisParent} :: ${category.cchilden}")
            if (showChild && category.cisParent && category.cchilden != null) {
                L.d(NAME,"incoming")
                recycler.show()
                recycler.layoutManager = LinearLayoutManager(context)
                recycler.adapter = ChildCategoryAdapter(lifecycle, category.cchilden!!, select)
            }

            layout.setOnClickListener {
                if (select) {
                    val intent = Intent()
                    intent.putExtra(C.CATEGORY, Gson().toJson(category))
                    (context as AppCompatActivity).setResult(Activity.RESULT_OK, intent)
                    context.finish()
                }
            }
        }
    }
}