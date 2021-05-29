package com.cap0323.medy.data.local.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "medicine")
@Parcelize
data class MedicineEntity(
	@PrimaryKey(autoGenerate = true)
	@NonNull
	@ColumnInfo(name = "id")
	val id: Int? = null,

	@ColumnInfo(name = "idMed")
	val idMed: String? = null,

	@ColumnInfo(name = "category")
	val category: String? = null,

	@ColumnInfo(name = "brandName")
	val brandName: String? = null,

	@ColumnInfo(name = "effectiveTime")
	val effectiveTime: String? = null,

	@ColumnInfo(name = "purpose")
	val purpose: String? = null,

	@ColumnInfo(name = "indicationsAndUsage")
	val indicationsAndUsage: String? = null,

	@ColumnInfo(name = "activeIngredient")
	val activeIngredient: String? = null,

	@ColumnInfo(name = "inactiveIngredient")
	val inactiveIngredient: String? = null,

	@ColumnInfo(name = "dosageAndAdministration")
	val dosageAndAdministration: String? = null,

	@ColumnInfo(name = "warnings")
	val warnings: String? = null
) : Parcelable
