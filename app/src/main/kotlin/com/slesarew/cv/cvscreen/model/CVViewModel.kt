package com.slesarew.cv.cvscreen.model

import com.slesarew.cv.cvscreen.model.CVAction.OnScreenCreateAction
import com.slesarew.mvi.ConnectableViewModel

class CVViewModel(
    transformers: CVTransformers,
    reducers: CVReducers
) : ConnectableViewModel<CVAction, CVState>(CVState(), {

    intentionOn(action = OnScreenCreateAction::class) {
        transform = transformers.loadCVData()
        reduce = reducers.reduceCVData()
    }
})