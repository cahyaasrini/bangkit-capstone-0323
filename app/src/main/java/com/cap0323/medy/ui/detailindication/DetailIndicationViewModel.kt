package com.cap0323.medy.ui.detailindication

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cap0323.medy.data.remote.api.ApiConfig
import com.cap0323.medy.data.remote.response.ConditionResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailIndicationViewModel : ViewModel() {
    private val _detailIndicationByCategory = MutableLiveData<List<ConditionResponse>>()
    val detailByCategory: LiveData<List<ConditionResponse>> = _detailIndicationByCategory

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _getQuery = MutableLiveData<String>()

    val getQueryLive: LiveData<String> = _getQuery

    fun getQuery(query: String) {
        _getQuery.postValue(query)
    }

    private val _noData = MutableLiveData<Boolean>()

    val noData: LiveData<Boolean> = _noData

    fun getIndicationByCategory(name: String) {
        _isLoading.value = true
        _noData.value = false
        val client = ApiConfig.getApiService().getCondition(name)
        client.enqueue(object : Callback<List<ConditionResponse>> {
            override fun onResponse(
                call: Call<List<ConditionResponse>>,
                response: Response<List<ConditionResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body().isNullOrEmpty()) {
                        _noData.value = true
                    } else {
                        _detailIndicationByCategory.value = response.body()
                        _noData.value = false
                    }
                } else {
                    _noData.value = false
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ConditionResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}