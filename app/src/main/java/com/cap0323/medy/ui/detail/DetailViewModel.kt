package com.cap0323.medy.ui.detail

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

class DetailViewModel : ViewModel() {

    private val _detailMedicine = MutableLiveData<List<MedicineResponse>>()
    val detailMedicine: LiveData<List<MedicineResponse>> = _detailMedicine

    private val _recommendationMedicine = MutableLiveData<List<MedicineResponse>>()
    val recommendationMedicine: LiveData<List<MedicineResponse>> = _recommendationMedicine

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun getDetailMedicine(id: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getMedicineById(id)
        client.enqueue(object : Callback<List<MedicineResponse>> {
            override fun onResponse(
                call: Call<List<MedicineResponse>>,
                response: Response<List<MedicineResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailMedicine.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<MedicineResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getRecommendation(categoryName: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getRecommendation(categoryName)
        client.enqueue(object : Callback<List<MedicineResponse>> {
            override fun onResponse(
                call: Call<List<MedicineResponse>>,
                response: Response<List<MedicineResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _recommendationMedicine.value = response.body()
                } else {
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<MedicineResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}