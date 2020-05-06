package com.slesarew.cv.di

import com.slesarew.cv.extension.create
import com.slesarew.cv.repository.CVRepository
import com.slesarew.cv.repository.network.GithubNetworkCVRepository
import com.slesarew.cv.repository.service.GithubService
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule: Module = module {

    single<CVRepository> { GithubNetworkCVRepository(get()) }

    single<GithubService> {
        Retrofit
            .Builder()
            .baseUrl(GithubService.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }
}
