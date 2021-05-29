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

class MedicineViewModel() : ViewModel() {

    private val _medicineListByName = MutableLiveData<MedicineResponse>()
    val medicineListByName: LiveData<MedicineResponse> = _medicineListByName

    private val _medicineList = MutableLiveData<MedicineResponse>()
    val medicineList: LiveData<MedicineResponse> = _medicineList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _getQuery = MutableLiveData<String>()
    val getQueryLive: LiveData<String> = _getQuery

    fun getQuery(query: String) {
        _getQuery.postValue(query)
    }

    fun getMedicineByName(name: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMedicineByName(name)
        client.enqueue(object : Callback<MedicineResponse> {
            override fun onResponse(
                call: Call<MedicineResponse>,
                response: Response<MedicineResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _medicineListByName.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MedicineResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getAllMedicine() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getAllMedicine("all")
        client.enqueue(object : Callback<MedicineResponse> {
            override fun onResponse(
                call: Call<MedicineResponse>,
                response: Response<MedicineResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _medicineListByName.value = response.body()
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<MedicineResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}