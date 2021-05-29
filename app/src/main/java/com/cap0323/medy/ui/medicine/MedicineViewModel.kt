package com.cap0323.medy.ui.medicine

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cap0323.medy.data.local.entity.MedicineEntity
import com.cap0323.medy.data.local.room.Repository
import com.cap0323.medy.data.remote.response.MedicineResponse

class MedicineViewModel() : ViewModel() {

    private val _medicineListByName = MutableLiveData<List<MedicineResponse>>()
    val medicineListByName: LiveData<List<MedicineResponse>> = _medicineListByName

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading





}