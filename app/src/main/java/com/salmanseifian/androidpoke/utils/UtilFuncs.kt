package com.salmanseifian.androidpoke.utils

import java.net.URI


fun getSpeciesId(urlStr: String?): String {
    val uri = URI(urlStr)
    val path: String = uri.path
    return path.substring(path.lastIndexOf('/') - 1, path.lastIndex)
}

fun createImageUrl(speciesId : String): String{
    return Constants.IMAGE_BASE_URL + speciesId + ".png"
}