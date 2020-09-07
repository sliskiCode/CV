package com.slesarew.cv.cvscreen.di

import android.app.Activity
import com.slesarew.cv.cvscreen.model.CvReducers
import com.slesarew.cv.cvscreen.model.CvSideEffects
import com.slesarew.cv.cvscreen.model.CvTransformers
import com.slesarew.cv.cvscreen.model.CvViewModel
import com.slesarew.cv.cvscreen.view.renderer.CvRenderer
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val cvModule: Module = module {

    viewModel { (activity: Activity) -> CvViewModel(get(), get(), CvSideEffects(activity)) }

    single { CvTransformers(get(), get()) }

    single { CvReducers() }

    single { CvRenderer(androidContext()) }
}
