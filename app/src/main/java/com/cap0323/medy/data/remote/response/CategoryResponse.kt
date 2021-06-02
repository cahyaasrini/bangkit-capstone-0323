package com.cap0323.medy.data.remote.response

import com.google.gson.annotations.SerializedName

data class CategoryResponse(

	@field:SerializedName("CategoryResponse")
	val categoryResponse: List<CategoryResponseItem?>? = null
)

data class CategoryResponseItem(

	@field:SerializedName("a")
	val A: List<AItem?>? = null
)

data class AItem(

	@field:SerializedName("2")
	val jsonMember2: String? = null,

	@field:SerializedName("1")
	val jsonMember1: String? = null,

	@field:SerializedName("0")
	val jsonMember0: String? = null
)
