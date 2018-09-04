package org.sourcei.xpns.handlers

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.LifecycleObserver
import android.arch.lifecycle.OnLifecycleEvent
import android.content.Context
import android.graphics.Bitmap
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.animation.GlideAnimation
import com.bumptech.glide.request.target.SimpleTarget

/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2018-09-04 by Saksham
 * @note Updates :
 */
object ImageHandler{

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