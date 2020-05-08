package com.slesarew.cv.cvscreen.model

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat.getColor
import com.slesarew.cv.R

private typealias SideEffect = (CVState) -> Unit

class CVSideEffects(private val activity: Activity) {

    fun navigateToMediumPage(): SideEffect =
        { state -> openExternalURL(state.links.mediumUrl) }

    fun navigateToStackOverflow(): SideEffect =
        { state -> openExternalURL(state.links.stackOverflowUrl) }

    fun navigateToYouTube(): SideEffect =
        { state -> openExternalURL(state.links.youtubeUrl) }

    private fun openExternalURL(url: String) {
        if (url.isNotEmpty()) {
            CustomTabsIntent.Builder()
                .setToolbarColor(getColor(activity, R.color.colorPrimary))
                .build()
                .launchUrl(activity, Uri.parse(url))
        }
    }
}
