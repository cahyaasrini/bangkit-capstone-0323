package com.cap0323.medy.data.remote.response

import com.google.gson.annotations.SerializedName

data class MedicineResponse(
        @field:SerializedName("active_ingredient")
        val activeIngredient: String? = null,

        @field:SerializedName("brand_name")
        val brandName: String? = null,

        @field:SerializedName("category")
        val category: String? = null,

        @field:SerializedName("dosage_and_administration")
        val dosageAndAdministration: String? = null,

        @field:SerializedName("effective_time")
        val effectiveTime: String? = null,

        @field:SerializedName("id")
        val id: String? = null,

        @field:SerializedName("inactive_ingredient")
        val inactiveIngredient: String? = null,

        @field:SerializedName("indications_and_usage")
        val indicationsAndUsage: String? = null,

        @field:SerializedName("purpose")
        val purpose: String? = null,
        @field:SerializedName("warnings")
        val warnings: String? = null,
)
