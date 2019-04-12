package org.sourcei.xpns.utils.extensions

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import java.io.File


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
fun Int.toColor(): String {
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

// convert to file type
fun String.toFile(): File {
    return File(this)
}

//  capitalize word
fun String.toCapital(): String {
    val result = StringBuilder(length)
    val words = split("\\ ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
    for (i in words.indices) {
        result.append(Character.toUpperCase(words[i][0])).append(words[i].substring(1)).append(" ")
    }
    return result.toString()
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

// set status bar color
fun Activity.setStatusBarColor(color: Int) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        val window = window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window.statusBarColor = color
    }
}

// fun run On UI Thread
fun Fragment.runOnUiThread(action: (() -> Unit)) {
    (this.context as Activity).runOnUiThread(action)
}

// toast for fragment
fun Fragment.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    this.context!!.toast(message, length)
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

// run on Ui thread
fun Context.runOnUiThread(action: (() -> Unit)) {
    (this as Activity).runOnUiThread(action)
}
