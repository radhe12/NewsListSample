package com.radhecodes.cbctest.di

import com.radhecodes.cbctest.ui.NewsViewModel
import org.koin.androidx.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module

val newsViewModelModule = module {
    viewModel {
        NewsViewModel(get())
    }
}