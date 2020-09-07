package com.slesarew.cv.cvscreen.model

sealed class CvAction {

    object OnScreenCreateAction : CvAction()
    object OnMediumClickedAction : CvAction()
    object OnStackOverflowClickedAction : CvAction()
    object OnYouTubeClickedAction : CvAction()
}
