package com.hearx.din.dependencyinjection

import com.hearx.din.viewmodel.HearXViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel {
        HearXViewModel()
    }
}