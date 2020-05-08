package com.slesarew.cv.cvscreen.model

sealed class CVAction {

    object OnScreenCreateAction : CVAction()
    object OnMediumClickedAction : CVAction()
    object OnStackOverflowClickedAction : CVAction()
    object OnYouTubeClickedAction : CVAction()
}
