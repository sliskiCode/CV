package com.slesarew.cv.extension

import android.widget.ImageView
import coil.api.load
import coil.transform.CircleCropTransformation

fun ImageView.loadUrl(url: String, round: Boolean = false) =
    load(url) {
        crossfade(true)
        if (round) transformations(CircleCropTransformation())
    }
