package com.cap0323.medy.ui.typeCategory

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cap0323.medy.data.remote.api.ApiConfig
import com.cap0323.medy.data.remote.response.CategoryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TypeCategoryViewModel() : ViewModel() {
    private val _indicationByChar = MutableLiveData<List<CategoryResponse>>()
    val indicationByChar: LiveData<List<CategoryResponse>> = _indicationByChar

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _getQuery = MutableLiveData<String>()

    val getQueryLive: LiveData<String> = _getQuery

    fun getQuery(query: String) {
        _getQuery.postValue(query)
    }

    private val _noData = MutableLiveData<Boolean>()

    val noData: LiveData<Boolean> = _noData

    fun getCategoryByChar(name: String) {
        _isLoading.value = true
        _noData.value = false
        val client = ApiConfig.getApiServiceHeroku().getIndicationByChar(name)
        client.enqueue(object : Callback<List<CategoryResponse>> {
            override fun onResponse(
                call: Call<List<CategoryResponse>>,
                response: Response<List<CategoryResponse>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    if (response.body().isNullOrEmpty()) {
                        _noData.value = true
                    } else {
                        _indicationByChar.value = response.body()
                        _noData.value = false
                    }
                } else {
                    _noData.value = false
                    Log.e(ContentValues.TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<CategoryResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}