package org.sourcei.xpns.utils.views

import android.content.Context
import androidx.core.view.ViewCompat
import android.util.AttributeSet
import android.view.View

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-08-18 by Saksham
 * @tnote Updates :
 */
class BottomNavigationBehaviour<V : View>(context: Context, attrs: AttributeSet) :
        androidx.coordinatorlayout.widget.CoordinatorLayout.Behavior<V>(context, attrs) {

    override fun onStartNestedScroll(
            coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout, child: V, directTargetChild: View, target: View, axes: Int, type: Int
    ): Boolean {
        return axes == ViewCompat.SCROLL_AXIS_VERTICAL
    }

    override fun onNestedPreScroll(
            coordinatorLayout: androidx.coordinatorlayout.widget.CoordinatorLayout, child: V, target: View, dx: Int, dy: Int, consumed: IntArray, type: Int
    ) {
        super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        child.translationY = Math.max(0f, Math.min(child.height.toFloat(), child.translationY + dy))
    }
}