package com.slesarew.cv.cvscreen.model

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat.getColor
import com.slesarew.cv.R
import com.slesarew.mvi.SideEffect

class CvSideEffects(private val activity: Activity) {

    fun navigateToMediumPage(): SideEffect<CVState, CvAction> =
        { state, _ -> openExternalURL(state.links.mediumUrl) }

    fun navigateToStackOverflow(): SideEffect<CVState, CvAction> =
        { state, _ -> openExternalURL(state.links.stackOverflowUrl) }

    fun navigateToYouTube(): SideEffect<CVState, CvAction> =
        { state, _ -> openExternalURL(state.links.youtubeUrl) }

    private fun openExternalURL(url: String) {
        if (url.isNotEmpty()) {
            CustomTabsIntent.Builder()
                .setToolbarColor(getColor(activity, R.color.colorPrimary))
                .build()
                .launchUrl(activity, Uri.parse(url))
        }
    }
}
