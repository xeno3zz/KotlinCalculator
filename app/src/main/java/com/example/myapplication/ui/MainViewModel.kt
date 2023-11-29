package com.example.myapplication.ui

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.domain.usecase.CalculateResults

class MainViewModel(private val calcResults: CalculateResults) : ViewModel() {

    private val resultLive = MutableLiveData<String>()
    private val workingsLive = MutableLiveData<String>()
    init {
        Log.e("AAA", "VM created")
    }

    override fun onCleared() {
        super.onCleared()
    }
    fun getResultLive():LiveData<String>{
        return resultLive
    }
    fun getWorkingsLive(): LiveData<String> {
        return workingsLive
    }

    fun destroySave(passedList: String){
        workingsLive.value = passedList
    }
    fun calc(passedList: String) {
      val result = calcResults.execute(passedList)
        workingsLive.value = passedList
        resultLive.value = result
    }
    fun clear(){
        workingsLive.value = ""
        resultLive.value = ""
    }
}