package com.hearx.din.dependencyinjection

import com.hearx.din.repository.ScoreRepository
import com.hearx.din.repository.ScoreRepositoryImpl
import com.hearx.din.repository.TestHistoryRepository
import com.hearx.din.repository.TestHistoryRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {
    single<ScoreRepository>{
        ScoreRepositoryImpl()
    }

    single<TestHistoryRepository>{
        TestHistoryRepositoryImpl(get())
    }
}