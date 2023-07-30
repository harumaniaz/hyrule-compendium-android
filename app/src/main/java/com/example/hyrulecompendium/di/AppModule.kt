package com.example.hyrulecompendium.di

import com.example.hyrulecompendium.data.local.DataStoreManager
import com.example.hyrulecompendium.data.repository.ApiRepository
import com.example.hyrulecompendium.ui.screen.detail.DetailViewModel
import com.example.hyrulecompendium.ui.screen.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        ApiModule.provideService()
    }

    single {
        ApiRepository(apiService = get())
    }

    single {
        DataStoreManager(get())
    }

    viewModel {
        HomeViewModel(repository = get())
    }

    viewModel {
        DetailViewModel(repository = get())
    }
}