package com.slesarew.cv.cvscreen.model

import com.slesarew.cv.cvscreen.model.CvAction.OnMediumClickedAction
import com.slesarew.cv.cvscreen.model.CvAction.OnScreenCreateAction
import com.slesarew.cv.cvscreen.model.CvAction.OnStackOverflowClickedAction
import com.slesarew.cv.cvscreen.model.CvAction.OnYouTubeClickedAction
import com.slesarew.mvi.ConnectableViewModel

class CvViewModel(
    transformers: CvTransformers,
    reducers: CvReducers,
    sideEffects: CvSideEffects
) : ConnectableViewModel<CvAction, CVState>(CVState(), {

    intentionOn(action = OnScreenCreateAction::class) {
        transform = transformers.loadCVData()
        reduce = reducers.reduceCVData()
    }

    intentionOn(action = OnMediumClickedAction::class) {
        sideEffect = sideEffects.navigateToMediumPage()
    }

    intentionOn(action = OnStackOverflowClickedAction::class) {
        sideEffect = sideEffects.navigateToStackOverflow()
    }

    intentionOn(action = OnYouTubeClickedAction::class) {
        sideEffect = sideEffects.navigateToYouTube()
    }
})
