package com.slesarew.cv.cvscreen.model

import com.slesarew.cv.cvscreen.model.CVAction.*
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

    sideEffectIntentionOn(action = OnMediumClickedAction::class) {
        sideEffect = sideEffects.navigateToMediumPage()
    }

    sideEffectIntentionOn(action = OnStackOverflowClickedAction::class) {
        sideEffect = sideEffects.navigateToStackOverflow()
    }

    sideEffectIntentionOn(action = OnYouTubeClickedAction::class) {
        sideEffect = sideEffects.navigateToYouTube()
    }
})