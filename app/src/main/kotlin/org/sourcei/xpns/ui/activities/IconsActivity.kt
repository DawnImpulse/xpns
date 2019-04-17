package org.sourcei.xpns.ui.activities

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_icons.*
import org.sourcei.xpns.R
import org.sourcei.xpns.ui.adapter.IconsAdapter
import org.sourcei.xpns.database.realtime.models.IconsModel
import org.sourcei.xpns.utils.others.Arrays
import org.sourcei.xpns.utils.extensions.gone
import org.sourcei.xpns.utils.extensions.show

class IconsActivity : AppCompatActivity() {
    private val NAME = "IconsActivity"
    lateinit var model: IconsModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_icons)

        iconsProgress.gone()
        iconsRecycler.show()

        iconsRecycler.layoutManager = GridLayoutManager(this,4)
        iconsRecycler.adapter = IconsAdapter(lifecycle, Arrays.icons)
    }

    // if back pressed
    override fun onBackPressed() {
        setResult(Activity.RESULT_CANCELED)
        finish()
    }
}
