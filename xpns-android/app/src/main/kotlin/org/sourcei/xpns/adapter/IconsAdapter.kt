package org.sourcei.xpns.adapter

import androidx.lifecycle.Lifecycle
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.gson.Gson
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.pojo.IconPojo
import org.sourcei.xpns.sheets.ModalSheetIcon
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.viewholders.IconsViewHolder

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-04 by Saksham
 * @tnote Updates :
 */
class IconsAdapter(private val lifecycle: Lifecycle, private val icons: List<IconPojo>)
    : androidx.recyclerview.widget.RecyclerView.Adapter<IconsViewHolder>() {

    private lateinit var context: Context
    private var sheet = ModalSheetIcon()

    // item count
    override fun getItemCount(): Int {
        return icons.size
    }

    // on create
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IconsViewHolder {
        context = parent.context
        return IconsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.inflator_icons, parent, false))
    }

    // bind view
    override fun onBindViewHolder(holder: IconsViewHolder, position: Int) {
        ImageHandler.setImageInView(lifecycle, holder.icon, icons[position].iurls!!.url64)
        holder.icon.setOnClickListener {
            sheet.arguments = bundleOf(Pair(C.ICON, Gson().toJson(icons[position])))
            sheet.show((context as AppCompatActivity).supportFragmentManager, sheet.tag)
        }
    }

}