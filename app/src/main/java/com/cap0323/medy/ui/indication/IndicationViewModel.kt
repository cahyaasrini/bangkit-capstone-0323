package com.cap0323.medy.ui.indication

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cap0323.medy.data.remote.api.ApiConfig
import com.cap0323.medy.data.remote.response.MedicineResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class IndicationViewModel : ViewModel() {
    private val _allByCategory = MutableLiveData<List<MedicineResponse>>()
    val allByCategory: LiveData<List<MedicineResponse>> = _allByCategory

    private val _medicineListByIndication = MutableLiveData<List<MedicineResponse>>()
    val medicineListByIndication: LiveData<List<MedicineResponse>> = _medicineListByIndication

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _getQuery = MutableLiveData<String>()

    val getQueryLive: LiveData<String> = _getQuery

    fun getQuery(query: String) {
        _getQuery.postValue(query)
    }

    private val _noData = MutableLiveData<Boolean>()
    val noData: LiveData<Boolean> = _noData

    fun getAllByCategory(name: String) {
        _isLoading.value = true
        _noData.value = false
        val client = ApiConfig.getApiServiceHeroku().getRecommendation(name)
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
                        _allByCategory.value = response.body()
                        _noData.value = false
                    }
                } else {
                    _noData.value = false
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<MedicineResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getMedicineByIndication(indication: String) {
        _isLoading.value = true
        _noData.value = false
        val client = ApiConfig.getApiService().getMedicineByIndication(indication)
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
                        _medicineListByIndication.value = response.body()
                        _noData.value = false
                    }
                } else {
                    _noData.value = false
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<MedicineResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}