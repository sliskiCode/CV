package com.slesarew.cv.cvscreen.model

import com.slesarew.cv.cvscreen.model.CVAction.OnMediumClickedAction
import com.slesarew.cv.cvscreen.model.CVAction.OnScreenCreateAction
import com.slesarew.cv.cvscreen.model.CVAction.OnStackOverflowClickedAction
import com.slesarew.cv.cvscreen.model.CVAction.OnYouTubeClickedAction
import com.slesarew.mvi.ConnectableViewModel

class CVViewModel(
    transformers: CVTransformers,
    reducers: CVReducers,
    sideEffects: CVSideEffects
) : ConnectableViewModel<CVAction, CVState>(CVState(), {

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
