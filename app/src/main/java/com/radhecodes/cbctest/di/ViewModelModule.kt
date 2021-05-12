package com.radhecodes.cbctest.di

import com.radhecodes.cbctest.ui.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val newsViewModelModule = module {
    viewModel {
        NewsViewModel(get())
    }
}