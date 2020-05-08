package com.slesarew.cv.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions

fun ImageView.fromUrl(url: String, round: Boolean = false) =
    Glide.with(context)
        .load(url)
        .apply { if (round) apply(RequestOptions().circleCrop()) }
        .into(this)
