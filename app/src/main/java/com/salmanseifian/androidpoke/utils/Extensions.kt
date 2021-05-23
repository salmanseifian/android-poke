package com.salmanseifian.androidpoke.utils

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import java.net.URI


fun Context.toast(@StringRes resId: Int) {
    Toast.makeText(this, resId, Toast.LENGTH_SHORT).show()
}

fun String.extractSpeciesId(): String {
    val uri = URI(this)
    val path: String = uri.path
    return path.substring(path.lastIndexOf('/') - 1, path.lastIndex)
}

fun String.createImageUrl() = IMAGE_BASE_URL + this.extractSpeciesId() + ".png"