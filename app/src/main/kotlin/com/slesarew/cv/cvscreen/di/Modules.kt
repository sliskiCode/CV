package com.slesarew.cv.cvscreen.di

import android.app.Activity
import com.slesarew.cv.cvscreen.model.CVReducers
import com.slesarew.cv.cvscreen.model.CVSideEffects
import com.slesarew.cv.cvscreen.model.CVTransformers
import com.slesarew.cv.cvscreen.model.CVViewModel
import com.slesarew.cv.cvscreen.view.renderer.CVRenderer
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

val cvModule: Module = module {

    viewModel { (activity: Activity) -> CVViewModel(get(), get(), CVSideEffects(activity)) }

    single { CVTransformers(get(), get()) }

    single { CVReducers() }

    single { CVRenderer(androidContext()) }
}
