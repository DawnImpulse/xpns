package org.sourcei.xpns.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import org.json.JSONObject
import java.io.File
import android.view.MotionEvent
import android.os.SystemClock



/**
 * @info -
 *
 * @author - Saksham
 * @note Last Branch Update - master
 *
 * @note Created on 2019-01-17 by Saksham
 * @note Updates :
 */
// int color to hex string
fun Int.toHexa(): String {
    return String.format("#%06X", 0xFFFFFF and this)
}

// file path string to uri
fun String.toFileUri(): Uri {
    return Uri.fromFile(File(this))
}

// file path string to content uri
fun String.toContentUri(context: Context): Uri {
    return FileProvider.getUriForFile(context, "com.chillkr.android", toFile())
    //return this.toFileUri().toContentUri(context)
}

// tree uri path to file uri path
fun String.toFileString(): String {
    return if (this.contains(":")) {
        var substring = split(":")
        var tree = substring[0]

        if (tree.contains("primary"))
            Environment.getExternalStorageDirectory().path + "/${substring[1]}"
        else
            "/storage/${tree.replace("/tree/", "")}/${substring[1]}"
    } else
        this
}

//covert to file type
fun String.toFile(): File {
    return File(this)
}

//convert to content uri
fun Uri.toContentUri(context: Context): Uri {
    val cr = context.contentResolver
    val file = File(this.path)
    val imagePath = file.absolutePath
    val imageName: String? = null
    val imageDescription: String? = null
    val uriString = MediaStore.Images.Media.insertImage(cr, imagePath, imageName, imageDescription)
    return Uri.parse(uriString)
}

// gone view
fun View.gone() {
    visibility = View.GONE
}

// hide view
fun View.hide() {
    visibility = View.INVISIBLE
}

// gone view
fun View.show() {
    visibility = View.VISIBLE
}

// open activity for result
fun <T> Activity.openActivityForResult(it: Class<T>, code: Int, bundle: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(bundle))
    startActivityForResult(intent, code)
}

// open activity for result
fun <T> Activity.openActivityForResult(it: Class<T>, code: Int) {
    startActivityForResult(Intent(this, it), code)
}

// toast
fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}

// open activity
fun <T> Context.openActivity(it: Class<T>) {
    startActivity(Intent(this, it))
}

// open activity
fun <T> Context.openActivity(it: Class<T>, bundle: Bundle.() -> Unit = {}) {
    val intent = Intent(this, it)
    intent.putExtras(Bundle().apply(bundle))
    startActivity(intent)
}

// get display height
fun Context.displayDimensions(): Point {
    val point = Point()
    val mWindowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
    val display = mWindowManager.defaultDisplay
    display.getSize(point) //The point now has display dimens
    return point
}

//get display ratio a/b
fun Context.displayRatio(): Pair<Int, Int> {

    fun calculateHcf(width1: Int, height1: Int): Int {
        var width = width1
        var height = height1
        while (height != 0) {
            val t = height
            height = width % height
            width = t
        }
        return width
    }

    val point = displayDimensions()
    val hcf = calculateHcf(point.x, point.y)

    return Pair(point.y / hcf, point.x / hcf)
}

//start web
fun Context.startWeb(url: String) {
    startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
}

// json put params
fun jsonOf(vararg pairs: Pair<String, Any>) = JSONObject().apply {
    pairs.forEach {
        put(it.first, it.second)
    }
}

// array list
fun <T> arrayListOf(item: T): ArrayList<T> {
    val list = ArrayList<T>()
    list.add(item)
    return list
}
