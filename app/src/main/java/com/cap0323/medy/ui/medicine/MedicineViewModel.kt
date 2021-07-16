package com.cap0323.medy.ui.medicine

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cap0323.medy.data.remote.api.ApiConfig
import com.cap0323.medy.data.remote.response.MedicineResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MedicineViewModel : ViewModel() {

    private val _medicineListByName = MutableLiveData<List<MedicineResponse>>()
    val medicineListByName: LiveData<List<MedicineResponse>> = _medicineListByName

    private val _medicineList = MutableLiveData<List<MedicineResponse>>()
    val medicineList: LiveData<List<MedicineResponse>> = _medicineList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _getQuery = MutableLiveData<String>()

    val getQueryLive: LiveData<String> = _getQuery

    init {
        getQuery("")
    }

    fun getQuery(query: String) {
        if (query != "") {
            _getQuery.postValue(query)
        } else {
            _getQuery.postValue("")
        }
    }

    private val _noData = MutableLiveData<Boolean>()

    val noData: LiveData<Boolean> = _noData

    fun getMedicineByName(name: String) {
        _isLoading.value = true
        _noData.value = false
        val client = ApiConfig.getApiService().getMedicineByName(name)
        client.enqueue(object : Callback<List<MedicineResponse>> {
            override fun onResponse(
                call: Call<List<MedicineResponse>>,
                response: Response<List<MedicineResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body().isNullOrEmpty()) {
                        _noData.value = true
                    } else {
                        _medicineListByName.value = response.body()
                        _noData.value = false
                    }
                } else {
                    _noData.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<MedicineResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getAllMedicine(all: String) {
        _isLoading.value = true
        _noData.value = false
        val client = ApiConfig.getApiService().getAllMedicine(all)
        client.enqueue(object : Callback<List<MedicineResponse>> {
            override fun onResponse(
                call: Call<List<MedicineResponse>>,
                response: Response<List<MedicineResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body().isNullOrEmpty()) {
                        _noData.value = true
                    } else {
                        _medicineList.value = response.body()
                        _noData.value = false
                    }
                } else {
                    _noData.value = false
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<MedicineResponse>>, t: Throwable) {
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}