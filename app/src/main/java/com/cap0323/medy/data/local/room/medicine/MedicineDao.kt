package com.cap0323.medy.data.local.room.medicine

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.cap0323.medy.data.local.entity.MedicineEntity

@Dao
interface MedicineDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(medicineEntity: MedicineEntity)

    @Query("SELECT * from medicine where brandName=:brandName")
    fun getMedByName(brandName: String): LiveData<List<MedicineEntity>>

    @Query("SELECT category from medicine where brandName=:brandName")
    fun getCategoryMedByBrand(brandName: String): LiveData<MedicineEntity>

    @Query("SELECT * from medicine where category=:category")
    fun getAllByCategory(category: String):LiveData<MedicineEntity>
}