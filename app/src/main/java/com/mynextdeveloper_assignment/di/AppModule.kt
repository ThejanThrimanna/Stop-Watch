package com.mynextdeveloper_assignment.di


import com.mynextdeveloper_assignment.viewmodel.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {
    viewModel { MainViewModel() }
}