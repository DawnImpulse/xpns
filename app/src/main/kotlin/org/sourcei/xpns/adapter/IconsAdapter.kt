package org.sourcei.xpns.adapter

import android.arch.lifecycle.Lifecycle
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import com.google.gson.Gson
import org.sourcei.xpns.R
import org.sourcei.xpns.handlers.ImageHandler
import org.sourcei.xpns.holders.IconsViewHolder
import org.sourcei.xpns.pojo.IconPojo
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.sheets.ModalSheetIcon

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-04 by Saksham
 * @note Updates :
 */
class IconsAdapter(private val lifecycle: Lifecycle, private val icons: List<IconPojo>)
    : RecyclerView.Adapter<IconsViewHolder>() {

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
        ImageHandler.setImageInView(lifecycle, holder.icon, icons[position].url64)
        holder.icon.setOnClickListener {
            sheet.arguments = bundleOf(Pair(C.ICON, Gson().toJson(icons[position])))
            sheet.show((context as AppCompatActivity).supportFragmentManager, sheet.tag)
        }
    }

}