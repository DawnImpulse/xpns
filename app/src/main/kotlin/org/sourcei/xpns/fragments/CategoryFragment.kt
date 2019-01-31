package org.sourcei.xpns.fragments


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dawnimpulse.wallup.utils.L
import kotlinx.android.synthetic.main.fragment_category.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.sourcei.xpns.R
import org.sourcei.xpns.adapter.CategoryAdapter
import org.sourcei.xpns.models.CategoryModel
import org.sourcei.xpns.utils.C
import org.sourcei.xpns.utils.Event


/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-04 by Saksham
 * @tnote Updates :
 */
class CategoryFragment : androidx.fragment.app.Fragment() {
    private val NAME = "CategoryFragment"
    private var select = false
    private var showChild = false
    private lateinit var model: CategoryModel
    private lateinit var adapter: CategoryAdapter
    private lateinit var type: String

    // on create
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    // on view created
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        select = arguments!!.getBoolean(C.SELECT)
        showChild = arguments!!.getBoolean(C.SHOW_CHILD)
        type = arguments!!.getString(C.TYPE)!!

        model = CategoryModel(lifecycle, context!!)
        model.getItems(type) {
            adapter = CategoryAdapter(lifecycle, select, it, showChild)
            categoryRecycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
            categoryRecycler.adapter = adapter
        }
    }

    // on start
    override fun onStart() {
        super.onStart()
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this)
    }

    // on destroy
    override fun onDestroy() {
        super.onDestroy()
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Event) {
        if (event.obj.has(C.TYPE)) {
            when (event.obj.getString(C.TYPE)) {
                C.NEW_CATEGORY -> {
                    L.d(NAME, "$type :: ${event.obj.getString(C.CATEGORY_TYPE)}")
                    if (type == event.obj.getString(C.CATEGORY_TYPE)) {
                        model.getItems(type) {
                            adapter = CategoryAdapter(lifecycle, select, it, showChild)
                            categoryRecycler.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(context)
                            categoryRecycler.adapter = adapter
                        }
                    }
                }
            }
        }
    }

}
