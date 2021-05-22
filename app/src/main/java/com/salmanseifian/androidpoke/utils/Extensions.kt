package com.salmanseifian.androidpoke.utils

import java.net.URI


fun String.extractSpeciesId(): String{
    val uri = URI(this)
    val path: String = uri.path
    return path.substring(path.lastIndexOf('/') - 1, path.lastIndex)
}

fun String.createImageUrl() = IMAGE_BASE_URL + this.extractSpeciesId() + ".png"