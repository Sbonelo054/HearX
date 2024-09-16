package com.hearx.din.dependencyinjection

import com.hearx.din.viewmodel.HearXViewModel
import com.hearx.din.viewmodel.TestHistoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { HearXViewModel(get()) }

    viewModel {
        TestHistoryViewModel(get(),get())
    }
}