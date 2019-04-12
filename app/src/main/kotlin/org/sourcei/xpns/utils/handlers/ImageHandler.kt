package org.sourcei.xpns.utils.handlers

import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget
import org.sourcei.xpns.R

/**
 * @info -
 *
 * @author - Saksham
 * @tnote Last Branch Update - master
 *
 * @tnote Created on 2018-09-04 by Saksham
 * @tnote Updates :
 *  Saksham - 2019 02 02 - master - icon placeholder
 */
object ImageHandler {


    // https://stackoverflow.com/questions/37964187/preload-multiple-images-with-glide


    /**
     * Use to set image on a view using glide
     * @param lifecycle
     * @param view
     * @param url
     */
    fun setImageInView(lifecycle: Lifecycle, view: ImageView, url: String) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            Glide.with(view.context)
                .load(url)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        } else {
            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart() {
                    Glide.with(view.context)
                        .load(url)
                        .into(view)
                }
            })
        }
    }

    // set icon in view with placeholder
    fun setIconInView(lifecycle: Lifecycle, view: ImageView, url: String) {
        if (lifecycle.currentState.isAtLeast(Lifecycle.State.STARTED)) {
            Glide.with(view.context)
                .load(url)
                .placeholder(ContextCompat.getDrawable(view.context, R.drawable.vd_icon_placeholder))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(view)
        } else {
            var once = true
            lifecycle.addObserver(object : LifecycleObserver {
                @OnLifecycleEvent(Lifecycle.Event.ON_START)
                fun onStart() {
                    if (once)
                        Glide.with(view.context)
                            .load(url)
                            .placeholder(ContextCompat.getDrawable(view.context, R.drawable.vd_icon_placeholder))
                            .diskCacheStrategy(DiskCacheStrategy.ALL)
                            .into(view)
                    once = false
                }
            })
        }
    }

    /**
     * get image as bitmap from url
     * @param lifecycle
     * @param context
     * @param url
     */
    fun getImageAsBitmap(lifecycle: Lifecycle, context: Context, url: String, callback: (bitmap: Bitmap) -> Unit) {
        lifecycle.addObserver(object : LifecycleObserver {

            @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
            fun onResume() {
                Glide.with(context)
                    .load(url)
                    .asBitmap()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(object : SimpleTarget<Bitmap>() {
                        override fun onResourceReady(resource: Bitmap?, glideAnimation: GlideAnimation<in Bitmap>?) {
                            resource.let {
                                callback(it!!)
                            }
                        }
                    })
            }

        })
    }
}