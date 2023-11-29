package com.example.myapplication.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.domain.usecase.CalculateResults

class MainViewModelFactory : ViewModelProvider.Factory{

    private val calculateResultsUseCase by lazy(LazyThreadSafetyMode.NONE) {
        CalculateResults()
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(calcResults = calculateResultsUseCase) as T
    }
}