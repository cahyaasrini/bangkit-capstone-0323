package com.cap0323.medy.ui.typeIndication

import android.content.ContentValues
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.cap0323.medy.data.local.entity.TypeIndicationEntity
import com.cap0323.medy.data.local.source.DummyData
import com.cap0323.medy.data.remote.api.ApiConfig
import com.cap0323.medy.data.remote.response.IndicationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TypeIndicationViewModel() : ViewModel() {

    private val _indicationByChar = MutableLiveData<List<IndicationResponse>>()
    val indicationByChar: LiveData<List<IndicationResponse>> = _indicationByChar

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
        val client = ApiConfig.getApiService().getIndicationByChar(name)
        client.enqueue(object : Callback<List<IndicationResponse>> {
            override fun onResponse(
                call: Call<List<IndicationResponse>>,
                response: Response<List<IndicationResponse>>
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

            override fun onFailure(call: Call<List<IndicationResponse>>, t: Throwable) {
                Log.e(ContentValues.TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }

    fun getAllIndication(): ArrayList<TypeIndicationEntity> = DummyData.getTypeIndication()
}