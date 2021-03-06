package com.salmanseifian.androidpoke.utils

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.StringRes
import com.bumptech.glide.Glide
import java.net.URI


fun Context.toast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun String.extractSpeciesId(): String {
    val uri = URI(this)
    val path: String = uri.path
    val segments = path.split("/")
    return segments[segments.size - 2]
}

fun String.createImageUrl() = IMAGE_BASE_URL + this.extractSpeciesId() + ".png"

fun ImageView.loadUrl(url: String?){
    Glide.with(context).load(url).into(this)
}