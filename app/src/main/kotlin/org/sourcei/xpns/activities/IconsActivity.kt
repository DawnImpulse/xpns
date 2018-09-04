package org.sourcei.xpns.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import androidx.core.widget.toast
import com.dawnimpulse.wallup.utils.L
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_icons.*
import org.sourcei.xpns.R
import org.sourcei.xpns.adapter.IconsAdapter
import org.sourcei.xpns.models.IconsModel
import org.sourcei.xpns.pojo.IconPojo

class IconsActivity : AppCompatActivity() {
    private val NAME = "IconsActivity"
    lateinit var model: IconsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icons)

        model = IconsModel(lifecycle)
    }

    // resumed
    override fun onResume() {
        super.onResume()

        model.getLatestIcons { error, details ->
            if (error != null) {
                toast("there is an error")
                L.d(NAME, error.toString())
            } else {
                L.d(NAME, Gson().toJson(details))
                var adapter = IconsAdapter(lifecycle, details as List<IconPojo>)
                iconsRecycler.layoutManager = LinearLayoutManager(this)
                iconsRecycler.adapter = adapter
            }
        }
    }
}
